package com.lingyang.search.client;

import com.lingyang.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author yangzicheng
 * @Date Created in 22:42 2023/3/12
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
