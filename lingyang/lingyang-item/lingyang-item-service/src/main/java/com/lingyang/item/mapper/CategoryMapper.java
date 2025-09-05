package com.lingyang.item.mapper;

import com.lingyang.item.pojo.Brand;
import com.lingyang.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 15:53 2023/3/6
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {
    @Select("select * from tb_category_brand a inner join tb_category b on a.category_id=b.id where a.brand_id=#{bid}")
    List<Category> selectCategoryByBid(Long bid);
}
