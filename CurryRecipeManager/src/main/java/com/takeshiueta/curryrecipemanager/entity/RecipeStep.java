package com.takeshiueta.curryrecipemanager.entity;

import lombok.Data;

/**
 * 調理手順
 */
@Data
public class RecipeStep {
	/** ID */
	private Integer id;
	/** レシピID */
	private Integer recipeId;
	/** 手順番号 */
	private Integer stepNo;
	/** 手順内容 */
	private String content;

	/** コンストラクタ */
	public RecipeStep() {
	}

	public RecipeStep(Integer id, Integer recipeId, Integer stepNo, String content) {
		this.id = id;
		this.recipeId = recipeId;
		this.stepNo = stepNo;
		this.content = content;
	}

}
