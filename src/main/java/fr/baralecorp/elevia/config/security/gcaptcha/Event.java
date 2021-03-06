package fr.baralecorp.elevia.config.security.gcaptcha;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String token;
    private String siteKey;
    private String expectedAction;

    public Event() {
    }

    public Event(String token, String siteKey, String expectedAction) {
        this.token = token;
        this.siteKey = siteKey;
        this.expectedAction = expectedAction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getExpectedAction() {
        return expectedAction;
    }

    public void setExpectedAction(String expectedAction) {
        this.expectedAction = expectedAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!Objects.equals(token, event.token)) return false;
        if (!Objects.equals(siteKey, event.siteKey)) return false;
        return Objects.equals(expectedAction, event.expectedAction);
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (siteKey != null ? siteKey.hashCode() : 0);
        result = 31 * result + (expectedAction != null ? expectedAction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "token='" + token + '\'' +
                ", siteKey='" + siteKey + '\'' +
                ", expectedAction='" + expectedAction + '\'' +
                '}';
    }
}
