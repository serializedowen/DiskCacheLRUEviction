package com.serializedowen.lrucache;

public interface ICacheService {
    public int get(String key);
    public void put(String key, int value, byte [] file );
}
