package com.hukai.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Stage2StrategyTest {

    @Autowired
    private MockMvc mockMvc;

    // ----------正例测试----------
    @Test
    @DisplayName("标准案例")
    void testStage2StrategyNormalCase() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aabcccbbad")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("d"));
    }

    @Test
    @DisplayName("无重复案例")
    void testStage2NoSameCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "abcdefg")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("abcdefg"));
    }

    @Test
    @DisplayName("全重复案例")
    void testStage2AllSameCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaabbbccc")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("ab"));
    }

    // ----------边界测试----------
    @Test
    @DisplayName("两个相同字符")
    void testStage2SameTwoCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aa")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("aa"));
    }

    @Test
    @DisplayName("多个连续两个相同字符")
    void testStage2SameTwoCharacterButMoreDifferentCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aabbccdd")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value("aabbccdd"));
    }

    @Test
    @DisplayName("多个连续两个相同字符")
    void testStage2ThreeSameCharacter() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaa")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(""));
    }

    // ----------反例测试----------
    @Test
    @DisplayName("非法输入参数案例")
    void testStage2InvalidInput() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "123abc")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }

    @Test
    @DisplayName("非法输入空串案例")
    void testStage2InvalidInputBlank() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }

    @Test
    @DisplayName("非法策略配置案例")
    void testStage2InvalidStrategyName() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaa")
                        .param("strategy", "notAValidStrategy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid strategy param"));
    }

    // ----------特殊测试----------
    @Test
    @DisplayName("大小写测试案例")
    void testStage2CaseSensitive() throws Exception {
        mockMvc.perform(get("/api/remove")
                        .param("input", "aaaAAA")
                        .param("strategy", "stage2Strategy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(""));
    }
}
