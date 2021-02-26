package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName  = ConstantPropertiesUtils.BUCKET_NAME;

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = null; 

        try {
            inputStream = file.getInputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = file.getOriginalFilename();

        String s = UUID.randomUUID().toString().replaceAll("-","");//replaceAll替换
        fileName = s+fileName;
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath+"/"+fileName;
        String  url =   "https://"+bucketName+"."+endpoint+"/"+fileName;
        System.out.println("url:"+url);
        ossClient.putObject(bucketName, fileName , inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }
}
