package com.takeshiueta.curryrecipemanager.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * レシピ
 */
@Data
public class RecipeForm {
	/**id*/
	private Integer id;
	/** レシピ名 */
	@NotBlank
	private String recipeName;
	/** 調理時間(分) */
	@NotNull
	@Min(value = 1)
	private Integer cookingTime;
	/** レシピの総合評価（1～5） */
	@Min(value = 1)
	@Max(value = 5)
	private Integer evaluation;

	/** コンストラクタ */
	public RecipeForm() {
	}

	public RecipeForm(Integer id,String recipeName, Integer cookingTime, Integer evaluation) {
		this.id = id;
		this.recipeName = recipeName;
		this.cookingTime = cookingTime;
		this.evaluation = evaluation;
	}
}
