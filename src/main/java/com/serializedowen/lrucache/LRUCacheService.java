package com.serializedowen.lrucache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class LRUCacheService implements ICacheService{
    @Resource
    private LRUCache cache;


    @Override
    public int get(String key) {
        return this.cache.get(key);
    }

    @Override
    public void put(String key, int value, byte[] file) {
        try {
            this.cache.put(key, value, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
