package com.jiang.eduservice.controller;

import com.jiang.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://tupian.qqw21.com/article/UploadPic/2019-3/201932522333323326.jpg");
    }
}
