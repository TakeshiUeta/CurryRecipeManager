package com.takeshiueta.curryrecipemanager.service;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.takeshiueta.curryrecipemanager.dto.RecipeDetailDto;
import com.takeshiueta.curryrecipemanager.dto.RecipeListDto;
import com.takeshiueta.curryrecipemanager.entity.Ingredient;
import com.takeshiueta.curryrecipemanager.entity.Recipe;
import com.takeshiueta.curryrecipemanager.entity.RecipeStep;
import com.takeshiueta.curryrecipemanager.form.IngredientForm;
import com.takeshiueta.curryrecipemanager.form.RecipeForm;
import com.takeshiueta.curryrecipemanager.form.RecipeStepForm;
import com.takeshiueta.curryrecipemanager.mapper.IngredientMapper;
import com.takeshiueta.curryrecipemanager.mapper.RecipeMapper;
import com.takeshiueta.curryrecipemanager.mapper.RecipeStepMapper;

/**
 * レシピ一覧画面ビジネスロジック
 */
@Service
public class RecipeService {
	/** レシピマッパー */
	@Autowired
	private RecipeMapper recipeMapper;
	/** 材料マッパー */
	@Autowired
	private IngredientMapper ingredientMapper;
	/** 調理手順マッパー */
	@Autowired
	private RecipeStepMapper recipeStepMapper;
	/** Modelマッパー(オブジェクト変換) */
	@Autowired
	private ModelMapper modelMapper;

	/** レシピ一覧作成 */
	public RecipeListDto createRecipeListDto() {
		// レシピ情報一覧
		RecipeListDto recipeListDto = new RecipeListDto();
		// レシピ情報詳細のリスト
		List<RecipeDetailDto> recipes = new ArrayList<>();
		// レシピ全件取得
		List<Recipe> recipeList = recipeMapper.findAll();
		/** レシピ情報一覧を完成させる */
		for (Recipe recipe : recipeList) {
			// レシピ詳細
			RecipeDetailDto recipeDetail = new RecipeDetailDto();
			// レシピからレシピidをゲット。
			Integer recipeId = recipe.getId();
			// レシピ名
			String recipeName = recipe.getRecipeName();
			// 調理時間(分)
			Integer cookingTime = recipe.getCookingTime();
			// レシピの総合評価（1～5）
			Integer evaluation = recipe.getEvaluation();
			// レシピidと紐付いている材料を取得
			List<Ingredient> ingredients = ingredientMapper.findByRecipeId(recipeId);
			// エンティティクラス→フォームクラスに変換
			List<IngredientForm> ingredientForms = ingredients.stream()
					.map(ingredient -> modelMapper.map(ingredient, IngredientForm.class)).toList();
			// レシピidと紐付いている調理手順を取得
			List<RecipeStep> recipeSteps = recipeStepMapper.findByRecipeId(recipeId);
			// エンティティクラス→フォームクラスに変換
			List<RecipeStepForm> recipeStepForms = recipeSteps.stream()
					.map(recipeStep -> modelMapper.map(recipeStep, RecipeStepForm.class)).toList();
			// レシピ詳細にそれぞれの値をセット
			// レシピ
			recipeDetail.setId(recipeId);
			recipeDetail.setRecipeName(recipeName);
			recipeDetail.setCookingTime(cookingTime);
			recipeDetail.setEvaluation(evaluation);
			// 材料リスト
			recipeDetail.setIngredients(ingredientForms);
			// 調理手順リスト
			recipeDetail.setRecipeSteps(recipeStepForms);

			recipes.add(recipeDetail);
		}
		recipeListDto.setRecipes(recipes);
		return recipeListDto;
	}

	/** レシピ登録 */
	@Transactional
	public void recipeInsert(RecipeForm form) {
		// FormをEntityに変換
		Recipe entity = modelMapper.map(form, Recipe.class);
		recipeMapper.insertOne(entity);
	}

	/** 材料登録 */
	@Transactional
	public void ingredientInsert(IngredientForm form) {
		// FormをEntityに変換
		Ingredient entity = modelMapper.map(form, Ingredient.class);
		ingredientMapper.insertOne(entity);
	}

	/** 材料1件更新 */
	@Transactional
	public void ingredientUpdate(IngredientForm form) {
		// FormをEntityに変換
		Ingredient entity = modelMapper.map(form, Ingredient.class);
		ingredientMapper.updateOne(entity);
	}

	/** 材料1件削除 */
	@Transactional
	public void ingredientDelete(Integer id) {
		ingredientMapper.deleteOne(id);
	}

	/** 調理手順登録 */
	@Transactional
	public void recipeStepInsert(RecipeStepForm form) {
		// FormをEntityに変換
		RecipeStep entity = modelMapper.map(form, RecipeStep.class);
		recipeStepMapper.insertOne(entity);
	}

	/** 調理手順更新 */
	@Transactional
	public void recipeStepUpdate(RecipeStepForm form) {
		// FormをEntityに変換
		RecipeStep entity = modelMapper.map(form, RecipeStep.class);
		recipeStepMapper.updateOne(entity);
	}

	/** 調理手順削除 */
	@Transactional
	public void recipeStepDelete(Integer id) {
		recipeStepMapper.deleteOne(id);
	}
}
