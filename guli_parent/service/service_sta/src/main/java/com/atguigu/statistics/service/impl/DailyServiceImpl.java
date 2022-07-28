package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.client.UcenterClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-07-25
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //添加记录之前删除表中相同日期的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer)registerR.getData().get("countRegister");

        //把获取数据添加到数据库统计分析表里
        Daily sta = new Daily();
        sta.setRegisterNum(countRegister);    //统计注册人数
        sta.setDateCalculated(day);           //统计的哪一天的数据
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<Daily> staList = baseMapper.selectList(wrapper);

        //前端要求json数组，对应到后端是一个集合，即后端的一个集合转换为json就是一个json数组
        //创建两个list集合，一个是日期list，一个是数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历查询出来的所有数据list集合
        for(int i = 0;i < staList.size();i++){
            date_calculatedList.add(staList.get(i).getDateCalculated());
            switch (type){
                case "login_num":
                    numDataList.add(staList.get(i).getLoginNum());
                    break;
                case"register_num":
                    numDataList.add(staList.get(i).getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(staList.get(i).getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(staList.get(i).getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
