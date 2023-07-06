package ru.sber.project_08.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.sber.project_08.repositories.CartRepository;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;


/**
 * Класс для хранения данных о клиентах
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "users", 
        uniqueConstraints = { 
          @UniqueConstraint(columnNames = "username"),
          @UniqueConstraint(columnNames = "email") }
        , schema = "products_petrov_vv")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"), schema = "products_petrov_vv")
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
    }
}