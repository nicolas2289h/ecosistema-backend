package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.CategoryC;
import com.semillero.ecosistemas.repository.ICategoryCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryCService {
    @Autowired
    private ICategoryCRepository iCategoryCRepository;

    public List<CategoryC> getAllCategories() {
        return iCategoryCRepository.findAll();
    }

    public CategoryC getCategoryById(Long id) {
        return iCategoryCRepository.findById(id).orElse(null);
    }

    public CategoryC saveCategory(CategoryC category) {
        return iCategoryCRepository.save(category);
    }

    public void deleteCategory(Long id) {
        iCategoryCRepository.deleteById(id);
    }
}