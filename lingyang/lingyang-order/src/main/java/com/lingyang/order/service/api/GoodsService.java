package com.lingyang.order.service.api;

import com.lingyang.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "lingyang-gateway", path = "/api/item")
public interface GoodsService extends GoodsApi {
}
