package com.lz.member;

import com.alibaba.fastjson.JSONObject;
import com.lz.base.BaseResponse;
import com.lz.member.input.dto.UserInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "会员注册接口")
public interface MemberRegisterService {
    /**
     * 用户注册接口
     * @param dto
     * @param bindingResult
     * @param registerCode
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "会员用户注册信息接口")
    BaseResponse<JSONObject> saveUser(@RequestBody UserInpDTO dto, BindingResult bindingResult, @RequestParam("registerCode") String registerCode);
}
