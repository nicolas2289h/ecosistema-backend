package com.semillero.ecosistemas.service;


import com.semillero.ecosistemas.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IUserService {
    //Create
    User saveGoogleUser(OAuth2User oAuth2User);

    public User saveUser(User user);

    //Find
    public User findUserById(Long id);

    //Update (Change State --> deleted)
    public void switchState(User user);

    String generateJwtToken(User user);
}
