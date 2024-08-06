package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.CategoryC;
import com.semillero.ecosistemas.service.CategoryCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoryCController {

    @Autowired
    private CategoryCService categoryCService;

    @GetMapping
    public List<CategoryC> getAllCategories() {
        return categoryCService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryC getCategoryById(@PathVariable Long id) {
        return categoryCService.getCategoryById(id);
    }

    //Crear Categoria
    @PostMapping
    public CategoryC createCategory(@RequestBody CategoryC category) {
        return categoryCService.saveCategory(category);
    }

    //Editar
    @PutMapping("/{id}")
    public CategoryC updateCategory(@PathVariable Long id, @RequestBody CategoryC category) {
        category.setId(id);
        return categoryCService.saveCategory(category);
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryCService.deleteCategory(id);
    }
}
