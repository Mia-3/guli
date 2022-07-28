package com.atguigu.eduservice.client;

/**
 * @author ly
 * @create 2022-07-25-9:44
 */
public class OrdersDegradeFeignClient implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
