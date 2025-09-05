package com.lingyang.auth.client;

import com.lingyang.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author yangzicheng
 * @Date Created in 1:06 2023/3/19
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
