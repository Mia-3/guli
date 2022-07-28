package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly
 * @create 2022-07-12-17:09
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
