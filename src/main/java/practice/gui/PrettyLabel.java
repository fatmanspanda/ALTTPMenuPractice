package practice.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

import static practice.MenuGameConstants.*;

public class PrettyLabel extends JComponent {
	private static final long serialVersionUID = 8691532086942138069L;

	private BufferedImage bg;
	private int length;

	private String text;
	private BufferedImage disp;

	private String textRight;
	private BufferedImage dispRight;

	private BufferedImage icon;

	private Dimension d;

	public PrettyLabel(int length) {
		this.length = length;
		d = new Dimension(((length + 2) * 8) * ZOOM, 24 * ZOOM);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		bg = makePrettyBorder(length);
		setFocusable(false);
	}

	public void setText(String text) {
		this.text = text;
		disp = makeWordImage(this.text, WHITE);
		repaint();
	}

	public void setRightText(String text) {
		this.textRight = text;
		dispRight = makeWordImage(this.textRight, WHITE);
		repaint();
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
		repaint();
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(ZOOM, ZOOM);

		g2.drawImage(bg, 0, 0, null);

		int align = 0;
		if (icon != null) {
			g2.drawImage(icon, 8, 4, null);
			align = 16;
		}

		g2.drawImage(disp, 8 + align, 8, null);

		if (dispRight != null) {
			align =  (length - textRight.length()) * 8;
			g2.drawImage(dispRight, 8 + align, 8, null);
		}
	}
}