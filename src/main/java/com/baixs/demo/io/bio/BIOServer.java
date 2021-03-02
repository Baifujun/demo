package com.baixs.demo.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("等待客户端连接...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("客户端" + socket.getRemoteSocketAddress().toString().substring(1) + "已连接");
            Executors.newCachedThreadPool().submit(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    byte[] buffer = new byte[1024];
                    int length = inputStream.read(buffer);
                    socket.getOutputStream().write(buffer, 0, length);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
