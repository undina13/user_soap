package com.undina.user_soap.user;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name")
    private String name;


@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
@JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_login"),
        inverseJoinColumns = @JoinColumn(name = "roles_id"))
@ToString.Exclude
    private List<Role> roles;
}
