package com.takeshiueta.curryrecipemanager.form;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 材料
 */
@Data
public class IngredientForm {
	/** レシピID */
	@NotNull
	private Integer recipeId;
	/** 材料名 */
	@NotBlank
	private String ingredientName;
	/** 分量 */
	@NotBlank
	private String amount;
	/** 単位 */
	@NotBlank
	private String unit;

	/** コンストラクタ */
	public IngredientForm() {
	}

	public IngredientForm(Integer recipeId, String ingredientName, String amount, String unit) {
		this.recipeId = recipeId;
		this.ingredientName = ingredientName;
		this.amount = amount;
		this.unit = unit;
	}

}
