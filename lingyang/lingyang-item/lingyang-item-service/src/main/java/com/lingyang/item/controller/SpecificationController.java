package com.lingyang.item.controller;

import com.lingyang.item.pojo.SpecGroup;
import com.lingyang.item.pojo.SpecParam;
import com.lingyang.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author yangzicheng
 * @Date Created in 21:32 2023/3/9
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid){

        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据组id查询参数组
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "generic",required = false)Boolean generic,
            @RequestParam(value = "searching",required = false)Boolean searching
    ){

        List<SpecParam> params = this.specificationService.queryParams(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    //根据组id查询参数组并且携带规格参数
    @GetMapping("group/param/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsWithParam(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupsWithParam(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    //根据cid新增分组
    @PostMapping("group")
    public ResponseEntity<Void> insertGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.insertGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据cid修改分组
    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.updateGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据cid删除分组
    @DeleteMapping("group/{cid}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("cid") Long cid){
        this.specificationService.deleteGroup(cid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据pid新增分组
    @PostMapping("param")
    public ResponseEntity<Void> insertParam(@RequestBody SpecParam specParam){
        this.specificationService.insertParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据pid修改分组
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam){
        this.specificationService.updateParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据pid删除分组
    @DeleteMapping("param/{pid}")
    public ResponseEntity<Void> deleteParam(@PathVariable("pid") Long pid){
        this.specificationService.deleteParam(pid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
