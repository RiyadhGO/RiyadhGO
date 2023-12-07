package sa.edu.yamama.riyadhgo.models;

//The class AuthModel represents the credentials necessary to authorize access to the user 
public class AuthModel {
    
    private String email;
    private String password;
    private String name;
    private String phone;

    public AuthModel() {

    }

    public AuthModel(String email, String password) {
        this.email = email;
        this.password = password;
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

    

    
    
}
