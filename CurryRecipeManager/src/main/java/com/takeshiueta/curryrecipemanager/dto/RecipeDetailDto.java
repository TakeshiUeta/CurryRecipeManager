package com.takeshiueta.curryrecipemanager.dto;

import java.util.List;

import com.takeshiueta.curryrecipemanager.form.IngredientForm;
import com.takeshiueta.curryrecipemanager.form.RecipeStepForm;

import lombok.Data;

@Data
/**
 * レシピ詳細画面に渡すデータ
 */
public class RecipeDetailDto {
	/** Id */
	private Integer id;
	/** レシピ名 */
	private String recipeName;
	/** 調理時間(分) */
	private Integer cookingTime;
	/** 材料リスト */
	private List<IngredientForm> ingredients;
	/** 調理手順リスト */
	private List<RecipeStepForm> recipeSteps;
	/** レシピの総合評価（1～5） */
	private Integer evaluation;
}