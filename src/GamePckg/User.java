package GamePckg;

public class User {
	 
    private int id;
    private int login;
    private int password;
 
    public User(int id, int login, int password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
 
    public User() {
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public int getLogin() {
        return login;
    }
 
    public void setLogin(int login) {
        this.login = login;
    }
 
    public int getPassword() {
        return password;
    }
 
    public void setPassword(int password) {
        this.password = password;
    }
}