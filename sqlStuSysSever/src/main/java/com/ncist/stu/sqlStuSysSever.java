package com.ncist.stu;

import com.ncist.stu.thread.ThreadMain;

import java.net.InetAddress;


public class sqlStuSysSever {
    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("SqlStuSysSever online in [" + address.getHostName() + "]");
        Thread main = new ThreadMain();
        main.start();
    }
}
