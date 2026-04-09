# Minecraft 26.1.1 (1.21.4相当) 移行対応状況とプラン

## 1. 目的
Minecraft 1.21.1 対応のマルチローダープロジェクトを、最新の **26.1.1 (2026年新ナンバリング規格、1.21.4相当)** へ移行する。

## 2. 現在の状況

### 完了した作業
- [x] **Gradle 設定の更新**: Java 25 対応、Loom 1.15-SNAPSHOT / NeoForge 2.0.141 へのアップグレード。
- [x] **ビルド環境の正常化**: 設定ミスによる Gradle のサイレントクラッシュを解消し、`:fabric:genSources` に成功。
- [x] **マッピング移行 (基礎)**: `Yarn` から `Mojang Official` マッピングへの移行を開始。
- [x] **描画 API の初期対応**: `GuiGraphics` から `GuiGraphicsExtractor` へのコード置換を一部実施。

### 進行中の課題・エラー
- **クラス/メソッドの不整合**: Mojang マッピング移行に伴い、`KeyMapping` (旧 KeyBinding) や `Font` (旧 TextRenderer) 等の参照修正が必要。
- **レンダリングシステムの変更**: 26.1 (1.21.4+) で導入された `GuiGraphicsExtractor` およびマルチスレッドレンダリング対応の `onRender` メソッドの正確なシグネチャの確定。
- **依存関係の解決**: Fabric API (`0.145.3+26.1.1`) のパッケージ構造変更への対応。

## 3. 今後の対応プラン

### フェーズ 1: ソースコードの Mojang マッピング完全移行
- `KeyBinding.java`: すべての `net.minecraft.client.option.KeyBinding` を `net.minecraft.client.KeyMapping` へ置換。
- `ResourceLocation`: `Identifier` から `ResourceLocation` へ移行し、`ResourceLocation.fromNamespaceAndPath` を使用。

### フェーズ 2: レンダリングエンジンの修正 (26.1 仕様)
- `DaysRenderer.java` (Fabric): `HudElement` インターフェースの最新メソッド (`extractRenderState`, `onRender`) を実装。
- `DaysOverlay.java` (NeoForge): `RenderGuiEvent` の最新引数に基づき、`GuiGraphicsExtractor` を使用した描画処理を確定。
- `Font.drawInBatch` 等の低レベル描画 API の正確な呼び出し方法の確認。

### フェーズ 3: ビルド検証とクリーンアップ
- `:fabric:compileClientJava` および `:neoforge:compileJava` の完遂。
- 各ローダーでの実機動作確認 (HUD 描画、キー入力)。
- 不要なコメントアウトや旧バージョンの残骸の削除。

---

## 4. 具体的な TODO チェックリスト

### Fabric / Common 修正
- [ ] `Mc100daysClient.java` の修正 (`ResourceLocation` 非互換の解消)
- [ ] `DaysRenderer.java` の修正 (`HudElement` の new API 対応)
- [ ] `KeyBinding.java` の修正 (Mojang マッピング名 `KeyMapping` への統一)

### NeoForge 修正
- [ ] `DaysOverlay.java` の修正 (`GuiGraphicsExtractor` 対応)
- [ ] NeoForge プロジェクトのビルドパス同期の最終確認

### 検証
- [ ] Fabric でのビルド & 起動確認
- [ ] NeoForge でのビルド & 起動確認

---

## 技術的な重要変更点 (メモ)
- **Minecraft バージョン**: 26.1.1
- **Java**: 25 (OpenJDK)
- **マッピング**: Mojang Official
- **HUD API**: Fabric API の `HudElement` システムを使用。
- **描画クラス**: `GuiGraphics` -> `GuiGraphicsExtractor` (1.21.4+ / 26.x 仕様)
