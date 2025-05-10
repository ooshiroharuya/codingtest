package com.hukai.codingtest.service;

import org.springframework.stereotype.Component;

/**
 * @author Hukai
 * @date 2025/05/09
 * @description Stage 2 的策略，替换并最终删除重复三次及以上字符
 */
@Component("stage2Strategy")
public class Stage2Strategy implements RemoveStrategy{

    /**
     * 替换或删除重复三次及以上的字符串，并返回处理后的字符串。
     * 思路如下：
     * 1. 从左向右扫描字符串，检查是否有连续相同的字符。
     * 2. 如果有连续相同的字符，需要注意：
     * - 如果是a，直接删除
     * - 如果不是a，替换成26个字符中的上一个字符，如 b -> a，如 z -> y
     * 3. 处理完成之后返回给用户
     *
     * @param input 字符串信息
     * @return 处理后的字符串信息
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

                // 如果重复字符大于等于3个：是a，则删除；不是a，则替换成字母表中的上一个字符
                if (j - i >= 3) {
                    if ('a' != ch){
                        char replacement = (char) (ch - 1);
                        sb.replace(i, j, Character.toString(replacement));
                    } else {
                        sb.delete(i, j);
                    }
                    i = -1; // 重置索引，使外层 for 循环从头重新开始扫描（由于 i++ 会紧接执行，所以设为 -1）
                    changed = true;
                } else {
                    // 否则，将索引设置为非重复字符的索引（同上，会触发i++因此减1）
                    i = j - 1;
                }
            }
        }

        return sb.toString();
    }
}
