package com.map.video;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import com.sun.prism.paint.Color;

import javafx.scene.layout.Border;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


/**
 *
 * 项目名称：EarthDemo   
 * 类名称：MainWindow   
 * 类描述： 此类为播放视频的主界面，完成了视频读取播放的基本功能，之后会集成到主界面上！ 
 * 创建人：秦文帅   
 * 创建时间：2019年7月8日 上午10:57:58   
 * 
 */
public class MainWindow extends JPanel {

	// 创建播放器的界面需要使用 EmbeddedMediaPlayerComponent
	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel bottomPane;	
	public static JButton btnSave;
	public static JButton btnOpen;
	//private JButton btnExit;
	private JButton btnPlay;
	private JButton btnPause;
	public static JButton btnStop;
	public static JButton btnstart_choice;
	public static JButton btnstop_choice;
	private JButton btncancel_choice;
	private JPanel controlPane;
	private JProgressBar progress;
	private JSlider slider;
	private String imgPath;
	private JLabel msgLabel;
    public static long startTime=0;
    public static long selectTime=0;
   
	public MainWindow() {
		
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));

		JPanel videoPane = new JPanel();
		this.add(videoPane, BorderLayout.CENTER);
		videoPane.setLayout(new BorderLayout(0, 0));
		
		// 在 videoPane 创建之后实例化 playerComponent
		playerComponent = new EmbeddedMediaPlayerComponent();

		// 将 playerComponent 添加到 videoPane 中，并指定布局
		videoPane.add(playerComponent, BorderLayout.CENTER);

		bottomPane = new JPanel();
		videoPane.add(bottomPane, BorderLayout.SOUTH);
		bottomPane.setLayout(new BorderLayout(0, 0));
		controlPane = new JPanel();
		bottomPane.add(controlPane);
		
		btnOpen = new JButton("打开");
		controlPane.add(btnOpen);
		btnOpen.setFocusable(false);
		
//		btnExit = new JButton("关闭");
//		controlPane.add(btnExit);
//		btnExit.setFocusable(false);
		
		btnSave = new JButton("另存为");
		controlPane.add(btnSave);
		btnSave.setFocusable(false);

		btnStop = new JButton("停止");
		controlPane.add(btnStop);
		btnStop.setFocusable(false);

		btnPlay = new JButton("播放");
		controlPane.add(btnPlay);
		btnPlay.setFocusable(false);

		btnPause = new JButton("暂停");
		controlPane.add(btnPause);
		btnPause.setFocusable(false);
        
		btnstart_choice = new JButton("拆帧");
		controlPane.add(btnstart_choice);
		btnstart_choice.setFocusable(false);
        	
		btnstop_choice = new JButton("选择此处");
		controlPane.add(btnstop_choice);
		btnstop_choice.setFocusable(false);
		btnstop_choice.setVisible(false);
		
        slider = new JSlider();
		slider.setFocusable(false);

	
		// 关于默认音量的设定要在 stateChanged 事件之前
		// 如果要设定最大音量和最小音量也是如此
		slider.setValue(20);

		// 为音量调节 slider 添加 stateChanged 事件
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				VideoPlayer.setVol(slider.getValue());
			}
		});

		controlPane.add(slider);
		progress = new JProgressBar();
		progress.setFocusable(false);
		
		msgLabel = new JLabel();
		controlPane.add(msgLabel);
		
		//为开始拆帧按钮添加click事件
		btnstart_choice.addMouseListener(new MouseAdapter(){
			/*
	    	 * 
	    	 * 功能：拆帧功能界面
	    	 * 创建人：蒋程扬
	    	 * 创建时间：2019年7月14日15:59:14
	    	 */
			public void mouseClicked(MouseEvent e){
				
				//获取要保存帧文件的目录
//				// 创建文件选择器：JFileChooser
//				
				//暂停视频的播放
				VideoPlayer.pause();
				SplitVideo sv = new SplitVideo();
		        
			}
		});
		
		//为选择这里按钮添加click事件
	    btnstop_choice.addMouseListener(new MouseAdapter(){
	    	/*
	    	 * 
	    	 * 功能：opencv拆分视频帧
	    	 * 创建人：蒋程扬
	    	 */
			public void mouseClicked(MouseEvent e){
				selectTime=VideoPlayer.currentTime;
				if (SplitVideo.mode==1){
					SplitVideo.startText.setText(String.valueOf(selectTime));
				}
				else if(SplitVideo.mode==2){
					SplitVideo.stopText.setText(String.valueOf(selectTime));
				}
				else if(SplitVideo.mode==3){
					SplitVideo.thisText.setText(String.valueOf(selectTime));
				}
				
				btnstart_choice.setEnabled(true);
				btnstop_choice.setVisible(false);
				MainWindow.btnOpen.setVisible(true);
				MainWindow.btnSave.setVisible(true);
				MainWindow.btnstart_choice.setVisible(true);
				MainWindow.btnStop.setVisible(true);
				SplitVideo.jmodeFrame.setVisible(true);
			}	
			});
	
		// 为进度条 progress 添加 mouseClicked 事件
		progress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 获取鼠标点击在进度条上的位置
				int x = e.getX();
				// 计算点击位置占进度条总长的百分比
				float per = (float) x / progress.getWidth();
				VideoPlayer.jumpTo(per);
			}
		});

		// 让进度条 progress 显示数字百分比
		progress.setStringPainted(true);
		bottomPane.add(progress, BorderLayout.NORTH);
		
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoPlayer.openVideo();
			}
		});
		
//		btnExit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				VideoPlayer.exit();
//			}
//		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					VideoPlayer.save();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
			}
		});

		// 为 Stop 按钮添加 mouseClicked 事件
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VideoPlayer.stop();
			}
		});

		// 为 Play 按钮添加 mouseClicked 事件
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VideoPlayer.play();
			}
		});

		// 为 Pause 按钮添加 mouseClicked 事件
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VideoPlayer.pause();
			}
		});
	}
	//毫秒转时分
	public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }
	

	
	// 返回媒体播放器的实例
	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}

	// 返回JProgressBar的实例
	public JProgressBar getProgressBar() {
		return progress;
	}
}
