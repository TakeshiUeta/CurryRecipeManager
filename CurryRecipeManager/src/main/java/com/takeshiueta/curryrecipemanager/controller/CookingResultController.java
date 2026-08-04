package com.takeshiueta.curryrecipemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.takeshiueta.curryrecipemanager.dto.CookingResultDetailDto;
import com.takeshiueta.curryrecipemanager.dto.CookingResultDto;
import com.takeshiueta.curryrecipemanager.form.CookingResultForm;
import com.takeshiueta.curryrecipemanager.service.CommonService;
import com.takeshiueta.curryrecipemanager.service.CookingResultService;

import jakarta.validation.Valid;

/**
 * 調理結果画面コントローラ
 */
@Controller
@RequestMapping("/cooking-result")
public class CookingResultController {

	@Autowired
	private CookingResultService cookingResultService;
	@Autowired
	private CommonService commonService;

	/** 調理結果ボタン押下時*/
	@GetMapping("/sc102cooking-result")
	public String getSc102CookingResult(@RequestParam(required = false) Integer openResultId, Integer openRecipeId,
			Model model, @RequestParam Integer recipeId) {
		// レシピid存在チェック
		commonService.checkRecipeExists(recipeId);
		// 調理結果dto
		CookingResultDto dto = cookingResultService.createCookingResultDto(recipeId);
		// 調理結果フォーム
		CookingResultForm cookingResultForm = new CookingResultForm();
		// 調理結果フォームにレシピidをセット
		cookingResultForm.setRecipeId(recipeId);

		model.addAttribute("cookingResultForm", cookingResultForm);
		model.addAttribute("dto", dto);
		// 画面側のアコーディオン状態管理Ｉｄ
		model.addAttribute("openResultId", openResultId);
		// 画面遷移
		return "cooking-result/sc102cooking-result";
	}

	// 調理結果登録
	@PostMapping("/insert")
	public String postCookingResultInsert(@Valid @ModelAttribute("cookingResultForm") CookingResultForm form,
			BindingResult result, Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// レシピId
			Integer recipeId = form.getRecipeId();
			// 調理結果画面dto
			CookingResultDto dto = cookingResultService.createCookingResultDto(recipeId);

			model.addAttribute("cookingResultForm", form);
			model.addAttribute("dto", dto);
			// 画面側のアコーディオン状態管理Ｉｄ
			model.addAttribute("openResultId", null);
			// 画面遷移
			return "cooking-result/sc102cooking-result";
		}
		// レシピid存在チェック
		commonService.checkRecipeExists(form.getRecipeId());
		
		// 登録
		cookingResultService.insertCookingResult(form);
		// 画面遷移
		return "redirect:/cooking-result/sc102cooking-result?recipeId=" + form.getRecipeId();
	}

	// 調理結果更新
	@PostMapping("/update")
	public String postUpdateCookingResult(@Valid @ModelAttribute("cookingResultForm") CookingResultForm form,
			BindingResult result, Model model) {
		// バリデーション
		if (result.hasErrors()) {
			// id
			Integer id = form.getId();
			// レシピId
			Integer recipeId = form.getRecipeId();
			// 調理結果画面dto
			CookingResultDto dto = cookingResultService.createCookingResultDto(recipeId);
			// 調理結果一覧dto
			List<CookingResultDetailDto> detailDtos = dto.getResults();
			// Idと一致するエラーメッセージをセット
			for (CookingResultDetailDto detailDto : detailDtos) {
				Integer exId = detailDto.getId();
				if (id.equals(exId)) {
					if (result.hasFieldErrors("cookedDate")) {
						detailDto.setCookedDateError(result.getFieldError("cookedDate").getDefaultMessage());
					}
					if (result.hasFieldErrors("memo")) {
						detailDto.setMemoError(result.getFieldError("memo").getDefaultMessage());
					}
					break; // 見つかったら終了
				}
			}
			// 新規作成フォーム
			CookingResultForm cookingResultForm = new CookingResultForm();
			cookingResultForm.setRecipeId(recipeId);
			model.addAttribute("cookingResultForm", cookingResultForm);
			model.addAttribute("dto", dto);
			// 画面側のアコーディオン状態管理Ｉｄ
			model.addAttribute("openResultId", id);
			// 画面遷移
			return "cooking-result/sc102cooking-result";
		}
		// レシピid存在チェック
		commonService.checkRecipeExists(form.getRecipeId());

		// 更新
		cookingResultService.updateCookingResult(form);
		// 画面遷移
		return "redirect:/cooking-result/sc102cooking-result?recipeId=" + form.getRecipeId() + "&openResultId="
				+ form.getId();
	}

	// 調理結果削除
	@PostMapping("/delete")
	public String postDeleteCookingResult(@RequestParam Integer id, @RequestParam Integer recipeId) {
		// レシピid存在チェック
		commonService.checkRecipeExists(recipeId);
		// 削除
		cookingResultService.deleteOneCookingResult(id);
		// 画面遷移
		return "redirect:/cooking-result/sc102cooking-result?recipeId=" + recipeId;
	}

}
