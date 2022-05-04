package fr.baralecorp.elevia.config.security.gcaptcha;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Assesment {
    private TokenProperties tokenProperties;
    private Float score;
    private String[] reasons;
    private Event event;
    private String name;

    public TokenProperties getTokenProperties() {
        return tokenProperties;
    }

    public void setTokenProperties(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String[] getReasons() {
        return reasons;
    }

    public void setReasons(String[] reasons) {
        this.reasons = reasons;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
