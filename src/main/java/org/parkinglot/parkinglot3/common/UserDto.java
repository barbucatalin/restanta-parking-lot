package org.parkinglot.parkinglot3.common;

import java.io.Serializable;
public class UserDto implements Serializable {

    private final Long userId;
    private final String loginName;
    private final String contactEmail;

    // Constructor cu denumiri de argumente modificate
    public UserDto(Long id, String name, String email) {
        this.userId = id;
        this.loginName = name;
        this.contactEmail = email;
    }

    // Metodele Getter păstrează semnătura originală pentru compatibilitatea cu JSP/EL
    public Long getId() {
        return this.userId;
    }

    public String getUsername() {
        return this.loginName;
    }

    public String getEmail() {
        return this.contactEmail;
    }
}