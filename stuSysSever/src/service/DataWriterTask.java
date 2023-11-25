package service;

import sever.Message;
import sever.MessageType;
import sever.Student;

import java.util.ArrayList;
import java.util.TimerTask;

public class DataWriterTask extends TimerTask {
    private ArrayList<Message> list;
    public DataWriterTask(ArrayList<Message> list) {
        this.list = list;
    }
    @Override
    public void run() {
        if (!list.isEmpty()) {
            //写入算法
            System.out.println("Deal "+list.size()+" line");
            ArrayList<Message> writeInList = new ArrayList<>();
            String className = list.get(0).getClassName();
            for (Message m : list) {
                if (m.getMesType().equals(MessageType.MESSAGE_CHANGE_STU)){
                    new DataChange(m);
                }else if (m.getMesType().equals(MessageType.MESSAGE_DELETE_STU)){
                    new DataDelete(m);
                }else {
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
            }
            list.clear();
        }
    }
}
