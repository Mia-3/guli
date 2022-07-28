package com.atguigu.statistics.controller;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class DailyController {
    @Autowired
    DailyService dailyService;

    //统计某一天的注册人数，生成统计数据
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.ok();
    }

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = dailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

