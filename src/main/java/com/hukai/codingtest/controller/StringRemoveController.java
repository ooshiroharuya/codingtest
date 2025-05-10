package com.hukai.codingtest.controller;

import com.hukai.codingtest.common.Response;
import com.hukai.codingtest.service.RemoveStrategy;
import com.hukai.codingtest.service.StringRemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @author Hukai
 * @date 2025/05/09
 * @description 字符串处理-controller层，提供API接口用于处理用户输入的字符串，移除其中的重复字符
 */
@RestController
@RequestMapping("/api")
public class StringRemoveController {

    @Autowired
    private StringRemoveService stringRemoveService;

    private final Map<String, RemoveStrategy> removeStrategyMap;

    @Autowired
    private StringRemoveController(Map<String, RemoveStrategy> removeStrategyMap) {
        this.removeStrategyMap = removeStrategyMap;
    }

    /**
     * 处理用户输入的字符串，移除其中的重复字符
     * @param input 用户输入的字符串
     * @param strategy 用户需要使用的策略
     * @return 处理后的字符串
     */
    @GetMapping("/remove")
    public ResponseEntity<Response<String>> remove(@RequestParam("input") String input,
                                                   @RequestParam("strategy") String strategy) {

        RemoveStrategy removeStrategy = removeStrategyMap.get(strategy);

        // 非法输入校验
        Response<String> response = this.invalidParamCheck(input, removeStrategy);

        if (Objects.nonNull(response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 调用服务层处理字符串
        return new ResponseEntity<>(Response.success(stringRemoveService.remove(input.toLowerCase(), removeStrategy)),
                HttpStatus.OK);
    }

    /**
     * 输入参数校验
     * @param input 用户输入的字符串
     * @param strategy 删除策略
     */
    private Response<String> invalidParamCheck(String input, RemoveStrategy strategy){
        // 校验策略是否可用
        if (strategy == null) {
            return Response.error(400, "Invalid strategy param");
        }

        // 检查输入合法性
        if (input == null || input.isBlank() || !input.matches("^[a-zA-Z]+$")) {
            return Response.error(400, "Invalid input");
        }

        return null;
    }
}
