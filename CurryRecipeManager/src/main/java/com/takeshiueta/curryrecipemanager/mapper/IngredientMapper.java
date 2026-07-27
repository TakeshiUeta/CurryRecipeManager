package com.takeshiueta.curryrecipemanager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.takeshiueta.curryrecipemanager.entity.Ingredient;

/**
 * 材料マッパー
 */
@Mapper
public interface IngredientMapper {
	/** 材料1件取得 */
	public Ingredient findById(Integer id);

	/** レシピidと紐付いている材料を取得 */
	public List<Ingredient> findByRecipeId(Integer recipeId);

	/** 材料全件表示 */
	public List<Ingredient> findAll();

	/** 材料登録 */
	public void insertOne(Ingredient ingredient);

	/** 材料1件更新 */
	public void updateOne(Ingredient ingredient);

	/** 材料1件削除 */
	public void deleteOne(Integer id);
}
