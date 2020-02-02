package com.lz.weixin.mp.handler;

import com.lz.base.BaseResponse;
import com.lz.constants.Constants;
import com.lz.member.output.dto.UserOutDTO;
import com.lz.utils.GeneratorUtil;
import com.lz.utils.RedisUtil;
import com.lz.utils.RegexUtils;
import com.lz.weixin.mp.builder.TextBuilder;
import com.lz.weixin.mp.feign.MemberServiceFeign;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class MsgHandler extends AbstractHandler {
    /**
     * 发送验证码消息
     */
    @Value("${mayikt.weixin.registration.code.message}")
    private String registrationCodeMessage;
    /**
     * 默认回复消息
     */
    @Value("${mayikt.weixin.default.registration.code.message}")
    private String defaultRegistrationCodeMessage;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            // TODO 可以选择将消息保存到本地
        }

        // 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        String fromContent = wxMessage.getContent();
        log.info("fromContent:" + fromContent);
        if (RegexUtils.checkMobile(fromContent)) {
            //根据手机号码调用会员服务接口查询用户信息是否存在
            BaseResponse<UserOutDTO> resultUserInfo = memberServiceFeign.existMobile(fromContent);
            if (Constants.HTTP_RES_CODE_200.equals(resultUserInfo.getCode())) {
                return new TextBuilder().build("该手机号码" + fromContent + "已经存在", wxMessage, weixinService);
            }
            if (!Constants.HTTP_RES_CODE_existMobile_203.equals(resultUserInfo.getCode())) {
                return new TextBuilder().build(resultUserInfo.getMsg(), wxMessage, weixinService);
            }
            String registCode = GeneratorUtil.getNonceNumberString(4);
            String content = String.format(registrationCodeMessage, registCode);
            // 将注册码存入在redis中 key为手机号码
            redisUtil.set(Constants.WEIXINCODE_KEY + fromContent, registCode + "", Constants.WEIXINCODE_TIMEOUT);
            return new TextBuilder().build(content, wxMessage, weixinService);
        }
        // TODO 组装回复消息
        // String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
//		String content = "每特教育微服务电商项目牛逼！学习完毕稳拿20k月薪！";
        return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);

    }

}
