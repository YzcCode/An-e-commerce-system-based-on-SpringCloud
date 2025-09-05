package com.lingyang.search.client;

import com.lingyang.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author yangzicheng
 * @Date Created in 22:39 2023/3/12
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
