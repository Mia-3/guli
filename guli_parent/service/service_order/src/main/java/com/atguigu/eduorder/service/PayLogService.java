package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-22
 */
public interface PayLogService extends IService<PayLog> {

    //生成支付二维码
    Map createNative(String orderNo);

    //查询订单状态
    Map<String, String> queryPayStatus(String orderNo);

    //向支付表中添加记录，更新订单表状态
    void updateOrderStatus(Map<String, String> map);
}
