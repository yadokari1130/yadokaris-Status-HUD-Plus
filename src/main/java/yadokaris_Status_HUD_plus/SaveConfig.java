package yadokaris_Status_HUD_plus;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.minecraft.util.text.TextComponentTranslation;

public class SaveConfig implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Status_HUD.isShow[0] = GuiSetting.checkBox_isShowText.isSelected();
		Status_HUD.isShow[1] = GuiSetting.checkBox_isShowSword.isSelected();
		Status_HUD.isShow[2] = GuiSetting.checkBox_isShowBow.isSelected();
		Status_HUD.isShow[3] = GuiSetting.checkBox_isShowDeath.isSelected();
		Status_HUD.isShow[4] = GuiSetting.checkBox_isShowRate.isSelected();
		Status_HUD.isShow[5] = GuiSetting.checkBox_isShowTotalRate.isSelected();
		Status_HUD.isShow[6] = GuiSetting.checkBox_isShowGetxp.isSelected();
		Status_HUD.isShow[7] = GuiSetting.checkBox_isShowHavexp.isSelected();
		Status_HUD.isShow[8] = GuiSetting.checkBox_isShowRank.isSelected();
		Status_HUD.isShow[9] = GuiSetting.checkBox_isShowRankPoint.isSelected();
		Status_HUD.isShow[10] = GuiSetting.checkBox_isShowCurrentJob.isSelected();
		Status_HUD.text = GuiSetting.textField.getText();
		Status_HUD.x = (int) GuiSetting.spinner_x.getValue();
		Status_HUD.y = (int) GuiSetting.spinner_y.getValue();
		Rendering.isRainbow = GuiSetting.checkBox_isRainbow.isSelected();

		Status_HUD.cfg.get("render", "isShowText", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[0]);
		Status_HUD.cfg.get("render", "isShowSwordKill", true, "剣キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[1]) ;
		Status_HUD.cfg.get("render", "isShowBowKill", true, "弓キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[2]);
		Status_HUD.cfg.get("render", "isShowDeath", true, "デス数の表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[3]);
		Status_HUD.cfg.get("render", "isShowRate", true, "K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[4]);
		Status_HUD.cfg.get("render", "isShowTotalRate", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[5]);
		Status_HUD.cfg.get("render", "isShowXP", true, "獲得xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[6]);
		Status_HUD.cfg.get("render", "isShowTotalXP", true, "所持xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[7]);
		Status_HUD.cfg.get("render", "isShowRank", true, "ランクの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[8]);
		Status_HUD.cfg.get("render", "isShowRankPoint", true, "ランクポイントの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[9]);
		Status_HUD.cfg.get("render", "isShowJob", true, "現在の職業の表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[10]);
		Status_HUD.cfg.get("render", "text", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。").set(Status_HUD.text);
		Status_HUD.cfg.get("render", "x", 2, "ステータスの画面上のx座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.x);
		Status_HUD.cfg.get("render", "y", 2, "ステータスの画面上のy座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.y);
		Status_HUD.cfg.get("render", "isRainbow", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。").set(Rendering.isRainbow);
		Status_HUD.cfg.get("render", "isRenderWhenStart", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。").set(GuiSetting.checkBox_isEnable.isSelected());
		String colors = "0x" + Integer.toHexString(Status_HUD.color);
		Status_HUD.cfg.get("render", "color", 0xFF0000, "文字の色を設定します。16進数で指定してください。").set(colors);

		Status_HUD.cfg.save();

		JOptionPane.showMessageDialog(new GuiSetting(), new TextComponentTranslation("yadokaris_shp.setting.save").getUnformattedText());

		Component c = (Component)e.getSource();
		Window w = SwingUtilities.getWindowAncestor(c);
		w.dispose();
	}
}
