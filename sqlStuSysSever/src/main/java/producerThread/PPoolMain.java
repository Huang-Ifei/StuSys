package producerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PPoolMain extends Thread {
    @Override
    public void run(){
        try {
            System.out.println("Producer pool online...");
            //创建serverSocket对象，同时为服务器注册端口
            ServerSocket serverSocket = new ServerSocket(9766);

            //创建线程池负责通信管道的任务
            ThreadPoolExecutor pool = new ThreadPoolExecutor(20 * 2, //核心线程数
                    20 * 2,//最大线程数
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
                pool.execute(new PPoolReaderRunnable(socket));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
