package com.jiang.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.jiang.commonutils.R;
import com.jiang.servicebase.exceptionhandler.ServiceException;
import com.jiang.vod.service.VodService;
import com.jiang.vod.utils.ConstantVodUtils;
import com.jiang.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    //上传视频到阿里云
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){
        String videoId=vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }
    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();

        } catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(20001,"删除视频失败");
        }
    }
    //删除多个阿里云视频的方法
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List videoList){
        vodService.removeAlyVideo(videoList);
        return R.ok();
    }
}
