package com.sankuai.meituan.tomcat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 初始化请求信息
 */
public class Handler implements Runnable {
    private String OUTPUT_HEADERS = "HTTP/1.1 200 OK\n" +
            "Content-type: text/html; charset=UTF-8\n" +
            "Content-Length:";
    private String OUTPUT_END_OF_HEADERS = "\n\n";
    private Socket socket;
    private PrintWriter writer;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            String output = "<h1>Hello World!</h1>";

            writer.println(OUTPUT_HEADERS + output.length() + OUTPUT_END_OF_HEADERS + output);
            writer.flush();

            System.out.println("hello!");
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
