package com.lz.member.mapper;

import com.lz.member.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
    int saveUser(UserEntity userEntity);

    @Select("SELECT * FROM meite_user WHERE MOBILE=#{mobile};")
    UserEntity existMobile(@Param("mobile") String mobile);
}
