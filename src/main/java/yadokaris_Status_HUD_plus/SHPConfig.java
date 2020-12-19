package yadokaris_Status_HUD_plus;

import net.minecraftforge.common.ForgeConfigSpec;

public class SHPConfig {
	public static ForgeConfigSpec.BooleanValue doRender, isRainbow, doChangeTeamColor, doRenderWhenStart, doRenderEnchantment;
	public static ForgeConfigSpec.DoubleValue fontSize;
	public static ForgeConfigSpec.ConfigValue<String> colors, text;

	public static void init(ForgeConfigSpec.Builder client) {

		client.comment("yadokari's Status HUD Plus Config").push("general");

		colors = client.comment("文字の色を設定します。16進法で指定してください。").define("yadokaris_shp.color", "0xFFFFFF");

		text = client.comment("ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。").define("yadokaris_shp.text", "%sのステータス");
		fontSize = client.comment("ステータスの文字サイズを設定します。").defineInRange("yadokaris_shp.fontSize", 1d, 0d, 100d);
		doRenderWhenStart = client.comment("起動時のステータスの表示(true) / 非表示(false)を設定します。").define("yadokaris_shp.doRenderWhenStart", true);
		doRender = doRenderWhenStart;
		isRainbow = client.comment("ステータスの文字を虹色にする(true) / しない(false)を設定します。").define("yadokaris_shp.isRainbow", false);
		doChangeTeamColor = client.comment("テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。").define("yadokaris_shp.doChangeTeamColor", false);
		doRenderEnchantment = client.comment("エンチャント内容を表示する(true) / 表示しない(false)を設定します。").define("yadokaris_shp.doRenderEnchantment", true);
		client.pop();
	}
}
