package com.semillero.ecosistemas.service;


import com.semillero.ecosistemas.model.User;

public interface IUserService {
    //Create
    public User saveUser(User user);

    //Find
    public User findUserById(Long id);

    //Update (Change State --> deleted)
    public void switchState(User user);
}
