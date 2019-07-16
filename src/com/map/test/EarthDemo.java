	package com.map.test;

import javax.swing.*;
import com.map.ui.InitUI;


public class EarthDemo extends JFrame {
	InitUI mUI = new InitUI();
	
	public EarthDemo() {
		mUI.initUI();
		mUI.onClickBtn();
	}

/*	public static void main(String[] args) {
		new EarthDemo();
	}*/
}
