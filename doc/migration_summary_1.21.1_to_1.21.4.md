# Minecraft 1.21.1 to 1.21.4 (26.1.x) Migration Summary

Minecraft 1.21.1 (v1.21.11) から 1.21.4 (26.1.1相当) への移行において発生した主要な変更点をまとめます。このドキュメントは、同様のマルチローダープロジェクト（Fabric/NeoForge）の移行に際して参照可能な技術情報の提供を目的としています。

## 1. クラス・マッピングの変更

Mojang Official Mappings および各プラットフォームのマッピングにおいて、基本的なクラス名が変更されました。

| 項目 | 旧 (1.21.1) | 新 (1.21.4) | 備考 |
| :--- | :--- | :--- | :--- |
| リソース識別子 | `ResourceLocation` | `Identifier` | Minecraft 全体での名称変更 |
| ワールド時間取得 | `Level.getDayTime()` | `Level.getOverworldClockTime()` | 旧メソッドは廃止 |

## 2. レンダリングシステムの刷新

1.21.4 ではレンダリングパイプラインがマルチスレッド対応を見据えて大幅に変更されました。

### GuiGraphics から GuiGraphicsExtractor へ
直接的な描画メソッドが `GuiGraphics` から `GuiGraphicsExtractor` へ抽象化されました。

- **テキスト描画**:
  - 旧: `guiGraphics.drawString(font, text, x, y, color, shadow)`
  - 新: `guiGraphicsExtractor.text(font, text, x, y, color, shadow)`
- **引数の変更**:
  - 多くの描画メソッドが `DeltaTracker`（旧 `PartialTick` 相当）を要求するようになりました。

## 3. Fabric API の変更

Fabric API において、名称とパッケージ構成の整理（リファクタリング）が行われました。

### HUD Rendering (HudRenderCallbackV2 相当)
- **パッケージ**: `net.fabricmc.fabric.api.client.rendering.v1.hud` へ移動。
- **インターフェース**: `HudElement`
- **メソッド**: `onRender` から `extractRenderState` へ変更。
  - 名称は "State" ですが、現状の `GuiGraphicsExtractor` を通じた描画処理はこのメソッド内で行います。

### Key Mapping
- **パッケージ**: `net.fabricmc.fabric.api.client.keymapping.v1` へ移動。
- **名称**: `KeyBindingHelper` から `KeyMappingHelper` へ。
- **メソッド**: `registerKeyBinding` から `registerKeyMapping` へ。
- **データ型**: `KeyMapping.Category`
  - 旧: 第3引数に `"category.misc"` などの String を指定。
  - 新: `KeyMapping.Category.MISC` などの Enum を指定。

## 4. Mixin の変更

サーバーのワールドロードなどのコアプロセスにおいて、メソッド名が変更されています。

- **MinecraftServer**:
  - `loadWorld` -> `loadLevel`
- **トラブルシューティング**:
  - Boilerplate に含まれる `ExampleMixin` などが古いメソッド名を参照している場合、起動時に `InvalidInjectionException` でクラッシュします。不要な場合は削除するか、ターゲットメソッドを更新する必要があります。

## 5. 開発環境・CI

- **Java バージョン**: Java 25 への更新が必須です。
- **Gradle**: `gradle/actions/wrapper-validation@v4` など、最新のアクションを使用しないとネットワークエラー (`ETIMEDOUT`) が発生する場合があります。
- **Loom/NeoForge**: 各ツールチェーンのバージョンを 1.21.4 対応版（26.1.1 以上）に上げる必要があります。

---
> [!IMPORTANT]
> 1.21.4 はマイナーアップデートに見えますが、レンダリング周りは「26.x」という内部メジャーバージョンに相当する破壊的変更が含まれています。
