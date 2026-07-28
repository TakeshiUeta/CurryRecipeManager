package com.takeshiueta.curryrecipemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.takeshiueta.curryrecipemanager.dto.RecipeListDto;
import com.takeshiueta.curryrecipemanager.form.IngredientForm;
import com.takeshiueta.curryrecipemanager.form.RecipeForm;
import com.takeshiueta.curryrecipemanager.form.RecipeStepForm;
import com.takeshiueta.curryrecipemanager.service.CookingResultService;
import com.takeshiueta.curryrecipemanager.service.RecipeService;

/**
 * レシピ一覧画面コントローラ
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CookingResultService cookingResultService;

	// レシピ一覧画面遷移
	@GetMapping("/sc101recipe-list")
	public String getSc101recipeList(Model model) {
		// レシピ一覧
		RecipeListDto recipeList = recipeService.createRecipeListDto();
		model.addAttribute("recipeList", recipeList);
		// 登録フォーム
		model.addAttribute("recipeForm", new RecipeForm());
		// 画面遷移
		return "recipe/sc101recipe-list";
	}

	// レシピ登録
	@PostMapping("/insert")
	public String postrecipeInsert(@ModelAttribute RecipeForm form) {
		// 登録
		recipeService.recipeInsert(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 材料登録
	@PostMapping("/ingredient/insert")
	public String postIngredientInsert(@ModelAttribute IngredientForm form) {
		// 登録
		recipeService.ingredientInsert(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 材料更新
	@PostMapping("/ingredient/update")
	public String postIngredientUpdate(@ModelAttribute IngredientForm form) {
		// 更新
		recipeService.ingredientUpdate(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 材料削除
	@PostMapping("/ingredient/delete")
	public String postIngredientDelete(Integer id) {
		// 削除
		recipeService.ingredientDelete(id);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 調理手順登録
	@PostMapping("/recipeStep/insert")
	public String postRecipeStepInsert(@ModelAttribute RecipeStepForm form) {
		// 登録
		recipeService.recipeStepInsert(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 調理手順更新
	@PostMapping("/recipeStep/update")
	public String postRecipeStepUpdate(@ModelAttribute RecipeStepForm form) {
		// 更新
		recipeService.recipeStepUpdate(form);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}

	// 調理手順削除
	@PostMapping("/recipeStep/delete")
	public String postRecipeStepDelete(Integer id) {
		// 削除
		recipeService.recipeStepDelete(id);
		// 画面遷移
		return "redirect:/recipe/sc101recipe-list";
	}
}
