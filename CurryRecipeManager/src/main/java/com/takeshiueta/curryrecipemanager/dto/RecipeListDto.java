package com.takeshiueta.curryrecipemanager.dto;

import java.util.List;
import lombok.Data;

/**
 * レシピ一覧画面に渡すクラス
 */
@Data
public class RecipeListDto {
	/** レシピ情報一覧 */
	private List<RecipeDetailDto> recipes;
}
