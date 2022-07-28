package com.atguigu.msm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ly
 * @create 2022-07-20-18:29
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;   //这是Spring整合redis的时候给我们封装好的一个对象

    //发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsg(@PathVariable String phone) {
        //1.先从redis中获取验证码，如果获取到直接返回
        String code;
        code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2.如果redis获取不到，则进行阿里云发送
        //生成随机数，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信的方法
        Boolean isSend = msmService.send(param, phone);
        if (isSend == true) {
            //发送成功，将验证码存入redis并设置有效时间-------第一个参数：存到redis中的key,第一个参数：存到redis中key对应的value,第一个参数：该key的有效时间，时间的单位，例如此处就是指该key的有效时长为5min
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else
            return R.error().message("短信发送失败");
    }
}
