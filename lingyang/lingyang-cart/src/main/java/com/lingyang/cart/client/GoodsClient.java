package com.lingyang.cart.client;

import com.lingyang.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author yangzicheng
 * @Date Created in 11:22 2023/3/19
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
