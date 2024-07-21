//package com.semillero.ecosistemas.auth;
//
//import com.semillero.ecosistemas.jwt.JwtService;
//import com.semillero.ecosistemas.model.User;
//import com.semillero.ecosistemas.repository.IUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationService {
//    @Autowired
//    IUserRepository userRepository;
//
//    @Autowired
//    JwtService jwtConfig;
//
//    public User saveGoogleUser(OAuth2User oAuth2User) {
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("given_name");
//        String lastName = oAuth2User.getAttribute("family_name");
//        String picture = oAuth2User.getAttribute("picture");
//
//        User foundUser = userRepository.findByEmail(email);
//        if (foundUser != null) {
//            foundUser.setName(name);
//            foundUser.setLastname(lastName);
//            foundUser.setEmail(email);
//            foundUser.setPicture(picture);
//            return userRepository.save(foundUser);
//        }
//
//        User user = new User();
//        user.setName(name);
//        user.setLastname(lastName);
//        user.setEmail(email);
//        user.setDeleted(false);
//        user.setPicture(picture);
//        return userRepository.save(user);
//    }
//
//    public String generateJwtToken(User user) {
//        return jwtConfig.generateToken(user);
//    }
//}
