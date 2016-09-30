package command.user;

import com.google.gson.Gson;

/**
 * Created by jihoon on 9/30/2016.
 */
public class LoginObject {
    private String username;
    private String password;

    public LoginObject (String username, String password){
        this.username = username;
        this.password = password;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
