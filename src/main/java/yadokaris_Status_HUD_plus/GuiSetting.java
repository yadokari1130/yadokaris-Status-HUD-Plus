package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import net.minecraft.util.text.TextComponentTranslation;

public class GuiSetting extends JFrame {

	private JPanel contentPane;
	static JCheckBox checkBox_isEnable = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isEnable").getUnformattedText());
	static JCheckBox checkBox_isShowText = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowText").getUnformattedText());
	static JCheckBox checkBox_isShowSword = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowSword").getUnformattedText());
	static JCheckBox checkBox_isShowBow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowBow").getUnformattedText());
	static JCheckBox checkBox_isShowDeath = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowDeath").getUnformattedText());
	static JCheckBox checkBox_isShowRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRate").getUnformattedText());
	static JCheckBox checkBox_isShowTotalRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowTotalRate").getUnformattedText());
	static JCheckBox checkBox_isShowGetxp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowGetxp").getUnformattedText());
	static JCheckBox checkBox_isShowHavexp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowHavexp").getUnformattedText());
	static JCheckBox checkBox_isShowRank = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRank").getUnformattedText());
	static JCheckBox checkBox_isShowRankPoint = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRankPoint").getUnformattedText());
	static JCheckBox checkBox_isShowCurrentJob = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowCurrentJob").getUnformattedText());
	static JSpinner spinner_x = new JSpinner();
	static JSpinner spinner_y = new JSpinner();
	static JTextField textField = new JTextField();
	static JCheckBox checkBox_isRainbow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isRainbow").getUnformattedText());

	public GuiSetting() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button_color = new JButton(new TextComponentTranslation("yadokaris_shp.setting.colorButton").getUnformattedText());
		button_color.setBounds(315, 255, 144, 27);
		button_color.addActionListener(new ColorSetting());
		contentPane.add(button_color);

		checkBox_isEnable.setBackground(Color.WHITE);
		checkBox_isEnable.setSelected(Status_HUD.cfg.getBoolean("isRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。"));
		checkBox_isEnable.setBounds(22, 21, 254, 21);
		contentPane.add(checkBox_isEnable);

		checkBox_isShowText.setBackground(Color.WHITE);
		checkBox_isShowText.setSelected(Status_HUD.isShow[0]);
		checkBox_isShowText.setBounds(22, 44, 151, 21);
		contentPane.add(checkBox_isShowText);

		checkBox_isShowSword.setBackground(Color.WHITE);
		checkBox_isShowSword.setSelected(Status_HUD.isShow[1]);
		checkBox_isShowSword.setBounds(22, 67, 151, 21);
		contentPane.add(checkBox_isShowSword);

		checkBox_isShowBow.setBackground(Color.WHITE);
		checkBox_isShowBow.setSelected(Status_HUD.isShow[2]);
		checkBox_isShowBow.setBounds(22, 90, 151, 21);
		contentPane.add(checkBox_isShowBow);

		checkBox_isShowDeath.setBackground(Color.WHITE);
		checkBox_isShowDeath.setSelected(Status_HUD.isShow[3]);
		checkBox_isShowDeath.setBounds(22, 113, 151, 21);
		contentPane.add(checkBox_isShowDeath);

		checkBox_isShowRate.setBackground(Color.WHITE);
		checkBox_isShowRate.setSelected(Status_HUD.isShow[4]);
		checkBox_isShowRate.setBounds(22, 136, 151, 21);
		contentPane.add(checkBox_isShowRate);

		checkBox_isShowTotalRate.setBackground(Color.WHITE);
		checkBox_isShowTotalRate.setSelected(Status_HUD.isShow[5]);
		checkBox_isShowTotalRate.setBounds(22, 159, 151, 21);
		contentPane.add(checkBox_isShowTotalRate);

		checkBox_isShowGetxp.setBackground(Color.WHITE);
		checkBox_isShowGetxp.setSelected(Status_HUD.isShow[6]);
		checkBox_isShowGetxp.setBounds(22, 182, 151, 21);
		contentPane.add(checkBox_isShowGetxp);

		checkBox_isShowHavexp.setBackground(Color.WHITE);
		checkBox_isShowHavexp.setSelected(Status_HUD.isShow[7]);
		checkBox_isShowHavexp.setBounds(22, 205, 151, 21);
		contentPane.add(checkBox_isShowHavexp);

		checkBox_isShowRank.setSelected(Status_HUD.isShow[8]);
		checkBox_isShowRank.setBackground(Color.WHITE);
		checkBox_isShowRank.setBounds(22, 228, 151, 21);
		contentPane.add(checkBox_isShowRank);

		checkBox_isShowRankPoint.setSelected(Status_HUD.isShow[9]);
		checkBox_isShowRankPoint.setBackground(Color.WHITE);
		checkBox_isShowRankPoint.setBounds(22, 251, 213, 21);
		contentPane.add(checkBox_isShowRankPoint);

		checkBox_isShowCurrentJob.setBackground(Color.WHITE);
		checkBox_isShowCurrentJob.setSelected(Status_HUD.isShow[10]);
		checkBox_isShowCurrentJob.setBounds(22, 274, 151, 21);
		contentPane.add(checkBox_isShowCurrentJob);

		spinner_x.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_x.setValue(Status_HUD.x);
		spinner_x.setBounds(302, 151, 157, 27);
		contentPane.add(spinner_x);

		spinner_y.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_y.setValue(Status_HUD.y);
		spinner_y.setBounds(302, 203, 157, 27);
		contentPane.add(spinner_y);

		textField.setText(Status_HUD.text);
		textField.setBounds(205, 76, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBox_isRainbow.setBackground(Color.WHITE);
		checkBox_isRainbow.setSelected(Rendering.isRainbow);
		checkBox_isRainbow.setBounds(315, 288, 151, 21);
		contentPane.add(checkBox_isRainbow);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TextComponentTranslation("yadokaris_shp.setting.settingText").getUnformattedText());
		textPane.setBounds(205, 52, 106, 21);
		contentPane.add(textPane);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TextComponentTranslation("yadokaris_shp.setting.settingPoint").getUnformattedText());
		textPane_1.setBounds(302, 123, 123, 21);
		contentPane.add(textPane_1);

		JTextPane txtpnX = new JTextPane();
		txtpnX.setText("X");
		txtpnX.setBounds(277, 157, 13, 21);
		contentPane.add(txtpnX);

		JTextPane txtpnY = new JTextPane();
		txtpnY.setText("Y");
		txtpnY.setBounds(277, 209, 13, 21);
		contentPane.add(txtpnY);

		JButton button_set = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingSave").getUnformattedText());
		button_set.setBounds(365, 325, 106, 27);
		button_set.addActionListener(new SaveConfig());
		contentPane.add(button_set);
	}
}
