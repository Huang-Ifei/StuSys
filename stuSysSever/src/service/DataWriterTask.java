package service;

import sever.Message;

import java.util.ArrayList;
import java.util.TimerTask;

public class DataWriterTask extends TimerTask {
    private ArrayList<Message> list;
    private static int row=0;
    public DataWriterTask(ArrayList<Message> list) {
        this.list = list;
    }
    @Override
    public void run() {
        if (!list.isEmpty()) {
            //写入算法
            System.out.println("Write in "+list.size()+" line");
            ArrayList<Message> writeInList = new ArrayList<>();
            String className = list.get(0).getClassName();
            for (Message m : list) {
                row++;
                //如果classname不对了，且不是最后一个，就把原来的写入表写入，清除，将classname改成新的并添加到新的写入表
                if (!(m.getClassName().equals(className))&&!m.equals(list.get(list.size()-1))){
                    new WriteIn(writeInList,className);
                    writeInList.clear();
                    className=m.getClassName();
                    writeInList.add(m);
                }
                //如果classname不对了，且是最后一个，就把原来的写入表写入，清除，将classname改成新的并添加本项到新的写入表，再写入
                else if(!(m.getClassName().equals(className))&&m.equals(list.get(list.size()-1))){
                    new WriteIn(writeInList,className);
                    writeInList.clear();
                    className=m.getClassName();
                    writeInList.add(m);
                    new WriteIn(writeInList,className);
                    writeInList.clear();
                }
                //如果已经循环到最后一个那么把最后一个加上并写入写入表
                else if (m.equals(list.get(list.size()-1))){
                    writeInList.add(m);
                    new WriteIn(writeInList,className);
                    writeInList.clear();
                }
                //正常情况下添加
                else{
                    writeInList.add(m);
                }
            }
            list.clear();
        }
    }
}
