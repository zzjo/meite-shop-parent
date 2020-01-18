package com.lz.member.input.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 用户输入dto
 * @author lz
 * @date 2020-01-16 9:50
 */
@Data
@Accessors(chain = true)
@ApiModel("用户信息实体类 ")
public class UserInpDTO {
    /**
     * userid
     */
    @ApiModelProperty(value = "用户id", hidden = true)
    @ApiParam(hidden = true)
    private Long userId;
    /**
     * 手机号码
     */
    @NotNull(message = "手机号码不能为空!")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误!")
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空!")
    @Email(message = "邮箱格式错误!")
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空!")
    @Length(min = 6, message = "密码必须大于6位")
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 用户名称
     */
    @NotNull(message = "用户名称不能为空!")
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 性别 0 男 1女
     */
    @NotNull(message = "用户性别不能为空!")
    @ApiModelProperty(value = "用户性别")
    private Integer sex;
    /**
     * 年龄
     */
    @ApiModelProperty(value = "用户年龄")
    private Long age;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = " 用户头像")
    private String picImg;
}
