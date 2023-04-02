package org.lgq.iot.sdk.chatgpt.util;

import cn.hutool.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class ApiKeyUtil {

    private static String APP_KEY;

    /**
     * 你需要有一个chatGPT账户，并且找到你的APIKey，在类路径创建API_KEY.json文件，
     * 内容为：{"api_key":"sk-***********..."}
     * 寻找api_key的地址：https://platform.openai.com/account/api-keys
     * @return
     */
    public static String getApiKey() {
      if (APP_KEY != null) {
          return APP_KEY;
      }
        InputStream inputStream = ApiKeyUtil.class.getClassLoader().getResourceAsStream("API_KEY.json");
        if (inputStream == null) {
            throw new NullPointerException("file in classpath may be null !");
        }
        Scanner scanner = new Scanner(inputStream);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }
        APP_KEY = new JSONObject(sb.toString()).getStr("api_key");
        return APP_KEY;
    }
}
