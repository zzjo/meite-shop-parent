package com.lz.weixin.mp.feign;

import com.lz.base.BaseResponse;
import com.lz.member.MemberService;
import com.lz.member.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lz
 * @date 2020-01-16 17:21
 */
@FeignClient("app-lz-member")
public interface MemberServiceFeign extends MemberService {
}
