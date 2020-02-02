package com.lz.member.mapper.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("用户注册")
public class UserDO {
    /**
     * userid
     */
    private Long userId;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 性别 0 男 1女
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Long age;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 账号是否可用 1 正常 0冻结
     */
    private Integer isAvalible;
    /**
     * 用户头像
     */
    private String picImg;
    /**
     * 用户关联 QQ 开放ID
     */
    private Date qqOpenid;
    /**
     * 用户关联 微信 开放ID
     */
    private Date wxOpenid;
}