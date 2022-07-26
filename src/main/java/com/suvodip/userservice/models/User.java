package com.suvodip.userservice.models;


import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import javax.persistence.Id;
import javax.persistence.*;

import org.hibernate.mapping.Set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity @Data @NoArgsConstructor
public class User {
	
    public User(Long id, String name, String username, String password, Collection<Role> roles,
			boolean accountVerified) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.accountVerified = accountVerified;
	}

	@Id @GeneratedValue(strategy=AUTO)
    private Long id;

    private String name;

    @Column(unique=true)
    private String username;

    private String password;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
   
    @Column(columnDefinition="boolean default false")
    private boolean accountVerified = false;
    
    @OneToMany(mappedBy="user", targetEntity=VerificationToken.class)
    private java.util.Set tokens = new HashSet<>();
}
