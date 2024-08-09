package com.housekeeping.service;

import java.util.List;
import java.util.Map;

public interface RecipeService {
    List<Map<String, String>> searchRecipe(Map<String, Object> searchCriteria);
}
