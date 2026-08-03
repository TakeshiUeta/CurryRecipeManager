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
	/**ID*/
	private Integer id;
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

	/** 手順番号エラー */
	private String stepNoError;

    /** 手順内容エラー */
    private String contentError;
	
	/** コンストラクタ */
	public RecipeStepForm() {
	}

	public RecipeStepForm(Integer id,Integer recipeId, Integer stepNo, String content) {
		this.id = id;
		this.recipeId = recipeId;
		this.stepNo = stepNo;
		this.content = content;
	}

}
