package com.hukai.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class Stage1StrategyTest {

    @Autowired
    private MockMvc mockMvc;

    // -------------------------正例测试----------------------------------
    @Test
    @DisplayName("标准案例")
    void testStage1StrategyNormalCase() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aabcccbbad")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("d"));
    }

    @Test
    @DisplayName("无重复案例")
    void testStage1NoSameCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "abcdefg")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("abcdefg"));
    }

    @Test
    @DisplayName("全重复案例")
    void testStage1AllDeleted() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaabbbccc")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(""));
    }

    // -------------------------边界测试----------------------------------
    @Test
    @DisplayName("两个相同字符")
    void testStage1SameTwoCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aa")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("aa"));
    }

    @Test
    @DisplayName("多个连续两个相同字符")
    void testSameTwoCharacterButMoreDifferentCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aabbccdd")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("aabbccdd"));
    }

    @Test
    @DisplayName("多个连续两个相同字符")
    void testThreeSameCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaa")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(""));
    }


    // -------------------------反例测试----------------------------------
    @Test
    @DisplayName("非法输入参数案例")
    void testStage1InvalidInput() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "123abc")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }

    @Test
    @DisplayName("非法输入空串案例")
    void testStage1InvalidInputBlank() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }

    @Test
    @DisplayName("非法策略配置案例")
    void testStage1InvalidStrategyName() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaa")
                        .param("strategy", "notAValidStrategy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid strategy param"));
    }

    // -------------------------特殊字符测试----------------------------------
    @Test
    @DisplayName("大小写测试案例")
    void testCaseSensitive() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaaAAA")
                        .param("strategy", "stage1Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(""));
    }
}

