package sa.edu.yamamh.riyadhgo.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class LoginModel {
    //declare two private fields: email, password
    private String email;
    private String password;
    public String getEmail() {
        return email;
    } // this method returns the current email value
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    //this method sets the email field with the provided value
    public void setPassword(String password) {
        this.password = password;
    }
    //this method sets the password field with the provided value
    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("email", this.getEmail());
        data.put("password", this.getPassword());
        return  data;
    }
    //this method creates a new LoginModel object from a Map
    public static LoginModel fromMap(Map<String,Object> data){
        LoginModel login = new LoginModel();
        login.setEmail(MappingUtils.getString("email",data));
        login.setPassword(MappingUtils.getString("password",data));
        return login;
    }
    public static LoginModel fromJSONObject(JSONObject data){
        LoginModel login = new LoginModel();
        login.setEmail(MappingUtils.getString("email",data));
        login.setPassword(MappingUtils.getString("password",data));
        return login;
    }

}
