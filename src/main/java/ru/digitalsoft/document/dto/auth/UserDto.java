package ru.digitalsoft.document.dto.auth;

import ru.digitalsoft.document.dao.entity.auth.UserEntity;

import java.sql.Timestamp;


public class UserDto {
    private long id;
    private String name;
    private String surname;
    private String middlename;
    private String email;
    private String password;
    private String salt;
    private Timestamp registerDate;

    public UserDto() {
    }

    public UserDto(UserEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.surname = entity.getSurname();
        this.middlename = entity.getMiddlename();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.salt = entity.getSalt();
        this.registerDate = entity.getRegisterDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }
}
