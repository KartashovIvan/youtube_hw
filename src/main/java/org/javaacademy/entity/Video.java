package org.javaacademy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
@Entity
@Table(name = "videos")
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @OneToMany(mappedBy = "video")
    private List<Comment> comments;

    public Video(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
}
