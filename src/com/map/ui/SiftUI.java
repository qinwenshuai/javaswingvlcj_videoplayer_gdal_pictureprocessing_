package com.map.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.map.utils.FileOperation;
import com.map.utils.PictureShow;

public class SiftUI {

	public SiftUI() {	
		JFrame frame = new JFrame();// 构造一个新的JFrame，作为新窗口。
		frame.setTitle("遥感图像超分辨率重建软件系统");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		frame.setBounds(200, 200, 1000, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);		
		
		JPanel myPanel1 = new JPanel(new BorderLayout());//面板1 
		JPanel myPanel2 =new JPanel(new BorderLayout());//面板2 
		JPanel myPanel3 =new JPanel();//面板3
		JPanel parameter =new JPanel();//参数面板
		
		//子布局左右拆分
		JSplitPane jSplitPane =new JSplitPane();//设定为左右拆分布局	
		jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向 
		jSplitPane.setLeftComponent(myPanel1);//子布局中添加面板1 
		jSplitPane.setRightComponent(myPanel2);//添加面板2 
		jSplitPane.setDividerSize(10);//设置分割线的宽度
		jSplitPane.setDividerLocation(400);//设定分割线的距离左边的位置 
		
		//中布局上下拆分
		JSplitPane jSplitPane2 = new JSplitPane();
		jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);//设置分割线方向 
		jSplitPane2.setTopComponent(jSplitPane);
		jSplitPane2.setBottomComponent(myPanel3);
		jSplitPane2.setDividerLocation(410);//设定分割线的距离上边的位置 
		myPanel3.setLayout(null);//自定义布局
		
		//主布局左右拆分
		JSplitPane jSplitPane3 = new JSplitPane();
		jSplitPane3.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向 
		jSplitPane3.setTopComponent(parameter);
		jSplitPane3.setBottomComponent(jSplitPane2);
		jSplitPane3.setDividerLocation(150);//设定分割线的距离左边的位置 
		
		JLabel jLabel = new JLabel("连接点匹配度阈值:");
        JTextField jTextField = new JTextField(10);
        JLabel jLabel2 = new JLabel("影像几何模型:");
        JComboBox cmb=new JComboBox();//创建JComboBox
        cmb.addItem("--请选择--");    //向下拉列表中添加一项
        cmb.addItem("单位四元数定位模型");
        cmb.addItem("对偶四元数定位模型");
        cmb.addItem("SISAR定位模型");
        JLabel jLabel3 = new JLabel("连接点误差阈值:");
        JTextField jTextField3 = new JTextField(10);
        
        parameter.add(jLabel);
        parameter.add(jTextField);
        parameter.add(jLabel2);
        parameter.add(cmb);
        parameter.add(jLabel3);
        parameter.add(jTextField3);
		
		JButton btn = new JButton("基准影像");
		btn.setBounds(320, 10, 80, 20);//按钮的位置大小
		JButton btn1 = new JButton("待配准影像");
		btn1.setBounds(420, 10, 80, 20);//按钮的位置大小
		JButton OK = new JButton("配准");
		OK.setBounds(730, 10, 80, 20);//按钮的位置大小
		myPanel3.add(btn);
		myPanel3.add(btn1);
		myPanel3.add(OK);
		
		//设置lable显示图片
		JLabel jl = new JLabel();
		JLabel j2 = new JLabel();
		myPanel1.add(jl);
		myPanel2.add(j2);

		jl.setVerticalAlignment(JLabel.CENTER);
		jl.setHorizontalAlignment(JLabel.CENTER);
		
		j2.setVerticalAlignment(JLabel.CENTER);
		j2.setHorizontalAlignment(JLabel.CENTER);
		
		frame.setContentPane(jSplitPane3);		
		frame.setVisible(true);
		
		
		btn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				FileOperation file = new FileOperation();
				String rootPath = file.openFile();
				jl.setIcon(new PictureShow(new ImageIcon(rootPath)));
			}
		});
		btn1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				FileOperation file = new FileOperation();
				String rootPath = file.openFile();
				j2.setIcon(new PictureShow(new ImageIcon(rootPath)));
			}
		});
		InitUI initUI = new InitUI();
		OK.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				initUI.label.setIcon(new PictureShow(new ImageIcon("D:\\images\\result.JPG")));
				initUI.p_4.add(initUI.label);
				initUI.card.show(initUI.mainPane, "p4");
			}
		});
	}
}
