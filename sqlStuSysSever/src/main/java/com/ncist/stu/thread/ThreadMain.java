package com.ncist.stu.thread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadMain extends Thread{
    @Override
    public void run() {
        try{
            System.out.println("NCIST Students Management System online...");
            //创建serverSocket对象，同时为服务器注册端口
            ServerSocket serverSocket = new ServerSocket(9768);

            ThreadPoolExecutor pool = new ThreadPoolExecutor(1000, //核心线程数
                    1000,//最大线程数
                    0, //最大空闲时间
                    TimeUnit.SECONDS,//时间单位
                    new ArrayBlockingQueue<>(8), //阻塞队列
                    Executors.defaultThreadFactory(), //线程工程
                    new ThreadPoolExecutor.AbortPolicy());//拒绝策略

            while (true) {
                //使用serverSocket对象调用accept方法等待客户端连接
                Socket socket = serverSocket.accept();
                System.out.println("___Client online：" + socket.getRemoteSocketAddress());
                //把通信管道交给独立的线程处理
                pool.execute(new ThreadRunnable(socket));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
