package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Answer;
import com.semillero.ecosistemas.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private IAnswerRepository iAnswerRepository;

    public List<Answer> getAllAnswers() {
        return iAnswerRepository.findAll();
    }

    public Answer getAnswerById(Long id) {
        return iAnswerRepository.findById(id).orElse(null);
    }

    public Answer getAnswerByQuestionId(Long questionId) {
        return iAnswerRepository.findByQuestionId(questionId);
    }

    public Answer saveAnswer(Answer answer) {
        return iAnswerRepository.save(answer);
    }

    public void deleteAnswer(Long id) {
        iAnswerRepository.deleteById(id);
    }
}