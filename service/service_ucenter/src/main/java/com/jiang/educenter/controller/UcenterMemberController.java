package com.jiang.educenter.controller;


import com.jiang.commonutils.JwtUtils;
import com.jiang.commonutils.R;
import com.jiang.educenter.entity.UcenterMember;
import com.jiang.educenter.entity.vo.RegisterVo;
import com.jiang.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Shawn
 * @since 2022-03-05
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token=memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户idInfo(HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id 获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);

    }
}

