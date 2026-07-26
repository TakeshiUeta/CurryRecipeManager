package com.takeshiueta.curryrecipemanager.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * レシピ
 */
@Data
public class Recipe {
    /** ID */
    private Integer id;
    /** レシピ名 */
    private String recipeName;
    /** 調理時間(分) */
    private Integer cookingTime;
    /** レシピの総合評価（1～5） */
    private Integer evaluation;
    /** 登録日時 */
    private LocalDateTime createdAt;
    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** コンストラクタ */
    public Recipe() {
    }

    public Recipe(
            Integer id,
            String recipeName,
            Integer cookingTime,
            Integer evaluation,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.recipeName = recipeName;
        this.cookingTime = cookingTime;
        this.evaluation = evaluation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}