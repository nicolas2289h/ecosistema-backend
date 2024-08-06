package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Answer;

import java.util.List;

public interface IAnswerService {

    //Create
    public Answer saveAnswer(Answer answer);

    //Read
    public Answer findAnswerById(Long id);

    //Get All
    public List<Answer> getAllAnswer();

    //Update
    public Answer uptadeAnswer(Long id, Answer answer);

    //Delete
    public void deleteAnswerById(Long id);
}
