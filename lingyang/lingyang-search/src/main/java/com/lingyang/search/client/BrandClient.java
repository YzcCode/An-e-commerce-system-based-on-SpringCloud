package com.lingyang.search.client;

import com.lingyang.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author yangzicheng
 * @Date Created in 22:41 2023/3/12
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
