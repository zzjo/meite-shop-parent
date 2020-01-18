package com.lz.member.feign;

import com.lz.weixin.VerifierCodeService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author lz
 * @date 2020-01-17 18:02
 */
@FeignClient("app-lz-weixin")
public interface VerifierCodeServiceFeign extends VerifierCodeService {
}
