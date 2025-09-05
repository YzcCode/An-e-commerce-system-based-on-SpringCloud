package com.lingyang.item.api;

import com.lingyang.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author yangzicheng
 * @Date Created in 22:28 2023/3/12
 */
@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);
}
