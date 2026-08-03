package com.takeshiueta.curryrecipemanager.dto;

import java.util.List;

import lombok.Data;

/**
 * 調理結果画面dto
 */
@Data
public class CookingResultDto {
	/** レシピID */
	private Integer recipeId;
	/** レシピ名*/
	private String recipeName;
	/** 調理結果一覧 */
    private List<CookingResultDetailDto> results;
}
