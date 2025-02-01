package com.example.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;

    @ManyToOne
    @JoinColumn(name="sender_Authtoken")
    private Account sender;

    @ManyToOne
    @JoinColumn(name="receiver_Authtoken")
    private Account receiver;

    @Column(nullable = false)
    private String messageBody;

    private boolean isRead;

    private LocalDateTime messageDate=LocalDateTime.now();

    public Message() {}

    public Message(Account sender, Account receiver, String messageBody) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageBody = messageBody;
        this.isRead = false;
    }

    public Long getMessageId() {
        return messageId;
    }
    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }


    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getSentAt() {
        return this.messageDate;
    }



}
