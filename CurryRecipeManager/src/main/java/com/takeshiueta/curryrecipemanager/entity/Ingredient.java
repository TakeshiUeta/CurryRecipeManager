package com.takeshiueta.curryrecipemanager.entity;

import lombok.Data;

/**
 * 材料
 */
@Data
public class Ingredient {
	/** ID */
	private Integer id;
	/** レシピID */
	private Integer recipeId;
	/** 材料名 */
	private String ingredientName;
	/** 分量 */
	private String amount;
	/** 単位 */
	private String unit;

	/** コンストラクタ */
	public Ingredient() {
	}

	public Ingredient(Integer id, Integer recipeId, String ingredientName, String amount, String unit) {
		this.id = id;
		this.recipeId = recipeId;
		this.ingredientName = ingredientName;
		this.amount = amount;
		this.unit = unit;
	}

}
