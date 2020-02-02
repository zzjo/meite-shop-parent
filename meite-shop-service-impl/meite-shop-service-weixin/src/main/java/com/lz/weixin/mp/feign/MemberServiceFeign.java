package com.lz.weixin.mp.feign;

import com.lz.member.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author lz
 * @date 2020-01-16 17:21
 */
@FeignClient("app-lz-member")
public interface MemberServiceFeign extends MemberService {
}
