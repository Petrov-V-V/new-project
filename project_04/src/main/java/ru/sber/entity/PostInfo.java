package ru.sber.entity;

/**
 * Класс хранящий данные необходимые для отправки письма/посылки
 */
public class PostInfo {
    private String address;
    private String sender;

    public PostInfo(String address, String sender) {
        this.address = address;
        this.sender = sender;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
