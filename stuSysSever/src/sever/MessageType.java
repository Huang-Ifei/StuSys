package sever;

public interface MessageType {
    String MESSAGE_CREATE="1";//创建class和分区
    String MESSAGE_COMM_MES="2";//普通信息包
    String MESSAGE_READ="3";//请求按条读取分区文件夹中的数据
    String MESSAGE_READ_BY_NAME="4";
    String MESSAGE_READ_BY_ID="5";
    String MESSAGE_READ_CLASS="6";
    String MESSAGE_CHANGE_STU="7";
    String MESSAGE_DELETE_STU="8";
}
