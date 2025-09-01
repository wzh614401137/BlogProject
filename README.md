# BlogProject
<p align="center">
  <img src="https://img.shields.io/badge/-Java-007396.svg?logo=java&style=for-the-badge">
  <img src="https://img.shields.io/badge/-SpringBoot-6DB33F.svg?logo=springboot&style=for-the-badge&logoColor=white">
  <img src="https://img.shields.io/badge/-Thymeleaf-005F0F.svg?style=for-the-badge">
  <img src="https://img.shields.io/badge/-PostgreSQL-4479A1.svg?logo=mysql&style=for-the-badge&logoColor=white">
  <img src="https://img.shields.io/badge/-GitHub-181717.svg?logo=github&style=for-the-badge">
</p>

---

## 📚 目次

1. [プロジェクト概要](#プロジェクト概要)
2. [使用技術](#使用技術)
3. [画面イメージ](#画面イメージ)
4. [ユースケース図](#ユースケース図)
5. [テーブル設計](#テーブル設計)
6. [URL設計](#url設計)
7. [ディレクトリ構成](#ディレクトリ構成)
8. [工夫した点](#工夫した点)
9. [今後の課題](#今後の課題)
10. [作成者情報](#作成者情報)

---

## 🧩 プロジェクト概要

Spring Bootを使用して開発した個人用ブログアプリです。ユーザーはログインして記事を投稿・編集・削除・検索もできます。

---

## ⚙️ 使用技術

- バックエンド：Java 17 / Spring Boot / JPA
- フロントエンド：HTML / CSS / JavaScript / Thymeleaf
- データベース：PostgreSQL
- ビルド・管理：Maven / GitHub
- IDE：sping-tools-for-eclipse

---

## 🖼 画面イメージ

> スクリーンショットを `images/` フォルダに追加し、以下に貼り付けてください

- ログイン画面
- 投稿一覧画面
- 記事投稿フォーム
- コメント表示部分

---

## 🧭 ユースケース図

- ユーザー登録・ログイン
- 投稿作成・編集・削除
- 記事一覧・詳細表示
- コメント投稿・削除

> ※ draw.io や StarUML で作成した図を貼り付けてください

---

## 🗃 テーブル設計

```sql
-- users テーブル
id            SERIAL PRIMARY KEY
username      VARCHAR(50) UNIQUE NOT NULL
password      VARCHAR(255) NOT NULL

-- posts テーブル
id            SERIAL PRIMARY KEY
title         VARCHAR(100) NOT NULL
content       TEXT NOT NULL
author_id     BIGINT FOREIGN KEY (users.id)
created_at    DATETIME

-- comments テーブル
id            SERIAL PRIMARY KEY
content       TEXT
post_id       BIGINT FOREIGN KEY (posts.id)
user_id       BIGINT FOREIGN KEY (users.id)
created_at    DATETIME
```

---

## 🌐 URL設計

- `/register`：ユーザー登録画面（GET, POST）
- `/login`：ログイン画面（GET, POST）
- `/posts`：記事一覧（GET）
- `/posts/new`：投稿フォーム（GET, POST）
- `/posts/{id}`：記事詳細（GET）
- `/posts/{id}/edit`：記事編集（GET, POST）
- `/posts/{id}/delete`：記事削除（POST）
- `/search?keyword=xxx`：記事検索（GET）

---

## 📂 ディレクトリ構成

```
src/
├── controller       // 各種コントローラ
├── entity           // JPAエンティティ
├── repository       // データベース操作
├── service          // 業務ロジック
├── templates        // Thymeleafテンプレート
├── static           // CSSやJSなど静的ファイル
└── security         // Spring Security設定
```

---

## 💡 工夫した点

- Spring Securityによる認可設定
- 自作バリデーション（未入力、文字数チェックなど）
- 共通レイアウトのテンプレート化（ヘッダー／フッター）
- ページネーション対応
- BootstrapによるUIデザイン改善

---

## 🧪 今後の課題

- 投稿記事への「いいね」機能
- 通報・ブロック機能の導入
- テストコードの充実（JUnit / MockMvc）
- Herokuなどへのデプロイ

---

## 👤 作成者情報

- 氏名：〇〇 〇〇
- 所属：〇〇大学〇年
- GitHub：[@ユーザー名](https://github.com/ユーザー名)

<p align="right">(<a href="#top">トップへ</a>)</p>
