package com.atguigu.statistics.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ly
 * @create 2022-07-25-11:33
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //查询某一天的注册人数
    @GetMapping("/educenter/ucenter-member/countRegister/{day}")
    R countRegister(@PathVariable("day") String day);
}
