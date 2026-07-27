package com.takeshiueta.curryrecipemanager.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.takeshiueta.curryrecipemanager.entity.CookingResult;

/**
 * 調理結果マッパー
 */
@Mapper
public interface CookingResultMapper {
	/** 調理結果1件取得 */
	public CookingResult findById(Integer id);

	/** レシピidと紐付いている調理結果を取得 */
	public List<CookingResult> findByRecipeId(Integer recipeId);

	/** 調理結果全件表示 */
	public List<CookingResult> findAll();

	/** 調理結果登録 */
	public void insertOne(CookingResult cookingResult);

	/** 調理結果更新 */
	public void updateOne(CookingResult cookingResult);

	/** 調理結果削除 */
	public void deleteOne(Integer id);
}
