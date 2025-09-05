package com.lingyang.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author yangzicheng
 * @Date Created in 23:15 2023/3/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页

    private Map<String,Object> filter;

    private static final Integer DEFAULT_SIZE = 20;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}
