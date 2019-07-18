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
	public JPanel p_1 = null, p_2 = null, p_3 = null,  p_5 = null;// 要切换的三个JPanel
	public static JPanel p_4 ;
	private JTextArea textArea;
	private JButton jTif;
	private JButton jJepg;
	private JButton jPng;
	private JButton jGif;
	private JButton jBmp;
	public JButton harris;
	private JButton sift;
	private JButton surf;
	private JButton openFile;
	private JButton saveFile;
	private JButton reSave;
	private JButton zuilinjin;
	private JButton shuangxianxing;
	private JButton sancijuanji;
	private JButton button8;
	private JButton superResolution;
	private JButton moveDetection;
	private String demo;
	private String rootPath;
	int c1, c2, c3, c4;
	MouseMotionListener control;
	MouseListener control2;
	private JButton cut;
	private JButton action;
	private JButton process;
	private JButton system;
	private JButton CPU;
	private JButton strength;
	public static int flag = 10086;
	private JButton low;
	private JButton high;
	private JButton fusion;
	private JButton picture;
	private JButton pinjie;
	private JButton vertical;
	private JButton horizontal;
	private JButton histogram;
	private JButton logarithm;
	private JButton deviation;
	public static JLabel label;
	public static int flag1=1;

	
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
		TreeDemo test = new TreeDemo();
		p_3.add(test.test1(), BorderLayout.NORTH);

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
		jSplitPane1.setLeftComponent(p_3);
		jSplitPane1.setRightComponent(jSplitPane);

		// 拖动分隔条时连续重绘组件
		jSplitPane.setContinuousLayout(true);
		jSplitPane1.setContinuousLayout(true);

		// 设置分隔条的初始位置
		jSplitPane.setDividerLocation(30);
		jSplitPane1.setDividerLocation(200);

		this.setSize(1200, 900);
		this.setTitle("遥感图像超分辨率重建软件系统");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
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
        
        tabbedPane.addTab("格式转换", toolBar);
		
        jTif = new JButton();
        jTif.setBounds(0, 0, 30, 30);
		ImageIcon i1 = new ImageIcon("images//14.png");
		Image temp1 = i1.getImage().getScaledInstance(jTif.getWidth(), jTif.getHeight(),
				i1.getImage().SCALE_DEFAULT);
		i1 = new ImageIcon(temp1);
		jTif.setIcon(i1);
		jTif.setToolTipText("转TIF");
		
        jJepg = new JButton();
        jJepg.setBounds(0, 0, 30, 30);
		ImageIcon i2 = new ImageIcon("images//13.png");
		Image temp2 = i2.getImage().getScaledInstance(jJepg.getWidth(), jJepg.getHeight(),
				i2.getImage().SCALE_DEFAULT);
		i2 = new ImageIcon(temp2);
		jJepg.setIcon(i2);
		jJepg.setToolTipText("转JPG");
		
        jPng = new JButton();
        jPng.setBounds(0, 0, 30, 30);
		ImageIcon i3 = new ImageIcon("images//15.png");
		Image temp3 = i3.getImage().getScaledInstance(jPng.getWidth(), jPng.getHeight(),
				i3.getImage().SCALE_DEFAULT);
		i3 = new ImageIcon(temp3);
		jPng.setIcon(i3);
		jPng.setToolTipText("转PNG");
		
        jGif = new JButton();
        jGif.setBounds(0, 0, 30, 30);
		ImageIcon i4 = new ImageIcon("images//12.png");
		Image temp4 = i4.getImage().getScaledInstance(jGif.getWidth(), jGif.getHeight(),
				i4.getImage().SCALE_DEFAULT);
		i4 = new ImageIcon(temp4);
		jGif.setIcon(i4);
		jGif.setToolTipText("转GIF");
		
        jBmp = new JButton();
        jBmp.setBounds(0, 0, 30, 30);
		ImageIcon i5 = new ImageIcon("images//11.png");
		Image temp5 = i5.getImage().getScaledInstance(jBmp.getWidth(), jBmp.getHeight(),
				i5.getImage().SCALE_DEFAULT);
		i5 = new ImageIcon(temp5);
		jBmp.setIcon(i5);
		jBmp.setToolTipText("转BMP");
  //      toolBar.add(mReadWrite);
        toolBar.add(jTif);
        toolBar.add(jJepg);
        toolBar.add(jPng);
        toolBar.add(jGif);
        toolBar.add(jBmp); 
        
        tabbedPane.addTab("图层卷帘", toolBar1);
        vertical = new JButton("垂直卷帘");
        horizontal = new JButton("水平卷帘");
        toolBar1.add(vertical);
        toolBar1.add(horizontal);
        
        tabbedPane.addTab("影像显示", toolBar2);
        button8 = new JButton("加载");
        JButton button9 = new JButton("影像放大");
        JButton button10 = new JButton("影像缩小");
        JButton button11 = new JButton("影像平移");
        JButton button12 = new JButton("1:1显示");
        toolBar2.add(button8);
        toolBar2.add(button9);
        toolBar2.add(button10);
        toolBar2.add(button11);
        toolBar2.add(button12);
        
        tabbedPane.addTab("裁切", toolBar3);
        cut = new JButton("矩形裁切");
        
        JButton button14 = new JButton("不规则裁切");
        toolBar3.add(cut);
        toolBar3.add(button14);
        
        tabbedPane.addTab("增强", toolBar4);
        strength = new JButton("线性增强");
        histogram = new JButton("直方图增强");
        logarithm = new JButton("对数增强");
        deviation = new JButton("标准差增强");
        toolBar4.add(strength);
        toolBar4.add(histogram);
        toolBar4.add(logarithm);
        toolBar4.add(deviation);
        
        tabbedPane.addTab("重采样", toolBar5);
        zuilinjin = new JButton("最邻近内插法");
        shuangxianxing = new JButton("双线性内插法");
        sancijuanji = new JButton("三次卷积法内插");
        toolBar5.add(zuilinjin);
        toolBar5.add(shuangxianxing);
        toolBar5.add(sancijuanji);
        
        tabbedPane.addTab("配准", toolBar6);
        harris = new JButton("Harris");
        sift = new JButton("SIFT");
        surf = new JButton("SURF");
        superResolution = new JButton("超分辨率重建");
        moveDetection = new JButton("运动检测");
        toolBar6.add(harris);
        toolBar6.add(sift);
        toolBar6.add(surf);
        toolBar6.add(superResolution);
        toolBar6.add(moveDetection);
        
        tabbedPane.addTab("融合", toolBar9);
        low = new JButton("低分辨率");
        high = new JButton("高分辨率");
        fusion = new JButton("融合");
        toolBar9.add(low);
        toolBar9.add(high);
        toolBar9.add(fusion);
        
        tabbedPane.addTab("拼接", toolBar10);
        picture = new JButton("图片");
        pinjie = new JButton("拼接");
        toolBar10.add(picture);
        toolBar10.add(pinjie);
        
        tabbedPane.addTab("任务监控", toolBar8);
        CPU = new JButton("cpu利用率");
        action = new JButton("内存占用率");
        process = new JButton("I/O负载");
        system = new JButton("流量监控");
        toolBar8.add(CPU);
        toolBar8.add(action);
        toolBar8.add(process);
        toolBar8.add(system);
        
        
        
          
        this.getContentPane().add(tabbedPane, BorderLayout.NORTH);
        tabbedPane.setPreferredSize(new Dimension(0,100));
		this.setVisible(true);
		//set_click(cut, p_4,this);
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
				rootPath = file.openFile("F:\\Remote sensing project\\images");
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

		PictureConversion tiffTurn = new PictureConversion();
		// OpenFile openFile = new OpenFile();
		// String path = openFile.getPath();

		jPng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// tiffTurnPng转换原文件为.png文件后，默认将.png文件保存在原文件同目录下，并返回转换后的文件路径，
				// PictureShow根据返回的路径绘图。
				label.setIcon(new PictureShow(new ImageIcon(tiffTurn.tiffTurnPng(rootPath))));
				p_4.add(label);
				card.show(mainPane, "p4");
				flag = 1;
			}
		});
		jJepg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon(tiffTurn.tiffTurnJpg(rootPath))));
				p_4.add(label);
				card.show(mainPane, "p4");
				flag = 1;
			}
		});
		jBmp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon(tiffTurn.tiffTurnBmp(rootPath))));
				p_4.add(label);
				card.show(mainPane, "p4");
				flag = 1;
			}
		});
		jGif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon(tiffTurn.tiffTurnGif(rootPath))));
				p_4.add(label);
				card.show(mainPane, "p4");
				flag = 1;
			}
		});
		jTif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon(tiffTurn.jpgToTif(rootPath))));
				p_4.add(label);
				card.show(mainPane, "p4");
				flag = 1;
			}
		});
		harris.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {		
				 new HarrisUI();
			}
		});
		
		low.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {	
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\4.jpg")));//D:\\images
			    p_4.add(label);
			    card.show(mainPane, "p4");
				
			}
		});
		
		high.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {		
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\5.jpg")));
			    p_4.add(label);
			    card.show(mainPane, "p4");

			}
		});
		
		fusion.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {		
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\6.jpg")));
			    p_4.add(label);
			    card.show(mainPane, "p4");
			}
		});
		
		sift.addActionListener(new ActionListener() {  
			   @Override
			   public void actionPerformed(ActionEvent arg0) {
			    new SiftUI();
			   }
			  });
		surf.addActionListener(new ActionListener() {  
			   @Override
			   public void actionPerformed(ActionEvent e) {
				new SurfUI();
			   }
			  });
		superResolution.addActionListener(new ActionListener() {  
			   @Override
			   public void actionPerformed(ActionEvent e) {
				new SuperResolutionUI();
			   }
			  });
			  moveDetection.addActionListener(new ActionListener() {  //C:\Users\as\Desktop\images
			   @Override
			   public void actionPerformed(ActionEvent e) {
			    //label.setIcon(new PictureShow(new ImageIcon("C:\\Users\\瑞\\Desktop\\images\\traffic1.jpg")));
			    //p_4.add(label);
			    card.show(mainPane, "p5");
			   }
			  });
		zuilinjin.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\low.jpg")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		shuangxianxing.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\low1.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		sancijuanji.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\low2.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		vertical.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\卷帘前.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		horizontal.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\卷帘后.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		
		button8.addActionListener(new ActionListener() {		
			

			@Override
			public void actionPerformed(ActionEvent e) {
				ReadTiff readTiff = new ReadTiff();
				demo = readTiff.Tiftxt();
				String filePath = demo;
				FileInputStream fin;
				textArea.setText("");
				try {
					fin = new FileInputStream(filePath);
		        InputStreamReader reader = new InputStreamReader(fin);
		        BufferedReader buffReader = new BufferedReader(reader);
		        String strTmp = "";
		        while((strTmp = buffReader.readLine())!=null){
		        	textArea.append(strTmp+"\n");
					textArea.paintImmediately(textArea.getBounds());
					textArea.setLineWrap(true);  
					textArea.setWrapStyleWord(true);
		        } buffReader.close();
		        } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		strength.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\yuan1.jpg")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		histogram.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\yuan2.jpg")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		logarithm.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\yuan3.jpg")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		deviation.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\yuan4.jpg")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		CPU.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\cpu占用率.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		
		action.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\内存占用率.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		process.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\IO负载.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		system.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\流量监控.png")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		picture.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag1==1){
				JFrame frame = new JFrame("影像1");//构造一个新的JFrame，作为新窗口。
            	frame.setBounds(100, 100, 300, 300);
            	frame.setLocationRelativeTo(null);
            	frame.setAlwaysOnTop(true);
            	JLabel jl = new JLabel();// 注意类名别写错了。
            	frame.getContentPane().add(jl);
            	jl.setIcon(new PictureShow(new ImageIcon("D:\\images\\a.jpg")));
            	jl.setVerticalAlignment(JLabel.CENTER);
            	jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
            	frame.setVisible(true);}
				if (flag1==2)
				{
					JFrame frame = new JFrame("影像2");//构造一个新的JFrame，作为新窗口。
	            	frame.setBounds(100, 100, 300, 300);
	            	frame.setLocationRelativeTo(null);
	            	frame.setAlwaysOnTop(true);
	            	JLabel jl = new JLabel();// 注意类名别写错了。
	            	frame.getContentPane().add(jl);
	            	jl.setIcon(new PictureShow(new ImageIcon("D:\\images\\b.jpg")));
	            	jl.setVerticalAlignment(JLabel.CENTER);
	            	jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
	            	frame.setVisible(true);}
				if (flag1==3)
				{
					JFrame frame = new JFrame("影像3");//构造一个新的JFrame，作为新窗口。
	            	frame.setBounds(100, 100, 300, 300);
	            	frame.setLocationRelativeTo(null);
	            	frame.setAlwaysOnTop(true);
	            	JLabel jl = new JLabel();// 注意类名别写错了。
	            	frame.getContentPane().add(jl);
	            	jl.setIcon(new PictureShow(new ImageIcon("D:\\images\\c.jpg")));
	            	jl.setVerticalAlignment(JLabel.CENTER);
	            	jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
	            	frame.setVisible(true);}
				  flag1=flag1+1;
				  if (flag1==4){flag1=1;}
				  
			}
		});
		pinjie.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new PictureShow(new ImageIcon("D:\\images\\总.jpg")));
				p_4.add(label);
				card.show(mainPane, "p4");
			}
		});
		cut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PictureCut pictureCut = new PictureCut();
				pictureCut.doStart();
			}
			});
		
	}
}

