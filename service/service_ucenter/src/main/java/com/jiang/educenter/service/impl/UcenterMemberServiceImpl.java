package com.jiang.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.commonutils.JwtUtils;
import com.jiang.commonutils.MD5;
import com.jiang.educenter.entity.UcenterMember;
import com.jiang.educenter.entity.vo.RegisterVo;
import com.jiang.educenter.mapper.UcenterMemberMapper;
import com.jiang.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.servicebase.exceptionhandler.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Shawn
 * @since 2022-03-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //登录
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile=member.getMobile();
        String password=member.getPassword();

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new ServiceException(20001,"登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileMember==null){
            throw new ServiceException(20001,"账号不存在");
        }
        //判断密码
        //存储到数据库的密码是加密过的
        //吧输入的密码加密后再比较  MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new ServiceException(20001,"密码错误");
        }
        //判断用户是否禁用
        if(mobileMember.getIsDeleted()){
            throw new ServiceException(20001,"该用户被禁用");

        }
        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken= JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
        return jwtToken;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code=registerVo.getCode();
        String mobile=registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //非空判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw new ServiceException(20001,"注册失败");
        }
        //判断验证码 获取redis验证码
        String redisCode=redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new ServiceException(20001,"注册失败");
        }

        //判断手机号是否重复，查数据表
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new ServiceException(20001,"注册失败");
        }

        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        baseMapper.insert(member);

    }
}
