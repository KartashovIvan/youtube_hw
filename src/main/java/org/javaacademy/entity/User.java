package org.javaacademy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String nickname;
    @OneToMany(mappedBy = "user")
    private List<Video> videos;

    public User(String nickname) {
        this.nickname = nickname;
    }
}
