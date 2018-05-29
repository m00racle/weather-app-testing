package com.mooracle.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

/** ENTRY 6: Creating User @Entity
 *  1.  The User entity is a class implements UserDetails interface
 *  2.  UserDetails is a interface from the springframework security
 *  3.  We use (once again) BCrypt as password encoder
 *  4.  Because the User class implements Class from Spring security in it already includes username and password
 *  5.  The constructor looks like built for compatibility with UserDetails
 *
 * */

@Entity
public class User implements UserDetails {
    //first we instantiate the password Encoder
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    //field type id declaration
    @Id //use the Java.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //fields that is not presented as column but is essential in the User object and still get and set able
    private String firstName;
    private String lastName;

    //field username which also included as column in the database schema User
    @Column(unique = true)
    @Size(min = 4, max = 20)
    private String username;

    //field password also included in the column
    @Column(length = 100)
    private String password;

    //field is the user is enabled or not
    @Column(nullable = false)
    private boolean enabled;

    //field the user role, this will be connected this schema with the Role schema, let's make the Role Class first
    @OneToOne //one user can only have one role
    @JoinColumn(name = "role_id")
    private Role role;

    //here the construction is a bit different (not default constructor) all the initial values are null except enabled
    public User() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.enabled = true;
        this.role = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(role.getName());//this need role.getName()!
    }

    @Override //this is different from the course but let's just return it as the course:
    public String getPassword() {
        return password;
    }

    //setter for password: remember we use encryption from PASSWORD_ENCODER:
    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    @Override //this is also different but let's return as the course would
    public String getUsername() {
        return username;
    }

    //setter for username is just like normal setter:
    public void setUsername(String username) {
        this.username = username;
    }

    //getters and setters for the rest of other fields:

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //Account will be preserved after log out
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //Account will not be locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //credential will be preserved after log out
    }

    //this is the getter for enabled (it is part of the UserDetails interface)
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
