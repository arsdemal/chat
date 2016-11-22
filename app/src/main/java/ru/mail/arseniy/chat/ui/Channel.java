package ru.mail.arseniy.chat.ui;

public class Channel {
    String nameChannel;
    int countUser;
    String descChannel;

    public Channel(String nameChannel, int countUser, String descChannel) {
        this.nameChannel = nameChannel;
        this.countUser = countUser;
        this.descChannel = descChannel;
    }
}
