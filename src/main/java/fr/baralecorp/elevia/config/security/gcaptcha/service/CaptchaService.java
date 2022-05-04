package fr.baralecorp.elevia.config.security.gcaptcha.service;

public interface CaptchaService {

    boolean verifyUserAction(String userAction, String token);

}
