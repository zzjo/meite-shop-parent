package com.lz.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lz.base.BaseApiService;
import com.lz.base.BaseResponse;
import com.lz.constants.Constants;
import com.lz.member.MemberRegisterService;
import com.lz.member.entity.UserEntity;
import com.lz.member.feign.VerifierCodeServiceFeign;
import com.lz.member.mapper.UserMapper;
import com.lz.utils.GeneratorUtil;
import com.lz.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 * @date 2020-01-16 11:55
 */
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerifierCodeServiceFeign verifierCodeServiceFeign;

    @Override
    public BaseResponse<JSONObject> saveUser(@Validated @RequestBody UserEntity userEntity, BindingResult bindingResult, @RequestParam("registerCode") String registerCode) {
        //校验参数
        if (bindingResult.hasErrors()) {
            return setResultError(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        //校验注册码
        BaseResponse<JSONObject> result = verifierCodeServiceFeign.verifierWeixinCode(userEntity.getMobile(), registerCode);
        if (!Constants.HTTP_RES_CODE_200.equals(result.getCode())) {
            return setResultError(result.getMsg());
        }
        //密码加密
        String salt = GeneratorUtil.getNonceNumberString(4);
        String newPassword = MD5Util.MD5(userEntity.getPassword() + salt);
        userEntity.setPassword(newPassword);
        //调用数据库插入数据
        return userMapper.saveUser(userEntity) > 0 ? setResultSuccess("注册成功!") : setResultError("注册失败!");
    }
}
