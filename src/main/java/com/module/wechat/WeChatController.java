package com.module.wechat;

import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

@Controller
public class WeChatController {

    private static final String token = "lizhuangjiehuangqinhong";

    @ResponseBody
    @SneakyThrows(IOException.class)
    @RequestMapping("/wechat")
    public void getWeChatResponse(HttpServletRequest request, HttpServletResponse response){
        System.out.println("========message from /wechat path========= ");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.entrySet().stream().forEach(item -> {
            System.out.println("key:" + item.getKey());
            String[] value = item.getValue();
            Arrays.stream(value).forEach(valueItem -> System.out.println("value item: " + valueItem));
        });

        PrintWriter writer = response.getWriter();
        if(parameterMap.keySet().containsAll(Arrays.asList("signature", "echostr", "timestamp", "nonce"))){
            if(checkSignature(parameterMap.get("signature")[0], parameterMap.get("timestamp")[0], parameterMap.get("nonce")[0])){
                System.out.println("微信信息请求校验成功");
                writer.write(parameterMap.get("echostr")[0]);
            }
        }

//        writer.write(token);
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    private boolean checkSignature(String signature, String timestamp, String nonce){
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);

        StringBuilder sb = new StringBuilder();
        Arrays.stream(array).forEach(stringItem -> sb.append(stringItem));

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(sb.toString().getBytes());
        String result = ByteStringUtil.byteToString(digest);
        System.out.println(result);
        System.out.println(signature);
        System.out.println(result.equalsIgnoreCase(signature));

        return true;
    }
}
