package com.takeshiueta.curryrecipemanager.dto;

import java.util.List;
import com.takeshiueta.curryrecipemanager.form.CookingResultForm;
import lombok.Data;

/**
 * 調理結果画面に渡すデータ
 */
@Data
public class CookingResultDto {
	/** 調理結果一覧 */
	private List<CookingResultForm> cookingResults;
}
