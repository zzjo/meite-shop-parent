package com.lz.member.service.impl;

import com.lz.base.BaseApiService;
import com.lz.base.BaseResponse;
import com.lz.bean.MiteBeanUtils;
import com.lz.constants.Constants;
import com.lz.member.MemberService;
import com.lz.member.mapper.UserMapper;
import com.lz.member.mapper.entity.UserDO;
import com.lz.member.output.dto.UserOutDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 * @date 2020-01-16 13:59
 */
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        //1.校验参数
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号不能为空!");
        }
        //2.根据手机号码查询用户信息,单独定义code 表示是用户信息不存在
        UserDO userDO = userMapper.existMobile(mobile);
        if (ObjectUtils.isEmpty(userDO)) {
            return setResultError(Constants.HTTP_RES_CODE_existMobile_203, "用户不存在!");
        }
        //3.对敏感字段需要做脱敏
        return setResultSuccess(MiteBeanUtils.doToDto(userDO,UserOutDTO.class));

    }
}
