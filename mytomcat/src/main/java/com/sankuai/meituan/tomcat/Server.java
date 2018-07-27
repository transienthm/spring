package com.sankuai.meituan.tomcat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author alcalde
 * @see(功能介绍) 模拟Tomcat的服务启动类
 * @version 1.0
 *
 */
public class Server {
    private static ServerSocket serverSocket;
    /**
     * Java中对线程池定义的一个接口
     */
    private static ExecutorService executorService;
    private final static int POOL_SIZE = 15;

    private static int port = 8080;

    public void start() {
        try {
            serverSocket = new ServerSocket(port);

            Socket socket;
            System.out.println("Starting ProtocolHandler [ " + port + " ]");
            executorService = Executors.newFixedThreadPool(POOL_SIZE);

            while (true) {
                socket = serverSocket.accept();
                System.out.println("发现请求");



                executorService.execute(new Handler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
