package com.pks.blog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"userName"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;



    /**user role column will be added . Here the user_id table is the joining table .
     *  The id column here will be user_id column
     *  The inverse join colum's role_id value refere to the roles table where the role_id is the id of roles table
     *  **/
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role>roles;

    public User(long id, String name, String userName, String email, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
