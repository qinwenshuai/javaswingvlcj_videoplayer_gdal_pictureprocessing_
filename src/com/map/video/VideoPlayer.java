package com.map.video;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.map.ui.InitUI;
import com.map.utils.PictureConversion;
import com.map.video.MainWindow;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * 项目名称：EarthDemo   
 * 类名称：VideoPlayer   
 * 类描述：  此类为基于VLCJ播放视频的类，实现了视频的加载，播放，暂停，快进。
 * 创建人：秦文帅   
 * 创建时间：2019年7月8日 上午10:39:33   
 * 
 */
public class VideoPlayer {

	// VLC播放器系统库的路径：C:\software\VLC
	private static final String NATIVE_LIBRARY_SEARCH_PATH = "C:\\software\\VLC";
	
	public static String videoPath = "D:\\VideoExamples\\xiangyu.mp4";
    public static long currentTime;


	// 将声明转移到类中，并设为 static
	static MainWindow frame;

	public static void main1(JPanel jpanel) {
		// (1)法一：首先要找到本机库（VLC播放器的系统库），这是自动搜索本机库的位置
		boolean found = new NativeDiscovery().discover();
		// System.out.println(found);
		// System.out.println(LibVlc.INSTANCE.libvlc_get_version());

		// 判断当前的系统是否是Windows，还可以判断Mac和Linux，以传入不同的路径
		if (RuntimeUtil.isWindows()) {
			// (2)法二：手动设置本机库（VLC播放器的系统库）的路径
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);
			// System.out.println(LibVlc.INSTANCE.libvlc_get_version());
		}

		// 加载VLC播放器的系统库
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		// 在主方法中创建窗体
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindow();
					frame.setVisible(true);
					jpanel.add(frame);
					// 字幕的编码
					String options = "--subsdec-encoding=GB18030";
					
					//预播放视频，就是视频刚打开时默认打开的视频
					frame.getMediaPlayer().prepareMedia("D:\\VideoExamples\\xiangyu.mp4", options);
					frame.getMediaPlayer().toggleFullScreen();
					
					// 创建一个 SwingWorker 线程，用于实时调节进度
					// 注意：创建完毕，最后要 execute() 将它运行起来
					new SwingWorker<String, Integer>() {
						@Override
						protected String doInBackground() throws Exception {
							while (true) {
								// 视频总时长，以毫秒计
								long total = frame.getMediaPlayer().getLength();
								// 当前所看时长，以毫秒计
								long curr = frame.getMediaPlayer().getTime();
								currentTime=curr;
								// 百分比，并强转为 float
								float percent = (float) curr / total;
								publish((int) (percent * 100));	
								// 每隔 0.1 秒(100毫秒)更新一次进度条，如果不加则刷新过快
								Thread.sleep(100);
							}
						}

						protected void process(java.util.List<Integer> chunks) {
							// 创建int型变量 value 接收 chunks 中的值
							for (int value : chunks) {
								frame.getProgressBar().setValue(value);
							}
						};
					}.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// 播放
	public static void play() {
		frame.getMediaPlayer().play();
	}

	// 暂停
	public static void pause() {
		frame.getMediaPlayer().pause();
	}

	// 停止
	public static void stop() {
		frame.getMediaPlayer().stop();
	}

	public static void jumpTo(float to) {
		// 为跳转设定时间：百分比乘以视频时间的总长
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}

	public static void openVideo() {

		// 创建文件选择器：JFileChooser
		JFileChooser chooser = new JFileChooser();
		// 这里用到前面自定义的文件类型过滤器，这里取了avi，mp4，mkv，flv四种常见的视频格式，其余的文件类型都过滤掉
		MyFileFilter filter = new MyFileFilter();
		filter.addExtension("avi");
		filter.addExtension("mp4");
		filter.addExtension("mkv");
		filter.addExtension("flv");
		filter.setDescription("视频文件");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			frame.getMediaPlayer().playMedia(chooser.getSelectedFile().getAbsolutePath());
			videoPath = chooser.getSelectedFile().getAbsolutePath();
		}
	}
	
	public static void save() throws Exception {
		JFrame frame = new JFrame();
		JFileChooser jf = new JFileChooser();
		File fileFlag = new File(videoPath);
		jf.setCurrentDirectory(fileFlag);// 设置打开对话框的默认路径
		jf.setSelectedFile(fileFlag);// 设置选中原来的文件
		jf.showSaveDialog(frame);// 显示打开的文件对话框

		String resavePath = jf.getSelectedFile().toString();// 另存时选择的路径
		if (resavePath.indexOf(".") != -1) {
			resavePath = resavePath.substring(0, resavePath.indexOf("."));
		}
		// 以下两句是获得原文件的扩展名
		int flag = videoPath.lastIndexOf(".");
		String kuozhan = videoPath.substring(flag);

		// 这里改用了文件流
		FileInputStream input;
		try {
			input = new FileInputStream(videoPath);
			   BufferedInputStream inBuff = new BufferedInputStream(input); // 新建文件输入流并对它进行缓冲
			   FileOutputStream output = new FileOutputStream(resavePath + kuozhan);// 把扩展名添加到原来文件的后面
			   BufferedOutputStream outBuff = new BufferedOutputStream(output); // 新建文件输出流并对它进行缓冲
			   // 缓冲数组
			   byte[] b = new byte[1024 * 5];
			   int len;
			   while ((len = inBuff.read(b)) != -1) {
			    outBuff.write(b, 0, len);
			   }
			   outBuff.flush();
			   
			   inBuff.close();
			   outBuff.close();
			   output.close();
			   input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}

	public static void exit() {
		// 在退出之前，先释放播放器的资源
		frame.getMediaPlayer().release();
		//System.exit(0);
	}

	public static void setVol(int vol) {
		// 设定音量
		frame.getMediaPlayer().setVolume(vol);
	}
}