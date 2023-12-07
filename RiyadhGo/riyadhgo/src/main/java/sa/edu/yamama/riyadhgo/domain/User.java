package sa.edu.yamama.riyadhgo.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//The creation of table "users" in the database
@Entity
@Table(name="users")
public class User implements UserDetails {


//The creation of the variables user's ID, name, email, password, password salt, role, phone in the "users" table  
    @Id
    @GeneratedValue
    @Column(name="u_id")
    private Long userId;

    @Column(name="u_name")
    private String name;

    @Column(name="u_email", length = 100, unique =  true)
    private String email;

    @Column(name="u_password")
    private String password;

    @Column(name="password_salt")
    private String passwordSalt;

    @Column(name="u_role")
    private String role;

    @Column(name="u_phone")
    private String phone;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<FavoriteLocation> favoriteLocations;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getRole();
            }
        });
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String getPasswordSalt() {
        return passwordSalt;
    }
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public List<FavoriteLocation> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(List<FavoriteLocation> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    
    
}
