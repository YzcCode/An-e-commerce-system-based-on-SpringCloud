package com.lingyang.search.respository;

import com.lingyang.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author yangzicheng
 * @Date Created in 22:01 2023/3/13
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
