package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-07-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //获取登录的手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        //手机号是否正确，也就是查数据库中是否有该手机号
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查出来的对象是否为空
        if(mobileMember == null){
            throw new GuliException(20001,"手机号不存在");
        }
        //判断手机号和密码是否对应
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"手机号和密码不对应");
        }
        if(mobileMember.getIsDisabled()){
            throw new GuliException(20001,"该用户已被禁用");
        }

        //登录成功
        //生成token字符串，使用jwt生成
        return JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
    }

//    //注册（有验证码的）
//    @Override
//    public void register(RegisterVo registerVo) {
//        //获取注册的数据
//        String code = registerVo.getCode();
//        String mobile = registerVo.getMobile();
//        String nickname = registerVo.getNickname();
//        String password = registerVo.getPassword();
//        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
//            throw new GuliException(20001,"注册失败");
//        }
//
//        //判断输入的验证码和redis中的验证码是否一样
//        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)){
//            throw  new GuliException(20001,"验证码输入错误");
//        }
//
//        //判断手机号是否重复，标中如果存在相同的手机号不进行添加
//        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
//        wrapper.eq("mobile",mobile);
//        Integer count = baseMapper.selectCount(wrapper);
//        if(count > 0){
//            throw new GuliException(20001,"手机号已存在");
//        }
//
//        //添加至数据库
//        UcenterMember member = new UcenterMember();
//        member.setMobile(mobile);
//        member.setNickname(nickname);
//        member.setPassword(MD5.encrypt(password));
//        member.setIsDisabled(false);
//        member.setAvatar("https://guli-edu-0.oss-cn-beijing.aliyuncs.com/2022/07/17/94f156d2c79c4a669d189e34b68ba7032.jpg");
//        baseMapper.insert(member);
//    }


//注册（没有验证码的）
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册失败");
        }

        //不验证验证码输入是否正确
//        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(1234)){
//            throw  new GuliException(20001,"验证码输入错误");
//        }

        //判断手机号是否重复，标中如果存在相同的手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliException(20001,"手机号已存在");
        }

        //添加至数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://guli-edu-0.oss-cn-beijing.aliyuncs.com/2022/07/17/94f156d2c79c4a669d189e34b68ba7032.jpg");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenidMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
