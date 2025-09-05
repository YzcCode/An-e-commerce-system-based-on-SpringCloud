package com.lingyang.item.service;

import com.lingyang.item.mapper.CategoryMapper;
import com.lingyang.item.pojo.Brand;
import com.lingyang.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author yangzicheng
 * @Date Created in 15:54 2023/3/6
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     *根据父节点来查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    //根据cid集合查询分类集合
    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        return categories.stream().map(Category::getName).collect(Collectors.toList());
    }

    //根据品牌信息查询商品分类
    public List<Category> selectCategoryByBid(Long bid){
        return this.categoryMapper.selectCategoryByBid(bid);
    }

    //新增分类
    public void saveCategory(Category category){
        category.setId(null);
        this.categoryMapper.insertSelective(category);
    }

    //修改分类
    public void updateCategory(Category category){
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    //删除分类
    public void deleteCategory(Long id) {
        this.categoryMapper.deleteByPrimaryKey(id);
    }
}
