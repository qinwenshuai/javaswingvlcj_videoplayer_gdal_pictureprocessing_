package com.map.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import com.map.utils.FileOperation;
import com.map.utils.Harris;
import com.map.utils.PictureConversion;
import com.map.utils.PictureShow;
import com.map.utils.ReadTiff;
import com.map.video.FileMutiu2;
import com.map.video.VideoPlayer;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.globes.EarthFlat;

public class InitUI extends JFrame {
	public InitUI() {
	}

	public static JPanel mainPane = null; // 主要的JPanel，该JPanel的布局管理将被设置成CardLayout
	private JPanel p_btn = null;
	public static CardLayout card = null; // CardLayout布局管理器
	private JButton b_1 = null, b_2 = null, b_3 = null;// 三个可直接翻转到JPanel组件的按钮
	public JPanel p_1 = null, p_2 = null, p_3 = null, p_5 = null;// 要切换的三个JPanel
	public static JPanel p_4;
	private JTextArea textArea;	
	public JButton harris;
	private JButton openFile;
	private JButton saveFile;
	private JButton reSave;

	private String demo;
	private String rootPath;
	int c1, c2, c3, c4;
	MouseMotionListener control;
	MouseListener control2;

	public static int flag = 10086;
	public static JLabel label;
	public static int flag1 = 1;

	public void initUI() {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		card = new CardLayout(5, 5); // 创建一个具有指定的水平和垂直间隙的新卡片布局
		mainPane = new JPanel(card); // JPanel的布局管理将被设置成CardLayout
		p_btn = new JPanel(); // 构造放按钮的JPanel

		b_1 = new JButton("2D");
		b_1.setPreferredSize(new Dimension(40, 20));
		b_2 = new JButton("3D");
		b_2.setPreferredSize(new Dimension(40, 20));
		b_3 = new JButton("Video");
		b_3.setPreferredSize(new Dimension(60, 20));
		p_btn.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 3));
		p_btn.add(b_1);
		p_btn.add(b_2);
		p_btn.add(b_3);

		p_1 = new JPanel(new BorderLayout());
		p_2 = new JPanel(new BorderLayout());
		p_3 = new JPanel(new BorderLayout());
		p_4 = new JPanel(new BorderLayout());
		p_5 = new JPanel(new BorderLayout());

		textArea = new JTextArea();
		p_3.add(textArea, BorderLayout.CENTER);
		//TreeDemo test = new TreeDemo();
		//p_3.add(test.test1(), BorderLayout.NORTH);

		/*
		 * JButtonTable t1 = new JButtonTable(); p_3.add(t1.test2(),
		 * BorderLayout.CENTER);
		 */
		mainPane.add(p_1, "p1");
		mainPane.add(p_2, "p2");
		mainPane.add(p_4, "p4");
		mainPane.add(p_5, "p5");

		JSplitPane jSplitPane = new JSplitPane();
		jSplitPane.setOrientation(0);
		jSplitPane.setLeftComponent(p_btn);
		jSplitPane.setRightComponent(mainPane);
		JSplitPane jSplitPane1 = new JSplitPane();
		jSplitPane1.setLeftComponent(null);
		jSplitPane1.setRightComponent(jSplitPane);

		// 拖动分隔条时连续重绘组件
		jSplitPane.setContinuousLayout(true);
		jSplitPane1.setContinuousLayout(true);

		// 设置分隔条的初始位置
		jSplitPane.setDividerLocation(30);
		jSplitPane1.setDividerLocation(200);

		this.setSize(1200, 900);
		this.setTitle("图片视频处理");
		// this.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(jSplitPane1);
		this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);

		// 3D
		WorldWindowGLCanvas windowGLCanvas3D = new WorldWindowGLCanvas();// 新建一个视图View
		windowGLCanvas3D.setModel(new BasicModel());
		p_2.add(windowGLCanvas3D);
		this.setLocationRelativeTo(null);

		// 2D
		Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
		WorldWindowGLCanvas windowGLCanvas2D = new WorldWindowGLCanvas();// 新建一个视图View
		windowGLCanvas2D.setModel(new BasicModel());
		p_1.add(windowGLCanvas2D);

		// Video
		VideoPlayer.main1(p_5);

		JTabbedPane tabbedPane = new JTabbedPane();

		// 菜单栏
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 3));
		JToolBar toolBar1 = new JToolBar();
		toolBar1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar2 = new JToolBar();
		toolBar2.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar3 = new JToolBar();
		toolBar3.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar4 = new JToolBar();
		toolBar4.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar5 = new JToolBar();
		toolBar5.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar6 = new JToolBar();
		toolBar6.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar7 = new JToolBar();
		toolBar7.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 3));
		JToolBar toolBar8 = new JToolBar();
		toolBar8.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar9 = new JToolBar();
		toolBar9.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		JToolBar toolBar10 = new JToolBar();
		toolBar10.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

		tabbedPane.addTab("文件", toolBar7);
		openFile = new JButton();
		openFile.setBounds(0, 0, 30, 30);
		ImageIcon fileicon = new ImageIcon("images//openfile.png");
		Image ifile = fileicon.getImage().getScaledInstance(openFile.getWidth(), openFile.getHeight(),
				fileicon.getImage().SCALE_DEFAULT);
		fileicon = new ImageIcon(ifile);
		openFile.setIcon(fileicon);
		openFile.setToolTipText("打开");

		saveFile = new JButton();
		saveFile.setBounds(0, 0, 30, 30);
		ImageIcon saveicon = new ImageIcon("images//save.png");
		Image isave = saveicon.getImage().getScaledInstance(saveFile.getWidth(), saveFile.getHeight(),
				saveicon.getImage().SCALE_DEFAULT);
		saveicon = new ImageIcon(isave);
		saveFile.setIcon(saveicon);
		saveFile.setToolTipText("保存");

		reSave = new JButton();
		reSave.setBounds(0, 0, 30, 30);
		ImageIcon resaveicon = new ImageIcon("images//resave.png");
		Image iresave = resaveicon.getImage().getScaledInstance(reSave.getWidth(), reSave.getHeight(),
				resaveicon.getImage().SCALE_DEFAULT);
		resaveicon = new ImageIcon(iresave);
		reSave.setIcon(resaveicon);
		reSave.setToolTipText("另存为");

		toolBar7.add(openFile);
		toolBar7.add(saveFile);
		toolBar7.add(reSave);


		this.getContentPane().add(tabbedPane, BorderLayout.NORTH);
		tabbedPane.setPreferredSize(new Dimension(0, 100));
		this.setVisible(true);
		// set_click(cut, p_4,this);
	}

	public void onClickBtn() {
		// 切换2D\3D\视频
		b_1.addActionListener(new ActionListener() {
			// 直接翻转到p_1
			public void actionPerformed(ActionEvent e) {
				card.show(mainPane, "p1");
			}
		});
		b_2.addActionListener(new ActionListener() {
			// 直接翻转到p_2
			public void actionPerformed(ActionEvent e) {
				card.show(mainPane, "p2");
			}
		});
		b_3.addActionListener(new ActionListener() {
			// 直接翻转到视频
			public void actionPerformed(ActionEvent e) {
				card.show(mainPane, "p5");
			}
		});
		label = new JLabel();
		openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileOperation file = new FileOperation();
				rootPath = file.openFile("D:\\images\\open");
				label.setIcon(new PictureShow(new ImageIcon(rootPath)));
				p_4.add(label);
				card.show(mainPane, "p4");
				flag = 0;
			}
		});

		saveFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("保存文件");
			}
		});

		reSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("另存文件");
				FileOperation file = new FileOperation();
				try {
					file.saveFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		
		

	}
}
