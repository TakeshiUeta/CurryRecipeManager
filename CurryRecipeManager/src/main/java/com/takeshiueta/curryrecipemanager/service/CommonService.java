package com.takeshiueta.curryrecipemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeshiueta.curryrecipemanager.exception.ResourceNotFoundException;
import com.takeshiueta.curryrecipemanager.mapper.RecipeMapper;

/** 共通ビジネスロジック */
@Service
public class CommonService {
	@Autowired
	RecipeMapper recipeMapper;

	/** Id存在チェック */
	public void checkRecipeExists(Integer recipeId) {
		//レシピIdがnullの場合は例外クラスにスロー
		if (recipeId == null) {
			throw new ResourceNotFoundException("ご指定のレシピは存在していません。");
		}
		//レシピIdをカウントしてnullか存在しない場合は例外クラスにスロー
		Integer count = recipeMapper.countRecipeById(recipeId);
		if (count == null || count == 0) {
			throw new ResourceNotFoundException("ご指定のレシピは存在していません。");
		}
	}
}
