package com.takeshiueta.curryrecipemanager.entity;

import java.time.LocalDate;
import lombok.Data;

/**
 * 調理結果
 */
@Data
public class CookingResult {
	/** ID */
	private Integer id;
	/** レシピID */
	private Integer recipeId;
	/** 調理日付 */
	private LocalDate cookedDate;
	/** 出来栄え評価(1～5) */
	private Integer score;
	/** 一口メモ */
	private String memo;

	/** コンストラクタ */
	public CookingResult() {
	}

	public CookingResult(Integer id, Integer recipeId, LocalDate cookedDate, Integer score, String memo) {
		this.id = id;
		this.recipeId = recipeId;
		this.cookedDate = cookedDate;
		this.score = score;
		this.memo = memo;
	}
}
