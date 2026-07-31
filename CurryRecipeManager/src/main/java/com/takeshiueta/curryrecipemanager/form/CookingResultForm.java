package com.takeshiueta.curryrecipemanager.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 調理結果
 */
@Data
public class CookingResultForm {
	/** ID */
	private Integer id;
	/** レシピID */
	@NotNull
	private Integer recipeId;
	/** 調理日付 */
	@NotNull
	private LocalDate cookedDate;
	/** 出来栄え評価(1～5) */
	@Min(value = 1)
	@Max(value = 5)
	private Integer score;
	/** 一口メモ */
	@Size(max = 500)
	private String memo;

	/** コンストラクタ */
	public CookingResultForm() {
	}

	public CookingResultForm(Integer id, Integer recipeId, LocalDate cookedDate, Integer score, String memo) {
		this.id = id;
		this.recipeId = recipeId;
		this.cookedDate = cookedDate;
		this.score = score;
		this.memo = memo;
	}
}
