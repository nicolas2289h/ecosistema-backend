package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.CategoryC;
import com.semillero.ecosistemas.model.Country;

import java.util.List;

public interface ICategoryCService {

    List<CategoryC> getAllCategoryC();

    CategoryC getCategoryCById(Long id);

    //Delete
    public void deleteCategoryById(Long id);
}
