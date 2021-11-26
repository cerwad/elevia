package fr.baralecorp.elevia.security;

public interface CaptchaService {

    String getSiteKey();

    boolean verifyUserAction(String userAction, String token);

}
