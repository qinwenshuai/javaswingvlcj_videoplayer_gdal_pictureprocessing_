package com.map.utils;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.image.*;

public class PictureShow implements Icon {
	private Icon icon = null;

	public PictureShow(Icon icon) {
		this.icon = icon;
	}

	public int getIconHeight() {
		return icon.getIconHeight();
	}

	public int getIconWidth() {
		return icon.getIconWidth();
	}

	public void paintIcon(Component component, Graphics graphics, int x, int y) {
		float width = component.getWidth();
		float height = component.getHeight();
		int iconWid = icon.getIconWidth();
		int iconHei = icon.getIconHeight();

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.scale(width / iconWid, height / iconHei);
		icon.paintIcon(component, g2d, 0, 0);
	}
}