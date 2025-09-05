package com.lingyang.search.pojo;

import com.lingyang.common.pojo.PageResult;
import com.lingyang.item.pojo.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author yangzicheng
 * @Date Created in 17:19 2023/3/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult extends PageResult {
    private List<Map<String,Object>> categories;

    private List<Brand> brands;

    private List<Map<String,Object>> specs;

    public SearchResult(Long total, Integer totalPage, List items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
