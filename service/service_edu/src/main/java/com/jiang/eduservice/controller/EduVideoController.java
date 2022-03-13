package com.jiang.eduservice.controller;


import com.jiang.commonutils.R;
import com.jiang.eduservice.client.VodClient;
import com.jiang.eduservice.entity.EduVideo;
import com.jiang.eduservice.service.EduVideoService;

import com.jiang.servicebase.exceptionhandler.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    //注入VodClient
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){

        videoService.updateById(eduVideo);
        return R.ok();
    }
    //删除小节时删除对应阿里云视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id得到视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里是否有视频
        if(!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new ServiceException(20001,"删除视频失败，熔断器");
            }

        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

}

