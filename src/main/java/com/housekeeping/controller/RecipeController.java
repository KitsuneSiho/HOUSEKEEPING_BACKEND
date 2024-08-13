package com.housekeeping.controller;

import com.housekeeping.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe-search")
    public ResponseEntity<List<Map<String, String>>> searchRecipes(@RequestBody Map<String, Object> searchCriteria) {
        List<Map<String, String>> recipes = recipeService.searchRecipe(searchCriteria);
        return ResponseEntity.ok(recipes);
    }
}