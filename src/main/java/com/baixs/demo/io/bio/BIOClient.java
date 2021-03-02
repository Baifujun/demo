package com.baixs.demo.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOClient {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.submit(() -> {
                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1", 8888);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(("Hello, NIO Server, I'm NIO Client " + finalI).getBytes());
                    byte[] buffer = new byte[1024];
                    socket.getInputStream().read(buffer);
                    System.out.println(new String(buffer));
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
