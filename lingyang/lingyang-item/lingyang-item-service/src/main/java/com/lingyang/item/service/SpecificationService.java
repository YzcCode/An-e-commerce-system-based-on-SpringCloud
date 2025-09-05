package com.lingyang.item.service;

import com.lingyang.item.mapper.SpecGroupMapper;
import com.lingyang.item.mapper.SpecParamMapper;
import com.lingyang.item.pojo.SpecGroup;
import com.lingyang.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 21:31 2023/3/9
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {

        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        return this.specGroupMapper.select(record);
    }
    /**
     * 根据组id查询参数组
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.specParamMapper.select(record);
    }

    public List<SpecGroup> queryGroupsWithParam(Long cid) {
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(group ->{
            List<SpecParam> params = this.queryParams(group.getId(), null, null, null);
            group.setParams(params);
        });
        return groups;
    }


    //根据cid新增分组
    public void insertGroup( SpecGroup specGroup){
        this.specGroupMapper.insertSelective(specGroup);
    }

    //根据cid修改分组
    public void updateGroup( SpecGroup specGroup){
        this.specGroupMapper.updateByPrimaryKey(specGroup);
    }

    //根据cid删除分组
    public void deleteGroup(Long cid){
        this.specGroupMapper.deleteByPrimaryKey(cid);
    }

    public void insertParam(SpecParam specParam) {
        this.specParamMapper.insertSelective(specParam);
    }

    public void updateParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }

    public void deleteParam(Long pid) {
        this.specParamMapper.deleteByPrimaryKey(pid);
    }
}
