package org.lgq.iot.sdk.chatgpt.util;

import cn.hutool.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class ApiKeyUtil {

    private static String APP_KEY;

    public static String getApiKey() {
      if (APP_KEY != null) {
          return APP_KEY;
      }
        InputStream inputStream = ApiKeyUtil.class.getClassLoader().getResourceAsStream("API_KEY.json");
        if (inputStream == null) {
            throw new NullPointerException();
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
