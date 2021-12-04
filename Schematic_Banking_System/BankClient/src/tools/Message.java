package tools;

import java.io.Serializable;

public class Message implements Serializable {
    private String MsgText;

    public String getMsgText() {
        return MsgText;
    }

    public void setMsgText(String msgText) {
        MsgText = msgText;
    }
}
