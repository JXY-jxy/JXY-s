package com.jiang.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
//import com.aliyun.oss.common.utils.StringUtils;
import java.util.List;

public class TestVod {

    public static void main(String[] args) {
/**
 * 本地文件上传接口
 *
 * @param accessKeyId
 * @param accessKeySecret
 * @param title
 * @param fileName
 */
            UploadVideoRequest request = new UploadVideoRequest("LTAI5tJTj4EYJTE9JeYNsWJe", "5IbhtbR8DMHJDmUAyR0EuIDA9td3L2", "本地上传测试",
                    "D:\\BaiduNetdiskDownload\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4");
            /* 可指定分片上传时每个分片的大小，默认为2M字节 */
            request.setPartSize(2 * 1024 * 1024L);
            /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
            request.setTaskNum(1);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadVideoResponse response = uploader.uploadVideo(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else {
                /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        }


    //  得到视频凭证
    public static void getPlayAuth(){
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tJTj4EYJTE9JeYNsWJe", "5IbhtbR8DMHJDmUAyR0EuIDA9td3L2");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {

            //设置请求参数
            request.setVideoId("ce9eb695f86048c3bd6d27bdb018728b");
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
    //  得到视频地址
    public static void getPlayUrl() throws Exception{
        String accessKeyId = "LTAI5tJTj4EYJTE9JeYNsWJe";
        String accessKeySecret = "5IbhtbR8DMHJDmUAyR0EuIDA9td3L2";
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        try {
            //设置请求参数
            //注意：这里只能获取非加密视频的播放地址
            request.setVideoId("ce9eb695f86048c3bd6d27bdb018728b");
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
