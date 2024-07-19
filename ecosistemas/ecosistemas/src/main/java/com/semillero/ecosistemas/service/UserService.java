package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.jwt.JwtService;
import com.semillero.ecosistemas.model.User;
import com.semillero.ecosistemas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository usuarioRepo;

    @Autowired
    JwtService jwtConfig;

    @Override
    public User saveGoogleUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        String picture = oAuth2User.getAttribute("picture");

        User foundUser = usuarioRepo.findByEmail(email);
        if (foundUser != null) {
            foundUser.setName(name);
            foundUser.setLastname(lastName);
            foundUser.setEmail(email);
            foundUser.setPicture(picture);
            return usuarioRepo.save(foundUser);
            // VER ROLES AL GUARDAR USER
        }

        User user = new User();
        user.setName(name);
        user.setLastname(lastName);
        user.setEmail(email);
        user.setDeleted(false);
        user.setPicture(picture);
        // VER ROLES AL GUARDAR USER
        return usuarioRepo.save(user);
    }

    public String generateJwtToken(User user) {
        return jwtConfig.generateToken(user);
    }

    @Override
    public User saveUser(User user) {
        return usuarioRepo.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    @Override
    public void switchState(User user) {
        if (!user.getDeleted()) {
            user.setDeleted(true); // Set deleted TRUE --> Account deactivation
        } else {
            user.setDeleted(false); // Set deleted FALSE --> Account Reactivation
        }
    }
}