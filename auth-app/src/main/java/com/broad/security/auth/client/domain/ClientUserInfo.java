package com.broad.security.auth.client.domain;



import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ClientUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String accessToken;

    private Date expires;

    private String refreshToken;

    @Transient
    private List<Entry> entries = new ArrayList<>();


}
