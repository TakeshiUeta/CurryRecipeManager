package com.takeshiueta.curryrecipemanager.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 * 調理結果画面に渡すデータ
 */
@Data
public class CookingResultDto {
	/** レシピID */
	private Integer recipeId;
	/** 調理日付 */
	private LocalDate cookedDate;
	/** 出来栄え評価(1～5) */
	private Integer score;
	/** 一口メモ */
	private String memo;
}
