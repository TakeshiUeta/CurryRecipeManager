package com.takeshiueta.curryrecipemanager.dto;

import java.time.LocalDate;

import lombok.Data;

/*
* 調理結果一覧 
*/
@Data
public class CookingResultDetailDto {
	/** ID */
	private Integer id;
	/** 調理日付 */
	private LocalDate cookedDate;
	/** 出来栄え評価(1～5) */
	private Integer score;
	/** 一口メモ */
	private String memo;
	/** 調理日付エラー*/
	private String  cookedDateError;
	/** 一口メモエラー*/
	private String  memoError;
}
