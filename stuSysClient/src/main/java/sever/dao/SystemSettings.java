package sever.dao;

public class SystemSettings {
    public String username = "root";
    public String password = "root";
    public String id="3306";
    public void setUsername(String username) {
        this.username = username;
        System.out.println(this.username);
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(String id){
        this.id=id;
    }
}
