package com.hukai.codingtest.service;

/**
 * 策略接口 用于处理字符串信息
 */
public interface RemoveStrategy {

    /**
     * 处理字符串信息
     * @param input 字符串信息
     * @return 处理后的字符串信息
     */
    String remove(String input);
}
