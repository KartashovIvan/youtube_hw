package org.javaacademy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name = "video_id")
    @ToString.Exclude
    private Video video;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public Comment(String content, Video video, User user) {
        this.content = content;
        this.video = video;
        this.user = user;
    }
}
