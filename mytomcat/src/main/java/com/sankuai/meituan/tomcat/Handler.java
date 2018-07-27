package com.sankuai.meituan.tomcat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 初始化请求信息
 */
public class Handler implements Runnable {
    private Socket socket;
    private PrintWriter writer;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html;charset=UTF-8\r\n\r\n");

            writer.println("<h1>Hello World!</h1>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
