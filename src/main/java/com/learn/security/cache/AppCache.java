package com.learn.security.cache;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppCache {

    public Map<String,String> appCache;

    @PostConstruct
    public init(){
        appCache.put()
    }

}
