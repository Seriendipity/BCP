package com.example.bcp.ai;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.bcp.entity.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/ai")
public class Qwen {
    public static GenerationResult callWithMessage(String question) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant to help users with their course-related questions.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(question)
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                //.apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .apiKey("sk-5568e0006f834f3099a822f751b41691")
                // 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                .model("qwen-turbo")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }

    @PostMapping("/aiService")
    public Result aiService( @RequestParam String question){
        try {
            GenerationResult result = callWithMessage(question);
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());
            return Result.success(result.getOutput().getChoices().get(0).getMessage().getContent());
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("错误信息："+e.getMessage());
            System.out.println("请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code");
        }
        return Result.error("服务调用失败");
    }

}