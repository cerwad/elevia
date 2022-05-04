package fr.baralecorp.elevia.controller.dto;

import java.time.Duration;

public class BasicScoreDisplay {

    protected String handle;
    protected String time;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected String genTimeDisplay(Duration dur) {
        String time = "";
        if (dur.getNano() == 0) {
            time = String.format("%d s", dur.getSeconds());
        } else {
            time = String.format("%ds %d centièmes", dur.getSeconds(), dur.getNano() / 10000000);
        }
        return time;
    }


}
