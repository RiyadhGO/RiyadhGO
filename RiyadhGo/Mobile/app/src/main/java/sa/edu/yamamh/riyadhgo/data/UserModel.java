package sa.edu.yamamh.riyadhgo.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.MappingUtils;

public class UserModel {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phone;

    public UserModel() {
    }

    public UserModel(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> data = new HashMap<>();
        data.put("userId", this.getUserId());
        data.put("email", this.getEmail());
        data.put("password", this.getPassword());
        data.put("name", this.getName());
        data.put("phone", this.getPhone());
        return  data;
    }


    public static UserModel fromMap(Map<String,Object> data){
        UserModel user = new UserModel();
        if(data == null)
            return user;
        user.setUserId(MappingUtils.getLong("userId",data));
        user.setEmail(MappingUtils.getString("email",data));
        user.setPassword(MappingUtils.getString("password",data));
        user.setName(MappingUtils.getString("name",data));
        user.setPhone(MappingUtils.getString("phone",data));
        return user;
    }
    public static UserModel fromJSONObject(JSONObject data){
        UserModel user = new UserModel();
        if(data == null)
            return user;
        user.setUserId(MappingUtils.getLong("userId",data));
        user.setEmail(MappingUtils.getString("email",data));
        user.setPassword(MappingUtils.getString("password",data));
        user.setName(MappingUtils.getString("name",data));
        user.setPhone(MappingUtils.getString("phone",data));
        return user;
    }

}
