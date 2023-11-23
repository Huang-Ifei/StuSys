package common;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1;
    private String mesType;//消息类型
    private String content;//消息内容（json串）
    private String className;//消息（分区名称）

    public Message() {
    }

    public Message(String mesType, String content, String className) {
        this.mesType = mesType;
        this.content = content;
        this.className = className;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}