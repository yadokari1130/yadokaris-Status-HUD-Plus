package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

public class ColorSetting implements ActionListener {

	private final HasColorFrame frame;

	public ColorSetting(HasColorFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Color color = new JColorChooser().showDialog(frame, "Color", Color.white);

		if (color != null) {
			String red = Integer.toHexString(color.getRed());
			String green = Integer.toHexString(color.getGreen());
			String blue = Integer.toHexString(color.getBlue());
			if (red.length() < 2) red = "0" + red;
			if (green.length() < 2) green = "0" + green;
			if (blue.length() < 2) blue = "0" + blue;

			frame.color = Integer.parseInt(red + green + blue, 16);
		}
		else {
			frame.color = -1;
		}
	}
}
