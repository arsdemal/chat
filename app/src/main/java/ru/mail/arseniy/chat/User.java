package ru.mail.arseniy.chat;

/**
 * Здесь описана основная сущность ползователь, который имеет уникальное имя и открытый ключ
 */

public class User {

    String name;
    Integer openKey;

    public User (String name, Integer openKey) {
        this.name = name;
        this.openKey = openKey;
    }
}
