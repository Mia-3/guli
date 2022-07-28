package com.atguigu.msm.service;

import java.util.Map;

/**
 * @author ly
 * @create 2022-07-20-18:30
 */
public interface MsmService {
    Boolean send(Map<String, Object> param, String phone);
}
