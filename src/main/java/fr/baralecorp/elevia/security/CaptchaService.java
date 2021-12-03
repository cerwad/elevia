package fr.baralecorp.elevia.security;

public interface CaptchaService {

    boolean verifyUserAction(String userAction, String token);

}
