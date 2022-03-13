package com.jiang.educenter.service;

import com.jiang.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Shawn
 * @since 2022-03-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录
    String login(UcenterMember member);
    //注册
    void register(RegisterVo registerVo);
}
