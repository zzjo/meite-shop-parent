package com.lz.member.mapper;

import com.lz.member.input.dto.UserInpDTO;
import com.lz.member.mapper.entity.UserDO;
import com.lz.member.output.dto.UserOutDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
    int saveUser(UserDO userDO);

    @Select("SELECT * FROM meite_user WHERE MOBILE=#{mobile};")
    UserDO existMobile(@Param("mobile") String mobile);

    @Select("SELECT count(*) FROM meite_user WHERE email=#{email} or USER_NAME=#{userName} ;")
    int existEmailOrUsername(@Param("email") String email,@Param("userName") String userName);
}
