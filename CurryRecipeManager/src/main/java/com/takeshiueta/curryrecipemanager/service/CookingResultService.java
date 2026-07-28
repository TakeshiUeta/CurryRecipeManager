package com.takeshiueta.curryrecipemanager.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.takeshiueta.curryrecipemanager.entity.CookingResult;
import com.takeshiueta.curryrecipemanager.form.CookingResultForm;
import com.takeshiueta.curryrecipemanager.mapper.CookingResultMapper;

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

}
