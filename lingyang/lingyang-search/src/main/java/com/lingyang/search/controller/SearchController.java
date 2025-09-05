package com.lingyang.search.controller;

import com.lingyang.common.pojo.PageResult;
import com.lingyang.search.pojo.Goods;
import com.lingyang.search.pojo.SearchRequest;
import com.lingyang.search.pojo.SearchResult;
import com.lingyang.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author yangzicheng
 * @Date Created in 23:17 2023/3/14
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request){

        SearchResult result = this.searchService.search(request);
        if (result == null || CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
