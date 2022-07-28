package com.atguigu.eduorder.client;

import com.atguigu.commonutils.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ly
 * @create 2022-07-24-12:30
 */
@FeignClient("service-ucenter")
@Component
public interface UcernterClient {

    //根据用户id获取用户信息
    @GetMapping("/educenter/ucenter-member/getUserInfoOrder/{id}")
    UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
