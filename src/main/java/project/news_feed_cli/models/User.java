package project.news_feed_cli.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "user_following", joinColumns = {@JoinColumn(name = "user_id")},
                                        inverseJoinColumns = {@JoinColumn(name = "following_user_id")})
    private List<User> following;
}
