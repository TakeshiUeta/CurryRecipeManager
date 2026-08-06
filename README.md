# 🍛 Waterless Curry Management

## 1. アプリ概要

Spring Boot・MyBatis・Thymeleafを用いて開発した、
無水カレーのレシピ・材料・調理手順・調理結果を管理するWebアプリケーションです。

データベース設計からバックエンド、画面実装まで一貫して実装しました。

## 🌐 公開URL

以下のURLから実際に動作確認できます。

🔗 https://curryrecipemanager.onrender.com/recipe/sc101recipe-list

※デモ用途のため、サンプルレシピデータを登録しています。  
※Render無料プラン利用のため、初回アクセス時は起動に時間がかかる場合があります。

---

## 操作方法

### 1. レシピ管理

トップ画面では登録されているレシピ一覧が表示されます。  
「新規登録」からレシピ情報を登録できます。  
登録したレシピ名横の「＋」ボタンを押下すると、材料・調理手順・調理結果管理ボタンが表示されます。  
調理結果管理ボタンを押下すると調理結果管理画面に遷移します。

### 2. 材料管理

レシピ一覧画面から対象レシピを展開すると、材料の追加・編集・削除が可能です。

### 3. 調理手順管理

調理手順では工程ごとの内容を管理できます。  
手順の追加・編集・削除が可能です。

### 4. 調理結果管理

調理結果管理画面では、調理日・評価・メモなどの調理結果を登録できます。  
登録した調理結果の確認・編集・削除が可能です。

## Release

### Version 1.0.0

- CRUD機能実装
- Validation対応
- Exception処理
- H2 File Database対応
- BootstrapによるUI改善
- Git Flowを参考にした開発運用

## 2. 開発背景

自身が調理した無水カレーのレシピや調理結果を記録・管理することを目的として開発しました。  
単純なレシピ表示だけではなく、

- 材料管理
- 調理手順管理
- 調理結果管理
- レシピ情報管理

を行えるよう設計しています。

## 3. 使用技術

| 分類               | 技術                            |
| ------------------ | ------------------------------- |
| Backend            | Java 17                         |
| Framework          | Spring Boot 3.5.16              |
| ORM                | MyBatis                         |
| Template Engine    | Thymeleaf                       |
| Database           | H2 Database (File Mode)         |
| Frontend           | HTML / CSS / Bootstrap 5.3.3    |
| Build              | Maven                           |
| Version Control    | Git / GitHub                    |
| Project Management | GitHub Projects / GitHub Issues |

## 4. 機能一覧

| 機能          | 内容                         |
| ------------- | ---------------------------- |
| レシピ管理    | 登録・一覧表示・詳細表示     |
| 材料管理      | 追加・更新・削除             |
| 調理手順管理  | 追加・更新・削除             |
| 調理結果管理  | 調理結果の登録・更新・削除   |
| Validation    | 入力チェック                 |
| Exception処理 | 共通例外処理によるエラー制御 |

## 5. 画面構成

### レシピ一覧画面

#### TOP画面

![alt text](image-1.png)

#### アコーディオン展開後

![alt text](image-2.png)

##### レシピ新規登録

![alt text](image-9.png)

### 調理結果画面

#### TOP画面

![alt text](image-6.png)

#### 調理結果新規登録

![alt text](image-8.png)

#### アコーディオン展開後

![alt text](image-7.png)

## 6. システムフロー

```mermaid
flowchart LR

    A[Browser<br>HTML画面]

    B[RecipeController]

    C[RecipeService]

    D[Mapper<br>MyBatis]

    E[(H2 Database)]

    A -->|HTTP Request<br>Form送信| B

    B --> C

    C --> D

    D -->|SQL実行| E

    E -->|検索結果| D

    D --> C

    C --> B

    B -->|Model設定<br>View返却| A
```

## 7. アプリケーション構成

### レイヤー構成

```mermaid

flowchart TB

subgraph View
    A[Thymeleaf HTML]
end

subgraph Controller
    B[RecipeController]
end

subgraph Form
    C[RecipeForm]
    D[IngredientForm]
    E[RecipeStepForm]
end

subgraph Service
    F[RecipeService]
end

subgraph DTO
    G[RecipeDTO]
end

subgraph Mapper
    H[RecipeMapper]
    I[IngredientMapper]
    J[RecipeStepMapper]
    K[CookingResultMapper]
end

subgraph Entity
    L[Recipe Entity]
    M[Ingredient Entity]
    N[RecipeStep Entity]
    O[CookingResult Entity]
end

subgraph Database
    P[(recipe)]
    Q[(ingredient)]
    R[(recipe_step)]
    S[(cooking_result)]
end


A --> B

B --> C
B --> D
B --> E

B --> F

F --> H
F --> I
F --> J
F --> K

H --> L
I --> M
J --> N
K --> O

H <--> P
I <--> Q
J <--> R
K <--> S

F --> G
G --> B

B --> A

```

## 8. データベース設計

### ER図

```mermaid
erDiagram

    RECIPE {
        BIGINT id PK
        VARCHAR recipe_name
        INT cooking_time
        INT evaluation
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    INGREDIENT {
        BIGINT id PK
        BIGINT recipe_id FK
        VARCHAR ingredient_name
        VARCHAR amount
        VARCHAR unit
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    RECIPE_STEP {
        BIGINT id PK
        BIGINT recipe_id FK
        INT step_no
        VARCHAR content
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    COOKING_RESULT {
        BIGINT id PK
        BIGINT recipe_id FK
        DATE cooking_date
        INT evaluation
        VARCHAR memo
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    RECIPE ||--o{ INGREDIENT : contains
    RECIPE ||--o{ RECIPE_STEP : has
    RECIPE ||--o{ COOKING_RESULT : records
```

---

### テーブル関係

#### recipe

レシピの基本情報です
| 項目 | 説明 |
| ------------ | ------------ |
| id | レシピID |
| recipe_name | レシピ名 |
| cooking_time | 調理時間(分) |
| evaluation | レシピの総合評価（1～5） |
| created_at | 登録日時 |
| updated_at | 更新日時 |

#### ingredient

レシピに必要な材料です
| 項目 | 説明 |
| --------------- | -------- |
| id | 材料ID |
| recipe_id | レシピID |
| ingredient_name | 材料名 |
| amount | 分量 |
| unit | 単位 |

#### recipe_step

調理手順です
| 項目 | 説明 |
| ----------- | --------- |
| id 　　　　 | 手順ID 　　　 |
| recipe_id　 | レシピID |
| step_no　　 | 手順番号 |
| content　　 | 手順内容 |

#### cooking_result

実際に調理した結果を管理します。

| 項目         | 説明         |
| ------------ | ------------ |
| id           | 調理結果ID   |
| recipe_id    | レシピID(FK) |
| cooking_date | 調理日       |
| evaluation   | 出来栄え評価 |
| memo         | メモ         |

#### Excelでの設計書はこちら

| No  | 設計書                                                                                                                                                                                                                                                                                                                                                                | 内容                           |
| --- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| 101 | [画面遷移図](https://view.officeapps.live.com/op/view.aspx?src=https%3A%2F%2Fraw.githubusercontent.com%2FTakeshiUeta%2FCurryRecipeManager%2Frefs%2Fheads%2Fmain%2FCurryRecipeManager%2Fdocs%2F01_design%2F101%25E7%2594%25BB%25E9%259D%25A2%25E9%2581%25B7%25E7%25A7%25BB%25E5%259B%25B3%2520.xlsx&wdOrigin=BROWSELINK)                                               | 画面間の遷移                   |
| 102 | [詳細画面設計書](https://view.officeapps.live.com/op/view.aspx?src=https%3A%2F%2Fraw.githubusercontent.com%2FTakeshiUeta%2FCurryRecipeManager%2Frefs%2Fheads%2Fmain%2FCurryRecipeManager%2Fdocs%2F01_design%2F102%25E8%25A9%25B3%25E7%25B4%25B0_%25E7%2594%25BB%25E9%259D%25A2%25E8%25A8%25AD%25E8%25A8%2588%25E6%259B%25B8.xlsx&wdOrigin=BROWSELINK)                 | 画面レイアウト・項目・処理概要 |
| 103 | [モジュール関連図](https://view.officeapps.live.com/op/view.aspx?src=https%3A%2F%2Fraw.githubusercontent.com%2FTakeshiUeta%2FCurryRecipeManager%2Frefs%2Fheads%2Fmain%2FCurryRecipeManager%2Fdocs%2F01_design%2F103%25E3%2583%25A2%25E3%2582%25B8%25E3%2583%25A5%25E3%2583%25BC%25E3%2583%25AB%25E9%2596%25A2%25E9%2580%25A3%25E5%259B%25B3.xlsx&wdOrigin=BROWSELINK) | レイヤー構成・クラス構成       |
| 104 | [テーブル定義書](https://view.officeapps.live.com/op/view.aspx?src=https%3A%2F%2Fraw.githubusercontent.com%2FTakeshiUeta%2FCurryRecipeManager%2Frefs%2Fheads%2Fmain%2FCurryRecipeManager%2Fdocs%2F01_design%2F104%25E3%2583%2586%25E3%2583%25BC%25E3%2583%2596%25E3%2583%25AB%25E5%25AE%259A%25E7%25BE%25A9%25E6%259B%25B8.xls&wdOrigin=BROWSELINK)                   | テーブル・カラム定義           |
| 105 | [システムフロー](https://view.officeapps.live.com/op/view.aspx?src=https%3A%2F%2Fraw.githubusercontent.com%2FTakeshiUeta%2FCurryRecipeManager%2Frefs%2Fheads%2Fmain%2FCurryRecipeManager%2Fdocs%2F01_design%2F105%25E3%2582%25B7%25E3%2582%25B9%25E3%2583%2586%25E3%2583%25A0%25E3%2583%2595%25E3%2583%25AD%25E3%2583%25BC.xlsx&wdOrigin=BROWSELINK)                  | 主要処理フロー                 |
| 106 | [フォルダ構成](https://view.officeapps.live.com/op/view.aspx?src=https%3A%2F%2Fraw.githubusercontent.com%2FTakeshiUeta%2FCurryRecipeManager%2Frefs%2Fheads%2Fmain%2FCurryRecipeManager%2Fdocs%2F01_design%2F106%25E3%2583%2595%25E3%2582%25A9%25E3%2583%25AB%25E3%2583%2580%25E6%25A7%258B%25E6%2588%2590.xlsx&wdOrigin=BROWSELINK)                                   | プロジェクト構成               |

## 9. 設計・実装で工夫した点

### レイヤードアーキテクチャ

Controller・Service・Mapper・Entityで責務を分離しました。

- Controller：リクエスト受付、画面遷移制御
- Service：業務ロジック処理
- Mapper：データベースアクセス処理
- Entity：データモデル管理

各層の役割を明確にすることで、保守性・拡張性を考慮した構成にしました。

---

### DTO・Form・Entityの分離

画面入力・画面表示・データベース操作で扱うデータを分離しました。

- Form：画面入力値の受け取り
- DTO：画面表示用データ
- Entity：データベースとのマッピング

それぞれの責務を分けることで、画面変更やDB変更時の影響範囲を抑えられる構成にしました。

---

### MyBatisによるSQL管理

SQL処理をMapper XMLへ分離しました。

JavaコードとSQLを分離することで、
データアクセス処理の可読性・保守性を向上させました。

---

### Git Flowを意識したブランチ運用

機能単位でfeatureブランチを作成し、
developブランチへ段階的に統合する開発運用を行いました。

実装単位を分割することで、変更管理やレビューしやすい構成を意識しました。

---

### BootstrapによるUI改善

Bootstrapを利用し、以下のUI改善を行いました。

- カードレイアウト
- アコーディオン表示
- レスポンシブ対応

画面操作性と視認性を考慮した画面設計を行いました。

## 10. Git運用

本プロジェクトでは、Git Flowを参考にしたブランチ運用を行いました。

### ブランチ構成

```text
main
└─develop
    ├─feature/setup
    ├─feature/entity-form-dto
    ├─feature/mapper
    ├─feature/service
    ├─feature/controller
    ├─feature/view
    ├─feature/validation
    ├─feature/exception
    ├─feature/delete-confirm
    └─feature/h2-file-mode
```

### 運用内容

- GitHub Projects を利用したタスク管理
- GitHub Issues による機能単位の管理
- 機能ごとにfeatureブランチを作成して開発
- 完成した機能をdevelopへ統合
- 安定版をmainへ反映する運用を実施

## 11. 起動方法

### 前提環境

- Java 17
- Maven 3.9.x

```bash
git clone https://github.com/TakeshiUeta/CurryRecipeManager.git
cd CurryRecipeManager
mvn spring-boot:run
```

起動時にH2 File Databaseを利用します。

```text
DB保存先：
CurryRecipeManager/data/

※H2 File Databaseとして永続保存されます。
```

起動後、以下のURLへアクセスしてください。

```text
http://localhost:8080/recipe/sc101recipe-list
```

## 12. 今後の改善予定

### Version 1.1（機能拡張・品質改善）

- レシピ管理強化
- CSV/TSVインポート
- CSV/TSVエクスポート
- ドラッグ＆ドロップアップロード
- 単体テスト追加
- テスト設計書作成
- ログ機能追加
- Service責務整理
- DTO変換整理
- DBデータ管理改善
- 画像管理

### Version 2.0（SPA化）

- Reactへ画面移行
- REST API化
- クラウド環境への再デプロイ

## 13. 学習・技術的な挑戦

本プロジェクトでは以下の点を意識しました。

- Spring Bootのレイヤードアーキテクチャ
- MyBatisによるSQL管理
- DTO・Form・Entityの責務分離
- Bootstrapを利用したレスポンシブ対応・UI改善
- Git Flowを意識したブランチ運用
- GitHub Projectsによるタスク管理
