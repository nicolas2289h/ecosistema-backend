package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Question;

import java.util.List;

public interface IQuestionService {

    //Create
    public Question saveQuestion(Question question);

    //Read
    public Question findQuestionById(Long id);

    //Get All
    public List<Question> getAllQuestion();

    //Update
    public Question uptadeQuestion(Long id, Question question);

    //Delete
    public void deleteQuestionById(Long id);
}
