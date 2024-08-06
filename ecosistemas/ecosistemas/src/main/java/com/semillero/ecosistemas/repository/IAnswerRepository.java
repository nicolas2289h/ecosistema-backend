package com.semillero.ecosistemas.repository;

import com.semillero.ecosistemas.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
    Answer findByQuestionId(Long questionId);
}