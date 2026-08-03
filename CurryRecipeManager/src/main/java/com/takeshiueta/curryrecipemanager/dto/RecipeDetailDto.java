package com.takeshiueta.curryrecipemanager.dto;

import java.util.List;

import com.takeshiueta.curryrecipemanager.form.IngredientForm;
import com.takeshiueta.curryrecipemanager.form.RecipeStepForm;

import lombok.Data;

/**
 * レシピ詳細画面に渡すデータ
 */
@Data
public class RecipeDetailDto {
	/** レシピId */
	private Integer id;
	/** レシピ名 */
	private String recipeName;
	/** 調理時間(分) */
	private Integer cookingTime;
	/** 材料リスト */
	private List<IngredientForm> ingredients;
	/** 調理手順リスト */
	private List<RecipeStepForm> recipeSteps;
	// 材料登録フォーム
	private IngredientForm ingredientForm;
	// 調理手順登録フォーム
	private RecipeStepForm  recipeStepForm;
	/** レシピの総合評価（1～5） */
	private Integer evaluation;
}