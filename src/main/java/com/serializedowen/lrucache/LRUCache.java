package com.serializedowen.lrucache;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;


public class LRUCache{

    private final int CAPACITY = 2000000;
    private LinkedHashMap<String, Integer> cache;

    private int occupied = 0;

    private Lock lock;

    public LRUCache(){

        try {
            Files.deleteIfExists(Paths.get("wav"));
            Files.createDirectory(Paths.get("wav"));

        } catch (IOException e) {
            e.printStackTrace();
        }




        cache=new LinkedHashMap<String,Integer>(10,0.75f,true){
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<String, Integer> eldest) {

                Boolean removed = occupied > CAPACITY;

                if (removed) {
                    try {
                        Files.delete(Paths.get("wav/" + eldest.getKey() + ".wav"));
                        occupied -= eldest.getValue();
                    } catch (IOException e) {
                        e.printStackTrace();
                        removed = false;
                    }
                }

                return removed;
            }



        };
    }

    public int get(String key) {
        return cache.getOrDefault(key, -1);
    }

    public void put(String key, int value, byte [] file ) throws IOException {

        if (this.get(key) == -1){
            Files.write(Paths.get("wav/" + key + ".wav"), file);
            occupied += file.length;
        }

        cache.put(key, value);

    }

}
