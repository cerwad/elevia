package fr.baralecorp.elevia.config.security;

public class CaptchaProperties {
    private String siteKey;
    private String projectId;
    private String apiKey;

    public CaptchaProperties() {
    }

    public CaptchaProperties(String siteKey, String projectId, String apiKey) {
        this.siteKey = siteKey;
        this.projectId = projectId;
        this.apiKey = apiKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
