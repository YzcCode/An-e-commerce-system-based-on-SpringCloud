package com.lingyang.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 22:29 2023/3/12
 */
@RequestMapping("category")
public interface CategoryApi {
    @GetMapping
    public List<String> queryNameByIds(@RequestParam("ids")List<Long> ids);
}
