package readerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RPoolMain {
    public static void main(String[]args)throws IOException {
        System.out.println("Reader_DataExchangePool online...");
        //创建serverSocket对象，同时为服务器注册端口
        ServerSocket serverSocket = new ServerSocket(9768);

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
            System.out.println("Client online：" + socket.getRemoteSocketAddress());
            //把通信管道交给独立的线程处理
            pool.execute(new RPoolReaderRunnable(socket));
        }
    }
}
