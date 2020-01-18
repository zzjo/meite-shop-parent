package com.lz.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lz.base.BaseApiService;
import com.lz.base.BaseResponse;
import com.lz.constants.Constants;
import com.lz.utils.RedisUtil;
import com.lz.weixin.VerifierCodeService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 * @date 2020-01-15 17:30
 */
@RestController
public class VerifierCodeServiceImpl extends BaseApiService<JSONObject> implements VerifierCodeService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> verifierWeixinCode(String phone, String weixinCode) {
        //判空
        if (StringUtils.isEmpty(phone)) {
            return setResultError("手机号码不能为空!");
        }
        if (StringUtils.isEmpty(weixinCode)) {
            return setResultError("微信验证码不能为空!");
        }
        //根据手机号查找redis返回对应的注册码
        String weixinCodeKey= Constants.WEIXINCODE_KEY+phone;
        Object redisCode = redisUtil.get(weixinCodeKey);
        if (ObjectUtils.isEmpty(redisCode)){
            return setResultError("注册码可能已经过期!");
        }
        //redis中注册码与传递参数的weixinCode进行比对
        if (!redisCode.equals(weixinCode)){
            return setResultError("注册码不正确");
        }
        //移除注册码
        redisUtil.del(weixinCodeKey);
        return setResultSuccess("验证码对比正确");
    }
}
