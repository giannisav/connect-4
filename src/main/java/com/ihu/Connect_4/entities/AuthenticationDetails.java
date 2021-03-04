package com.ihu.Connect_4.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authentication_details")
public class AuthenticationDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Column()
    private String nickname;

    public AuthenticationDetails() {}

    public AuthenticationDetails(String uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
