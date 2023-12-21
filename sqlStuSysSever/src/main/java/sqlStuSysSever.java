import producerThread.PPoolMain;
import readerThread.RPoolMain;

import java.net.InetAddress;


public class sqlStuSysSever {
    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("SqlStuSysSever online in [" + address.getHostName() + "]");
        Thread pPool = new PPoolMain();
        Thread rPool = new RPoolMain();
        pPool.start();
        rPool.start();
    }
}
