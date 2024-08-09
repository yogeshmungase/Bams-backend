package com.management.studentattendancesystem.base.rest.model.Response;

import com.management.studentattendancesystem.base.db.model.User;

public class LoginResponse {

    private String status;

    private String message;

    private User user;

    private String token;

    private long expiresIn;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
