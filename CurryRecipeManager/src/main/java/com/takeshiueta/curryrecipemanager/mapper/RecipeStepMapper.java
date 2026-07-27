package com.takeshiueta.curryrecipemanager.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.takeshiueta.curryrecipemanager.entity.RecipeStep;

/**
 * 調理手順マッパー
 */
@Mapper
public interface RecipeStepMapper {
	/** 調理手順1件取得 */
	public RecipeStep findById(Integer id);

	/** レシピidと紐付いている調理手順を取得 */
	public List<RecipeStep> findByRecipeId(Integer recipeId);

	/** 調理手順全件表示 */
	public List<RecipeStep> findAll();

	/** 調理手順登録 */
	public void insertOne(RecipeStep recipeStep);

	/** 調理手順更新 */
	public void updateOne(RecipeStep recipeStep);

	/** 調理手順削除 */
	public void deleteOne(Integer id);
}
