package com.jiang.eduservice.client;

import com.jiang.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    //出错后执行
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频 time out");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频 time out");
    }
}
