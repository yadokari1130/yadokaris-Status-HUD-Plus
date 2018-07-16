package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

public class ColorSetting implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {

		Color color = new JColorChooser().showDialog(new SettingGui(), "Color", Color.white);

		if (color != null) {
			String red = Integer.toHexString(color.getRed());
			String green = Integer.toHexString(color.getGreen());
			String blue = Integer.toHexString(color.getBlue());
			if (red.length() < 2) red = "0" + red;
			if (green.length() < 2) green = "0" + green;
			if (blue.length() < 2) blue = "0" + blue;

			Status_HUD.color = Integer.parseInt(red + green + blue, 16);
			Status_HUD.colorCash = Status_HUD.color;
		}
	}
}
