package com.takeshiueta.curryrecipemanager.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.takeshiueta.curryrecipemanager.dto.CookingResultDetailDto;
import com.takeshiueta.curryrecipemanager.dto.CookingResultDto;
import com.takeshiueta.curryrecipemanager.entity.CookingResult;
import com.takeshiueta.curryrecipemanager.form.CookingResultForm;
import com.takeshiueta.curryrecipemanager.mapper.CookingResultMapper;
import com.takeshiueta.curryrecipemanager.mapper.RecipeMapper;

/**
 * 調理結果画面ビジネスロジック
 */
@Service
public class CookingResultService {
	/** 調理結果マッパー */
	@Autowired
	private CookingResultMapper cookingResultMapper;
	/** Modelマッパー(オブジェクト変換) */
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private RecipeMapper recipeMapper;

	/** レシピidと紐付いている調理結果を取得 */
	public List<CookingResult> getCookingResultsByRecipeId(Integer recipeId) {
		List<CookingResult> cookingResults = cookingResultMapper.findByRecipeId(recipeId);
		return cookingResults;
	}

	/** 調理結果登録 */
	@Transactional
	public void insertCookingResult(CookingResultForm form) {
		// FormをEntityに変換
		CookingResult entity = modelMapper.map(form, CookingResult.class);
		cookingResultMapper.insertOne(entity);
	}

	/** 調理結果更新 */
	@Transactional
	public void updateCookingResult(CookingResultForm form) {
		// FormをEntityに変換
		CookingResult entity = modelMapper.map(form, CookingResult.class);
		cookingResultMapper.updateOne(entity);
	}

	/** 調理結果削除 */
	public void deleteOneCookingResult(Integer id) {
		cookingResultMapper.deleteOne(id);
	}

	/** レシピIDに紐付く調理結果をDTO形式で取得 */
	public CookingResultDto createCookingResultDto(Integer recipeId) {
		// 調理結果画面に渡すデータ
		CookingResultDto dto = new CookingResultDto();
		// レシピidと紐付いている調理結果を取得
		List<CookingResult> entities = this.getCookingResultsByRecipeId(recipeId);
		// レシピidと紐付いているレシピ名を取得
		String name = recipeMapper.findRecipeNameById(recipeId);
		// EntityをDtoに変換
		List<CookingResultDetailDto> results = entities.stream()
				.map(result -> modelMapper.map(result, CookingResultDetailDto.class)).toList();
		// それぞれの値をセットする
		dto.setRecipeId(recipeId);
		dto.setRecipeName(name);
		dto.setResults(results);
		return dto;
	}
}
