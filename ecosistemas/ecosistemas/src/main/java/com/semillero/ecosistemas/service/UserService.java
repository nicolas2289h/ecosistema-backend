package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.User;
import com.semillero.ecosistemas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository usuarioRepo;

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
        if (!user.getDeleted()){
            user.setDeleted(true); // Set deleted TRUE --> Account deactivation
        }
        else{
            user.setDeleted(false); // Set deleted FALSE --> Account Reactivation
        }
    }
}