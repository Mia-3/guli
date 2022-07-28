package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.UUID;

/**
 * @author ly
 * @create 2022-07-10-16:29
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //获取文件名
        String fileName = file.getOriginalFilename();
        //在文件名中添加中添加随机唯一的值
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        fileName = uuid+fileName;
        //获取当前事件并格式化
        String dataPath = new DateTime().toString("yyyy/MM/dd");
        //拼接存储路径
        fileName = dataPath+"/"+fileName;
        try {
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            // 调用oss方法实现上传，第二个参数用来指定你上传到oss上后文件叫啥，可以带路径/a/b/c.jpg，也可以直接写文件名c.jpg
            ossClient.putObject(bucketName, fileName, inputStream);

            //把上传到阿里云的oss的路径手动拼接出来
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (Exception e) {

        } finally {
            //关闭oss对象
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
