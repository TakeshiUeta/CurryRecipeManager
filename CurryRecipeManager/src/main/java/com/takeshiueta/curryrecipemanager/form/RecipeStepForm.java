package com.takeshiueta.curryrecipemanager.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 調理手順
 */
@Data
public class RecipeStepForm {
	/** レシピID */
	@NotNull
	private Integer recipeId;
	/** 手順番号 */
	@NotNull
	@Min(value = 1)
	private Integer stepNo;
	/** 手順内容 */
	@NotBlank
	@Size(max = 500)
	private String content;
	
	/** コンストラクタ */
	public RecipeStepForm() {
	}

	public RecipeStepForm(Integer recipeId, Integer stepNo, String content) {
		this.recipeId = recipeId;
		this.stepNo = stepNo;
		this.content = content;
	}

}
