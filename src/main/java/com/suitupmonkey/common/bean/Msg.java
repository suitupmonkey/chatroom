package com.suitupmonkey.common.bean;




import java.io.Serializable;
import java.util.Date;


//消息封装类
public class Msg implements Serializable {

    //消息主键
    private String msgId;

    //消息内容
    private String content;

    //发送时间--需要时间戳
    private Date deliveryDate;

    //发送时间字符串
    private String sendDate;

    //消息发送用户ID
    private String sender;

    //消息发送对象--如果是群消息，就暂时标记为group吧。
    private String receiver;

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.setSendDate(sendDate);
        this.deliveryDate = deliveryDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "msgId='" + msgId + '\'' +
                ", content='" + content + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
