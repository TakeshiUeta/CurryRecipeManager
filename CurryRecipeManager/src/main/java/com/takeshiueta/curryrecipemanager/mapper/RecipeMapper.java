package com.takeshiueta.curryrecipemanager.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.takeshiueta.curryrecipemanager.entity.Recipe;

/**
 * レシピマッパー
 */
@Mapper
public interface RecipeMapper {
	/** レシピ1件取得 */
	public Recipe findById(Integer id);

	/** レシピ名だけ取得 */
	public String findRecipeNameById(Integer id);

	/**id存在チェック*/
	public Integer countRecipeById(Integer id);

	/** レシピID一覧取得 */
	public List<Integer> findRecipeIds();

	/** レシピ全件表示 */
	public List<Recipe> findAll();

	/** レシピ登録 */
	public void insertOne(Recipe recipe);
}
