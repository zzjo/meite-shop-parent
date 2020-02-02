package com.lz.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lz.base.BaseApiService;
import com.lz.base.BaseResponse;
import com.lz.bean.MiteBeanUtils;
import com.lz.constants.Constants;
import com.lz.member.MemberRegisterService;
import com.lz.member.feign.VerifierCodeServiceFeign;
import com.lz.member.input.dto.UserInpDTO;
import com.lz.member.mapper.UserMapper;
import com.lz.member.mapper.entity.UserDO;
import com.lz.utils.GeneratorUtil;
import com.lz.utils.MD5Util;
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
    public BaseResponse<JSONObject> saveUser(@Validated @RequestBody UserInpDTO dto, BindingResult bindingResult, @RequestParam("registerCode") String registerCode) {
        //校验参数
        if (bindingResult.hasErrors()) {
            return setResultError(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        //邮箱,用户名是否被注册过
        if (userMapper.existEmailOrUsername(dto.getEmail(), null) > 0) {
            return setResultError("邮箱已注册过！");
        }
        if (userMapper.existEmailOrUsername(null, dto.getUserName()) > 0) {
            return setResultError("用户名已注册过！");
        }
        //校验注册码
        BaseResponse<JSONObject> result = verifierCodeServiceFeign.verifierWeixinCode(dto.getMobile(), registerCode);
        if (!Constants.HTTP_RES_CODE_200.equals(result.getCode())) {
            return setResultError(result.getMsg());
        }
        //密码加密
        String salt = GeneratorUtil.getNonceNumberString(4);
        String newPassword = MD5Util.MD5(dto.getPassword() + salt);
        dto.setPassword(newPassword);
        //调用数据库插入数据参数转换
        UserDO userDO = MiteBeanUtils.dtoToDo(dto, UserDO.class);
        return userMapper.saveUser(userDO) > 0 ? setResultSuccess("注册成功!") : setResultError("注册失败!");
    }
}
