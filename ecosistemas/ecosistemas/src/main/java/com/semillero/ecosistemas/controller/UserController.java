package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.auth.AuthResponse;
import com.semillero.ecosistemas.model.User;
import com.semillero.ecosistemas.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/inicio")
    public String inicio() {
        return "PAGINA DE INICIO";
    }

    @GetMapping("/protegido")
    public String protegido(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User newUser = userService.saveGoogleUser(oAuth2User);
        String token = userService.generateJwtToken(oAuth2User);
        return "ACCESO A PAGINA PROTEGIDA.";
    }

    @GetMapping("/login")
    public ResponseEntity<?> saveGoogleUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User newUser = userService.saveGoogleUser(oAuth2User);
        String token = userService.generateJwtToken(oAuth2User);
        return ResponseEntity.ok(new AuthResponse(newUser, token));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Sesión cerrada.";
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> switchUserState(@PathVariable Long id) {

        User user = userService.findUserById(id);
        if (user != null) {
            userService.switchState(user);
            userService.saveUser(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}