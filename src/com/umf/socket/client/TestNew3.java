package com.umf.socket.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.umf.socket.client
 * @Author:LiuYuKun
 * @Date:2020/3/25 15:44
 * @Description:
 */
public class TestNew3 {
    public static void main(String[] args){
        List<String> list = new ArrayList(){{
            add("a");
            add("b");
            add("c");
            add("d");
        }};
        Map<String,List<String>> map = new HashMap(){{
            put("1",14);
        }};
        for(Map.Entry<String,List<String>> entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }


    }
}
