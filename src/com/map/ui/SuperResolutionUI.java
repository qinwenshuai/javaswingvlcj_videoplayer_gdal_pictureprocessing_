package com.map.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.map.utils.FileOperation;
import com.map.utils.PictureShow;

public class SuperResolutionUI {

	public SuperResolutionUI() {	
		JFrame frame = new JFrame();// 构造一个新的JFrame，作为新窗口。
		frame.setTitle("遥感图像超分辨率重建软件系统");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		frame.setBounds(200, 200, 600, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);		
		
		JPanel myPanel1 = new JPanel(new BorderLayout());//面板1 
		JPanel myPanel2 =new JPanel(new BorderLayout());//面板2 
		
		//子布局左右拆分
		JSplitPane jSplitPane =new JSplitPane();//设定为左右拆分布局	
		jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);//设置分割线方向 
		jSplitPane.setTopComponent(myPanel1);//子布局中添加面板1 
		jSplitPane.setBottomComponent(myPanel2);//添加面板2 
		jSplitPane.setDividerSize(10);//设置分割线的宽度
		jSplitPane.setDividerLocation(400);//设定分割线的距离左边的位置 
		
		myPanel2.setLayout(null);//自定义布局
		JButton btn = new JButton("目标图");
		btn.setBounds(260, 10, 80, 20);//按钮的位置大小
		JButton OK = new JButton("重建");
		OK.setBounds(480, 10, 80, 20);//按钮的位置大小
		myPanel2.add(btn);
		myPanel2.add(OK);
		
		//设置lable显示图片
		JLabel jl = new JLabel();
		myPanel1.add(jl);
		jl.setVerticalAlignment(JLabel.CENTER);
		jl.setHorizontalAlignment(JLabel.CENTER);		
		frame.setContentPane(jSplitPane);		
		frame.setVisible(true);
			
		btn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				FileOperation file = new FileOperation();
				String rootPath = file.openFile();
				jl.setIcon(new PictureShow(new ImageIcon(rootPath)));
			}
		});

		InitUI initUI = new InitUI();
		OK.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				initUI.label.setIcon(new PictureShow(new ImageIcon("D:\\images\\high.png")));
				initUI.p_4.add(initUI.label);
				initUI.card.show(initUI.mainPane, "p4");
			}
		});
	}
}
