package com.hukai.codingtest.service;

import org.springframework.stereotype.Component;

/**
 * @author Hukai
 * @date 2025/05/09
 * @description Stage 1 的策略，删除重复三次及以上字符
 */
@Component("stage1Strategy")
public class Stage1Strategy implements RemoveStrategy {

    /**
     * 删除输入字符串中重复出现三次及以上的字符，并返回处理后的字符串。
     * 示例：aabcccbbad -> aabbbad -> aaad -> d
     * 思路如下：
     * 1. 从左向右扫描字符串，检查是否有连续相同的字符。
     * 2. 如果字符连续重复三次及以上，则执行删除操作。
     * 3. 删除后，重置扫描位置，继续检查直到没有更多重复字符。
     *
     * @param input 输入字符
     * @return 输出字符
     */
    @Override
    public String remove(String input) {
        // 将输入字符串转换为 StringBuilder，以便更高效地进行字符删除操作
        StringBuilder sb = new StringBuilder(input);

        // 遍历字符串，直到索引跑完全部输入字符
        boolean changed = true;
        while (changed){
            changed = false;

            // 遍历用户字符串中的每个字符
            for (int i = 0; i < sb.length() - 2; i++) {
                char ch = sb.charAt(i);

                // 查找当前字符之后是否有相同字符
                int j;
                for (j = i + 1; j < sb.length(); j++) {
                    if (sb.charAt(j) != ch){
                        break;
                    }
                }

                // 如果重复字符大于等于3个，则删除，并重置索引
                if (j - i >= 3){
                    sb.delete(i, j);
                    i = -1;
                    changed = true;
                } else {
                    i = j - 1;
                }
            }
        }

        return sb.toString();
    }
}
