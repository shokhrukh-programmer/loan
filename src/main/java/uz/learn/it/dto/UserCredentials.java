package uz.learn.it.dto;

import java.util.StringJoiner;

public class UserCredentials {
    private int id;
    private String username;
    private String password;
    private int clientId;

    public UserCredentials(int id, String username, String password, int clientId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserCredentials.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("clientId=" + clientId)
                .toString();
    }
}
