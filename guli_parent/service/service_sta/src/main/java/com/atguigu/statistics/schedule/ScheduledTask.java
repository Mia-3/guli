package com.atguigu.statistics.schedule;

import com.atguigu.statistics.service.DailyService;
import com.atguigu.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ly
 * @create 2022-07-25-15:51
 */
@Component
public class ScheduledTask {

    @Autowired
    DailyService dailyService;

    //在每天凌晨1点，把前一天的数据数据进行统计
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }


    //表示没隔5秒执行一次该方法
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println("*********++++++++++++*****执行了");
//    }
}
