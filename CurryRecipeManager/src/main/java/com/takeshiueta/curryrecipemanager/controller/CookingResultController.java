package com.takeshiueta.curryrecipemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.takeshiueta.curryrecipemanager.dto.CookingResultDto;
import com.takeshiueta.curryrecipemanager.form.CookingResultForm;
import com.takeshiueta.curryrecipemanager.service.CookingResultService;

/**
 * 調理結果画面コントローラ
 */
@Controller
@RequestMapping("/cooking-result")
public class CookingResultController {
	@Autowired
	private CookingResultService cookingResultService;

	// 調理結果ボタン押下時
	@GetMapping("/sc102cooking-result")
	public String getSc102CookingResult(Model model, @RequestParam Integer recipeId) {
		List<CookingResultDto> dtos = cookingResultService.createCookingResultDtos(recipeId);
		model.addAttribute("dtos", dtos);
		model.addAttribute("recipeId", recipeId);
		// 画面遷移
		return "cooking-result/sc102cooking-result";
	}

	// 調理結果登録
	@PostMapping("/insert")
	public String postCookingResultInsert(@ModelAttribute CookingResultForm form) {
		// 登録
		cookingResultService.insertCookingResult(form);
		// 画面遷移
		return "redirect:/cooking-result/sc102cooking-result?recipeId=" + form.getRecipeId();
	}

	// 調理結果更新
	@PostMapping("/update")
	public String postUpdateCookingResult(@ModelAttribute CookingResultForm form) {
		// 更新
		cookingResultService.updateCookingResult(form);
		// 画面遷移
		return "redirect:/cooking-result/sc102cooking-result?recipeId=" + form.getRecipeId();
	}
}
