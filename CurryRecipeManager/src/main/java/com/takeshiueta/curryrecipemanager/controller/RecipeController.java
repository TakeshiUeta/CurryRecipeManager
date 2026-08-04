package com.takeshiueta.curryrecipemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.takeshiueta.curryrecipemanager.dto.RecipeDetailDto;
import com.takeshiueta.curryrecipemanager.dto.RecipeListDto;
import com.takeshiueta.curryrecipemanager.form.IngredientForm;
import com.takeshiueta.curryrecipemanager.form.RecipeForm;
import com.takeshiueta.curryrecipemanager.form.RecipeStepForm;
import com.takeshiueta.curryrecipemanager.service.CommonService;
import com.takeshiueta.curryrecipemanager.service.RecipeService;

import jakarta.validation.Valid;

/**
 * レシピ一覧画面コントローラ
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CommonService commonService;

	// レシピ一覧画面遷移
	@GetMapping("/sc101recipe-list")
	public String getSc101recipeList(@RequestParam(required = false) Integer openRecipeId, Model model) {
		// レシピ一覧
		RecipeListDto recipeList = recipeService.createRecipeListDto();
		model.addAttribute("recipeList", recipeList);
		// 登録フォーム
		model.addAttribute("recipeForm", new RecipeForm());
		// 開くレシピID
		model.addAttribute("openRecipeId", openRecipeId);
		// 画面遷移
		return "recipe/sc101recipe-list";
	}

	// レシピ登録
	@PostMapping("/insert")
	public String postrecipeInsert(@Valid @ModelAttribute("recipeForm") RecipeForm form, BindingResult result,
			Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// レシピ一覧
			RecipeListDto recipeList = recipeService.createRecipeListDto();
			model.addAttribute("recipeList", recipeList);
			// 登録フォーム
			model.addAttribute("recipeForm", form);
			// 画面遷移
			return "recipe/sc101recipe-list";
		}
		// 登録
		recipeService.recipeInsert(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 材料登録
	@PostMapping("/ingredient/insert")
	public String postIngredientInsert(@Valid @ModelAttribute("ingredientForm") IngredientForm form,
			BindingResult result, Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// レシピ一覧
			RecipeListDto recipeList = recipeService.createRecipeListDto();
			// 材料フォームに入力値をセットする/
			recipeService.restoreValidationInput(form, recipeList);
			// レシピ詳細DTO取得
			RecipeDetailDto recipe = recipeService.findRecipeDetailDto(form.getRecipeId(), recipeList);
			// エラーメッセージにValidationMessagesの内容をセット
			if (result.hasFieldErrors("ingredientName")) {
				// 材料名
				recipe.getIngredientForm()
						.setIngredientNameError(result.getFieldError("ingredientName").getDefaultMessage());
			}
			if (result.hasFieldErrors("amount")) {
				// 分量
				recipe.getIngredientForm().setAmountError(result.getFieldError("amount").getDefaultMessage());
			}
			if (result.hasFieldErrors("unit")) {
				// 単位
				recipe.getIngredientForm().setUnitError(result.getFieldError("unit").getDefaultMessage());
			}
			//レシピ一覧
			model.addAttribute("recipeList", recipeList);
			// レシピ登録フォーム
			model.addAttribute("recipeForm", new RecipeForm());
			// 開くレシピIDを保持
			model.addAttribute("openRecipeId", form.getRecipeId());
			return "recipe/sc101recipe-list";
		}
		//レシピId存在チェック
		commonService.checkRecipeExists(form.getRecipeId());
		
		// 登録
		recipeService.ingredientInsert(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list?openRecipeId=" + form.getRecipeId();
	}

	// 材料更新
	@PostMapping("/ingredient/update")
	public String postIngredientUpdate(@Valid @ModelAttribute("ingredientForm") IngredientForm form,
			BindingResult result, Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// レシピ一覧
			RecipeListDto recipeList = recipeService.createRecipeListDto();
			// 材料フォームに入力値をセットする
			recipeService.restoreUpdateValidationInput(form, recipeList);
			// レシピ詳細DTO取得
			RecipeDetailDto recipe = recipeService.findRecipeDetailDto(form.getRecipeId(), recipeList);
			// 材料リストをゲット
			List<IngredientForm> ingredients = recipe.getIngredients();
			// フォームからIdをゲット
			Integer id = form.getId();
			// Idと一致するエラーメッセージをセット
			for (IngredientForm ingredient : ingredients) {
				Integer exId = ingredient.getId();
				if (exId.equals(id)) {
					if (result.hasFieldErrors("ingredientName")) {
						ingredient.setIngredientNameError(result.getFieldError("ingredientName").getDefaultMessage());
					}
					if (result.hasFieldErrors("amount")) {
						ingredient.setAmountError(result.getFieldError("amount").getDefaultMessage());
					}
					if (result.hasFieldErrors("unit")) {
						ingredient.setUnitError(result.getFieldError("unit").getDefaultMessage());
					}
					break; // 見つかったら終了
				}
			}
			//レシピ一覧
			model.addAttribute("recipeList", recipeList);
			// レシピ登録フォーム
			model.addAttribute("recipeForm", new RecipeForm());
			// 開くレシピIDを保持
			model.addAttribute("openRecipeId", form.getRecipeId());
			// 画面遷移
			return "recipe/sc101recipe-list";
		}
		//レシピId存在チェック
		commonService.checkRecipeExists(form.getRecipeId());

		// 更新
		recipeService.ingredientUpdate(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list?openRecipeId=" + form.getRecipeId();
	}

	// 材料削除
	@PostMapping("/ingredient/delete")
	public String postIngredientDelete(Integer id, Integer recipeId) {
		//レシピId存在チェック
		commonService.checkRecipeExists(recipeId);

		// 削除
		recipeService.ingredientDelete(id);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list?openRecipeId=" + recipeId;
	}

	// 調理手順登録
	@PostMapping("/recipeStep/insert")
	public String postRecipeStepInsert(@Valid @ModelAttribute("recipeStepForm") RecipeStepForm form,
			BindingResult result, Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// レシピ一覧
			RecipeListDto recipeList = recipeService.createRecipeListDto();
			// 調理手順フォームに入力値をセットする
			recipeService.restoreValidationInput(form, recipeList);
			// レシピ詳細DTO取得
			RecipeDetailDto recipe = recipeService.findRecipeDetailDto(form.getRecipeId(), recipeList);
			// エラーメッセージにValidationMessagesの内容をセット
			if (result.hasFieldErrors("stepNo")) {
				// 手順番号
				recipe.getRecipeStepForm().setStepNoError(result.getFieldError("stepNo").getDefaultMessage());
			}
			if (result.hasFieldErrors("content")) {
				// 調理内容
				recipe.getRecipeStepForm().setContentError(result.getFieldError("content").getDefaultMessage());
			}
			//レシピ一覧
			model.addAttribute("recipeList", recipeList);
			// レシピ登録フォーム
			model.addAttribute("recipeForm", new RecipeForm());
			// 開くレシピIDを保持
			model.addAttribute("openRecipeId", form.getRecipeId());
			// 画面遷移
			return "recipe/sc101recipe-list";
		}
		//レシピId存在チェック
		commonService.checkRecipeExists(form.getRecipeId());

		// 登録
		recipeService.recipeStepInsert(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list?openRecipeId=" + form.getRecipeId();
	}

	// 調理手順更新
	@PostMapping("/recipeStep/update")
	public String postRecipeStepUpdate(@Valid @ModelAttribute("recipeStepForm") RecipeStepForm form,
			BindingResult result, Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// レシピ一覧
			RecipeListDto recipeList = recipeService.createRecipeListDto();
			// 調理手順フォームに入力値をセットする
			recipeService.restoreUpdateValidationInput(form, recipeList);
			// レシピ詳細DTO取得
			RecipeDetailDto recipe = recipeService.findRecipeDetailDto(form.getRecipeId(), recipeList);
			// 材料リストをゲット
			List<RecipeStepForm> recipeSteps = recipe.getRecipeSteps();
			// フォームからIdをゲット
			Integer id = form.getId();
			// Idと一致するエラーメッセージをセット
			for (RecipeStepForm recipeStep : recipeSteps) {
				Integer exId = recipeStep.getId();
				if (exId.equals(id)) {
					// エラーメッセージにValidationMessagesの内容をセット
					if (result.hasFieldErrors("stepNo")) {
						// 手順番号
						recipeStep.setStepNoError(result.getFieldError("stepNo").getDefaultMessage());
					}
					if (result.hasFieldErrors("content")) {
						// 調理内容
						recipeStep.setContentError(result.getFieldError("content").getDefaultMessage());
					}
					break;// 見つかったら終了
				}
			}
			//レシピ一覧
			model.addAttribute("recipeList", recipeList);
			// レシピ登録フォーム
			model.addAttribute("recipeForm", new RecipeForm());
			// 開くレシピIDを保持
			model.addAttribute("openRecipeId", form.getRecipeId());
			// 画面遷移
			return "recipe/sc101recipe-list";
		}
		//レシピId存在チェック
		commonService.checkRecipeExists(form.getRecipeId());

		// 更新
		recipeService.recipeStepUpdate(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list?openRecipeId=" + form.getRecipeId();
	}

	// 調理手順削除
	@PostMapping("/recipeStep/delete")
	public String postRecipeStepDelete(Integer id, Integer recipeId) {
		//レシピId存在チェック
		commonService.checkRecipeExists(recipeId);

		// 削除
		recipeService.recipeStepDelete(id);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list?openRecipeId=" + recipeId;
	}

}