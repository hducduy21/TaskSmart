package com.tasksmart.sharedLibrary.configs.interceptors;

import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class is used to intercept the request to add the Authorization header to the request.
 * This class implements the RequestInterceptor interface from the Feign library.
 * This class is used to add the Authorization header to the request when the request is sent to the other services.
 *
 * @author Duy Hoang
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null) {
            log.error("No request attributes found");
            throw new BadRequest("No request attributes found");
        }
        String authHeader = attributes.getRequest().getHeader("Authorization");

        if(StringUtils.hasText(authHeader)) {
            requestTemplate.header("Authorization", authHeader);
        }
    }
}
