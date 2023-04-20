package org.lgq.iot.chatgpt.util;

import java.net.InetSocketAddress;
import java.net.Proxy;


public class ProxyUtil {


    /**
     * http 代理
     * @param ip
     * @param port
     * @return
     */
    public static Proxy http(String ip, int port) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }

    /**
     * socks5 代理
     * @param ip
     * @param port
     * @return
     */
    public static Proxy socks5(String ip, int port) {
        return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
    }

    /**
     * 获取本地代理，需要知道本地代理的端口号
     * @return
     */
    public static Proxy getLocalProxy() {
        return http("127.0.0.1", 10809);
    }
}
