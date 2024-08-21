package com.housekeeping.service.implement;

import com.housekeeping.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    // Naver Clova API의 URL
    @Value("${naver.clova.api.url}")
    private String apiUrl;

    // Naver Clova API 인증 키
    @Value("${naver.clova.api.key}")
    private String apiKey;

    // Naver API Gateway 인증 키
    @Value("${naver.clova.apigw.key}")
    private String apigwKey;

    // HTTP 요청을 위한 RestTemplate
    private final RestTemplate restTemplate;

    public RecipeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Map<String, String>> searchRecipe(Map<String, Object> searchCriteria) {
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apigwKey);

        // API 요청 본문 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", List.of(
                // 시스템 메시지: API에 레시피 추천 시스템의 역할 설명
                Map.of("role", "system", "content", "- 재료와 조건을 입력하면 레시피를 알려주는 레시피 추천 시스템\n- 서문 없이 바로 1개 레시피 추천\n- 답변 폼\n\n이름:\n재료:\n소요시간:\n조리법:\n\n\n\n"),
                // 사용자 메시지: 실제 레시피 검색 쿼리
                Map.of("role", "user", "content", buildQuery(searchCriteria))
        ));
        // API 요청 파라미터 설정
        requestBody.put("topP", 0.8);
        requestBody.put("topK", 0);
        requestBody.put("maxTokens", 256);
        requestBody.put("temperature", 0.5);
        requestBody.put("repeatPenalty", 5.0);
        requestBody.put("includeAiFilters", true);
        requestBody.put("seed", 0);

        // HTTP 요청 엔티티 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // API 호출 및 응답 수신
        Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);

        // API 응답 처리
        if (response != null && response.containsKey("result")) {
            Object resultObj = response.get("result");
            if (resultObj instanceof Map) {
                // 단일 결과 처리
                Map<String, Object> resultMap = (Map<String, Object>) resultObj;
                if (resultMap.containsKey("message")) {
                    Map<String, Object> messageMap = (Map<String, Object>) resultMap.get("message");
                    if (messageMap.containsKey("content")) {
                        String recipeText = (String) messageMap.get("content");

                        System.out.println(recipeText);
                        return parseRecipes2(recipeText);
                    }
                }
            } else if (resultObj instanceof List) {
                // 다중 결과 처리
                List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultObj;
                if (!resultList.isEmpty()) {
                    Map<String, Object> firstResult = resultList.get(0);
                    if (firstResult.containsKey("message")) {
                        Map<String, Object> messageMap = (Map<String, Object>) firstResult.get("message");
                        if (messageMap.containsKey("content")) {
                            String recipeText = (String) messageMap.get("content");

                            System.out.println(recipeText);
                            return parseRecipes2(recipeText);
                        }
                    }
                }
            }
        }

        System.out.println("No valid recipe content found in the response.");
        return new ArrayList<>(); // 유효한 레시피 내용이 없을 경우 빈 리스트 반환
    }

    // 검색 조건을 API 쿼리 문자열로 변환하는 메소드
    private String buildQuery(Map<String, Object> searchCriteria) {
        StringBuilder queryBuilder = new StringBuilder();

        // 조리양 추가
        String amount = (String) searchCriteria.getOrDefault("amount", "");
        if (!amount.isEmpty()) {
            queryBuilder.append("조리양: ").append(amount).append("\n");
        }

        // 재료 추가
        List<String> ingredients = (List<String>) searchCriteria.getOrDefault("ingredients", new ArrayList<>());
        if (!ingredients.isEmpty()) {
            queryBuilder.append("재료: ").append(String.join(", ", ingredients)).append("\n");
        }

        // 요리 종류 추가
        String dishType = (String) searchCriteria.getOrDefault("dishType", "");
        if (!dishType.isEmpty()) {
            queryBuilder.append("요리 종류: ").append(dishType).append("\n");
        }

        // 요리 테마 추가
        String cuisine = (String) searchCriteria.getOrDefault("cuisine", "");
        if (!cuisine.isEmpty()) {
            queryBuilder.append("요리 테마: ").append(cuisine).append("\n");
        }

        // 조리 시간 추가
        String cookingTime = (String) searchCriteria.getOrDefault("cookingTime", "");
        if (!cookingTime.isEmpty()) {
            queryBuilder.append("조리 시간: ").append(cookingTime).append("\n");
        }

        return queryBuilder.toString().trim();
    }

    // API 응답 텍스트를 파싱하여 레시피 리스트로 변환하는 메소드
    private List<Map<String, String>> parseRecipes(String recipeText) {
        List<Map<String, String>> recipes = new ArrayList<>();
        String[] recipeStrings = recipeText.split("\n\n"); // 각 레시피는 빈 줄로 구분됨

        for (String recipeString : recipeStrings) {
            Map<String, String> recipe = new HashMap<>();
            String[] lines = recipeString.split("\n");

            int recipeStepsIndex = -1;
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.startsWith("이름 : ")) {
                    recipe.put("name", line.substring(5).trim());
                } else if (line.startsWith("재료 : ")) {
                    recipe.put("ingredients", line.substring(5).trim());
                } else if (line.startsWith("소요시간 : ")) {
                    recipe.put("time", line.substring(8).trim());
                } else if (line.startsWith("조리법 : ")) {
                    recipeStepsIndex = i;
                    break;
                }
            }

            // 레시피 단계 파싱
            if (recipeStepsIndex != -1) {
                StringBuilder recipeSteps = new StringBuilder();
                for (int i = recipeStepsIndex + 1; i < lines.length; i++) {
                    recipeSteps.append(lines[i]).append("\n");
                }
                recipe.put("steps", recipeSteps.toString().trim());
            }

            recipes.add(recipe);
        }

        return recipes;
    }

    // API 응답 텍스트를 파싱하여 레시피 리스트로 변환하는 메소드
    private List<Map<String, String>> parseRecipes2(String recipeText) {
        Map<String, String> recipe = new HashMap<>();

        String allRecipe = recipeText.split("이름 : ", 2)[1];

        String[] nameRecipe = allRecipe.split("재료 : ", 2);

        recipe.put("name", nameRecipe[0]);

        String[] ingredientRecipe = nameRecipe[1].split("소요시간 : ", 2);

        recipe.put("ingredients", ingredientRecipe[0]);

        recipe.put("time", ingredientRecipe[1].split("조리법 : \n", 2)[0]);

        recipe.put("steps", ingredientRecipe[1].split("조리법 : \n", 2)[1]);

        List<Map<String, String>> recipes = new ArrayList<>();

        recipes.add(recipe);

        return recipes;
    }
}