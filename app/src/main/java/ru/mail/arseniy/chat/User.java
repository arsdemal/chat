package ru.mail.arseniy.chat;

/**
 * Здесь описана основная сущность ползователь, который имеет уникальное имя и открытый ключ
 */

public class User {

    private String name;
    private String login;
    private Integer openKey;
    private Boolean statusOnline;

    public User (String name, Integer openKey) {
        this.name = name;
        this.openKey = openKey;
    }

    public Boolean getStatusOnline() {
        return statusOnline;
    }

    public void setStatusOnline(Boolean statusOnline) {
        this.statusOnline = statusOnline;
    }
}
