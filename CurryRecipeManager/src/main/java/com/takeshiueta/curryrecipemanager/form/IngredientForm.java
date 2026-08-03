package com.takeshiueta.curryrecipemanager.form;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 材料
 */
@Data
public class IngredientForm {
	/** ID */
	private Integer id;
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

	/** 材料名エラー */
	private String ingredientNameError;

	/** 分量エラー */
	private String amountError;

	/** 単位エラー */
	private String unitError;

	/** コンストラクタ */
	public IngredientForm() {
	}

	public IngredientForm(Integer id, Integer recipeId, String ingredientName, String amount, String unit) {
		this.id = id;
		this.recipeId = recipeId;
		this.ingredientName = ingredientName;
		this.amount = amount;
		this.unit = unit;
	}

}
