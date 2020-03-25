package com.umf.socket.client;

import java.math.BigDecimal;

/**
 * @Author:LiuYuKun
 * @Date:2019/12/6
 */
public class Test0 {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("50025");
        BigDecimal b = new BigDecimal("50000.000000000");
        System.out.println(a.compareTo(b));
    }
}
