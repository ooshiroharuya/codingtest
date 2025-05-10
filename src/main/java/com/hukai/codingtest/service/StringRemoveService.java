package com.hukai.codingtest.service;

import org.springframework.stereotype.Service;

/**
 * @author hukai
 * @date 2025-05-09
 * @description 字符串处理-service层，用于处理字符串的移除操作，依赖于具体的策略来实现不同的移除逻辑
 */
@Service
public class StringRemoveService {

    /**
     * 移除字符串
     * @param input 输入的字符串
     * @param strategy 策略
     * @return 处理后的字符串
     */
    public String remove(String input, RemoveStrategy strategy) {
        return strategy.remove(input);
    }
}
