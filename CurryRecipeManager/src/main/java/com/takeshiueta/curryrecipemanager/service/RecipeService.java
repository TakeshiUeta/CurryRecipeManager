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
			// 材料登録フォーム
			IngredientForm ingredientForm = new IngredientForm();
			ingredientForm.setRecipeId(recipeId);
			// 調理手順登録フォーム
			RecipeStepForm recipeStepForm = new RecipeStepForm();
			recipeStepForm.setRecipeId(recipeId);
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
			// 材料登録フォーム
			recipeDetail.setIngredientForm(ingredientForm);
			// 調理手順登録フォーム
			recipeDetail.setRecipeStepForm(recipeStepForm);
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

	/** レシピ詳細DTO取得 */
	public RecipeDetailDto findRecipeDetailDto(Integer recipeId, RecipeListDto recipeList) {
		List<RecipeDetailDto> detailDtos = recipeList.getRecipes();
		// Listを順番に処理できる形に変換
		RecipeDetailDto recipeDetailDto = detailDtos.stream()
				// dtoのidと紐付くレシピIdを検索
				.filter(dto -> dto.getId().equals(recipeId))
				// 対象のレシピ詳細Dtoを取得
				.findFirst()
				// 一致するものがなかった場合nullを返す
				.orElse(null);
		return recipeDetailDto;
	}

	/** 材料フォームに入力値をセットする（登録バリデーション用） */
	public void restoreValidationInput(IngredientForm form, RecipeListDto recipeList) {
		// レシピId
		Integer recipeId = form.getRecipeId();
		// 材料名
		String ingredientName = form.getIngredientName();
		// 分量
		String amount = form.getAmount();
		// 単位
		String unit = form.getUnit();
		// レシピ詳細をレシピIdからゲット
		RecipeDetailDto recipe = this.findRecipeDetailDto(recipeId, recipeList);
		// レシピ詳細DTO取得
		IngredientForm exForm = recipe.getIngredientForm();
		// 材料フォームにそれぞれの入力値をセットする
		exForm.setIngredientName(ingredientName);
		exForm.setAmount(amount);
		exForm.setUnit(unit);
	}
	
	/** 調理手順フォームに入力値をセットする（登録バリデーション用） */
	public void restoreValidationInput(RecipeStepForm form, RecipeListDto recipeList) {
		// レシピId
		Integer recipeId = form.getRecipeId();
		// 手順番号
		Integer stepNo = form.getStepNo();
		// 手順内容
		String content = form.getContent();
		// レシピ詳細をレシピIdからゲット
		RecipeDetailDto recipe = this.findRecipeDetailDto(recipeId, recipeList);
		// レシピ詳細DTO取得
		RecipeStepForm exForm = recipe.getRecipeStepForm();
		// 調理手順登録フォームにそれぞれの入力値をセットする
		exForm.setStepNo(stepNo);
		exForm.setContent(content);
	}

	/** 材料フォームに入力値をセットする（更新バリデーション用） */
	public void restoreUpdateValidationInput(IngredientForm form, RecipeListDto recipeList) {
		// フォームからIdをゲット
		Integer id = form.getId();
		// レシピIdをゲット
		Integer recipeId = form.getRecipeId();
		// 材料名をゲット
		String ingredientName = form.getIngredientName();
		// 分量をゲット
		String amount = form.getAmount();
		// 単位をゲット
		String unit = form.getUnit();
		// 詳細リストから対象の詳細クラスをゲット
		RecipeDetailDto details = this.findRecipeDetailDto(recipeId, recipeList);
		// 材料フォームリストを詳細dtoからゲット
		List<IngredientForm> ingredients = details.getIngredients();
		// 材料フォームリストのidに紐付くindexにフィールドをセットする
		for (IngredientForm ingredient : ingredients) {
			// idをゲット
			Integer exId = ingredient.getId();
			// フォームからゲットしたidとidが同値の場合フォームの値をセットする
			if (id != null && id.equals(exId)) {
				ingredient.setIngredientName(ingredientName);
				ingredient.setAmount(amount);
				ingredient.setUnit(unit);
				break;
			}
		}
	}

	/** 調理手順フォームに入力値をセットする（更新バリデーション用） */
	public void restoreUpdateValidationInput(RecipeStepForm form, RecipeListDto recipeList) {
		// フォームからIdをゲット
		Integer id = form.getId();
		// レシピIdをゲット
		Integer recipeId = form.getRecipeId();
		// 手順番号をゲット
		Integer stepNo = form.getStepNo();
		// 手順内容をゲット
		String content = form.getContent();
		// 詳細リストから対象の詳細クラスをゲット
		RecipeDetailDto details = this.findRecipeDetailDto(recipeId, recipeList);
		// 調理手順フォームリストを詳細dtoからゲット
		List<RecipeStepForm> recipeSteps = details.getRecipeSteps();
		// 調理手順フォームリストのidに紐付くindexにフィールドをセットする
		for (RecipeStepForm recipeStep : recipeSteps) {
			// idをゲット
			Integer exId = recipeStep.getId();
			// フォームからゲットしたidとidが同値の場合フォームの値をセットする
			if (id != null && id.equals(exId)) {
				recipeStep.setStepNo(stepNo);
				recipeStep.setContent(content);
				break;
			}
		}
	}

}
