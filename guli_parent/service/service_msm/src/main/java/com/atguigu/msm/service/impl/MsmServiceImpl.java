package com.atguigu.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author ly
 * @create 2022-07-20-18:31
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public Boolean send(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        //指定地域节点，accessKey和secret----------------固定写法，改成自己的值就行
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAIq6nIPY09VROj", "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");
        //创建IAcsClient对象用于发送短信-------------固定写法
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关参数-----------------------------------固定写法，不改
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数-------------第一个参数name是固定的不改，第二个参数填自己的相应的值
        request.putQueryParameter("PhoneNumbers",phone);     //要发短信送给哪个手机号
        request.putQueryParameter("SignName", "谷粒学院在线教育网站");      //签名名称，在阿里云签名管理处找到相应的签名名称
        request.putQueryParameter("TemplateCode", "SMS_246500148");        //模板code，在阿里云的模板管理找到相应的模板CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));     //要发送的验证码

        //最终发送
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        boolean success = response.getHttpResponse().isSuccess();
        return success;
    }
}
