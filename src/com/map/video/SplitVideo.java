package com.map.video;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.opencv.core.Core;

import java.io.File;
import java.io.IOException;
import org.gdal.gdal.gdal;
import org.gdal.ogr.ogr;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

/**
 *
 * 项目名称：EarthDemo 类名称：SplitVideo 类描述： 此类为视频拆帧功能类 创建人：蒋程扬 创建时间：2019年7月15日10:54:25
 * 
 */
public class SplitVideo {
	private String imgPath;
	private JLabel msgLabel;
	public static JFrame jmodeFrame;
	private JButton notFixedButton;
	private JButton fixedButton;
	private JButton thisButton;
	private JPanel modePanel;
	public static int mode = 0;
	public static JTextField startText;
	public static JTextField stopText;
	public static JTextField thisText;

	public SplitVideo() {
		initUi();
	}

	private void initUi() {
		jmodeFrame = new JFrame("选择拆帧模式");
		jmodeFrame.setSize(350, 300);
		jmodeFrame.setLocationRelativeTo(null);
		jmodeFrame.setResizable(false);
		JPanel contentPane = new JPanel();
		contentPane.setSize(300, 300);
		jmodeFrame.add(contentPane);
		jmodeFrame.setVisible(true);

		Box boxbase = Box.createVerticalBox(); // 水平box
		Box box1 = Box.createHorizontalBox();
		Box box_bottom = Box.createHorizontalBox();
		Box box_fixed = Box.createVerticalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createHorizontalBox();
		Box box6 = Box.createHorizontalBox();
		Box box7 = Box.createHorizontalBox();
		notFixedButton = new JButton("自定义时间段");
		notFixedButton.setFocusable(false);
		box1.add(notFixedButton);
		box1.add(Box.createHorizontalStrut(30)); // 创建空白格

		fixedButton = new JButton("等长时间段");
		fixedButton.setFocusable(false);
		box1.add(fixedButton);
		box1.add(Box.createHorizontalStrut(30));

		thisButton = new JButton("单帧图片");
		thisButton.setFocusable(false);
		box1.add(thisButton);
		// 自定义拆帧
		// 创建边框

		box1.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		JLabel startLabel = new JLabel("开始时刻:");
		startText = new JTextField(20);
		JButton jstartBtn = new JButton("选择/ms");
		box3.add(startLabel);
		box3.add(Box.createHorizontalStrut(15));
		box3.add(startText);
		box3.add(Box.createHorizontalStrut(15));
		box3.add(jstartBtn);

		JLabel stopLabe1 = new JLabel("结束时刻:");
		stopText = new JTextField(20);
		JButton jstopBtn = new JButton("选择/ms");
		box4.add(stopLabe1);
		box4.add(Box.createHorizontalStrut(15));
		box4.add(stopText);
		box4.add(Box.createHorizontalStrut(15));
		box4.add(jstopBtn);

		JLabel saveLabe1 = new JLabel("保存位置:");
		JTextField filepath = new JTextField(20);
		JButton jfileBtn = new JButton("浏览   ");
		box5.add(saveLabe1);
		box5.add(Box.createHorizontalStrut(15));
		box5.add(filepath);
		box5.add(Box.createHorizontalStrut(15));
		box5.add(jfileBtn);

		JButton jok = new JButton("确认");
		JButton jcancal = new JButton("清空");
		box6.add(jok);
		box6.add(Box.createHorizontalStrut(90));
		box6.add(jcancal);

		JLabel label1 = new JLabel("进度:");
		// 创建一个进度条
		final JProgressBar progressBar = new JProgressBar();

		// 设置进度的 最小值 和 最大值
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		// 设置当前进度值
		progressBar.setValue(0);

		// 绘制百分比文本（进度条中间显示的百分数）
		progressBar.setStringPainted(true);

		box_bottom.add(label1);
		box_bottom.add(Box.createHorizontalStrut(15));
		box_bottom.add(progressBar);

		box_fixed.add(box3);
		box_fixed.add(Box.createVerticalStrut(10));
		box_fixed.add(box4);
		box_fixed.add(Box.createVerticalStrut(10));
		box_fixed.add(box5);
		box_fixed.add(Box.createVerticalStrut(10));
		box_fixed.add(box6);
		box_fixed.add(Box.createVerticalStrut(10));
		box_fixed.add(box7);
		box_fixed.setBorder(BorderFactory.createTitledBorder("自定义时间段拆帧"));
		boxbase.add(box1);
		boxbase.add(Box.createVerticalStrut(10));
		boxbase.add(box_fixed);

		/// 等长拆帧
		Box notfixbox = Box.createVerticalBox();
		notfixbox.setBorder(BorderFactory.createTitledBorder("等长时间段拆帧"));

		Box notfixbox2 = Box.createHorizontalBox();
		Box notfixbox3 = Box.createHorizontalBox();
		Box notfixbox4 = Box.createHorizontalBox();

		JLabel labelnotfixed = new JLabel("间隔时间:");
		notfixbox2.add(labelnotfixed);
		notfixbox2.add(Box.createHorizontalStrut(15));
		JTextField notfixedText = new JTextField(20);
		notfixbox2.add(notfixedText);
		notfixbox2.add(Box.createHorizontalStrut(15));
		JButton jtestBtn = new JButton("单位/ms");
		jtestBtn.setEnabled(false);
		notfixbox2.add(jtestBtn);
		notfixbox2.add(Box.createHorizontalStrut(15));

		JLabel labellocation = new JLabel("保存位置:");
		notfixbox3.add(labellocation);
		notfixbox3.add(Box.createHorizontalStrut(15));

		JTextField notfixedpath = new JTextField(20);
		notfixbox3.add(notfixedpath);
		notfixbox3.add(Box.createHorizontalStrut(15));

		JButton notfixedpathbtn = new JButton("浏览    ");
		notfixbox3.add(notfixedpathbtn);
		notfixbox3.add(Box.createHorizontalStrut(15));

		JButton notFixedOkbtn = new JButton("确定");
		notfixbox4.add(notFixedOkbtn);
		notfixbox4.add(Box.createHorizontalStrut(90));

		JButton notFixedCanbtn = new JButton("清空");
		notfixbox4.add(notFixedCanbtn);
		notfixbox4.add(Box.createHorizontalStrut(15));

		notfixbox.add(notfixbox2);
		notfixbox.add(Box.createVerticalStrut(15));
		notfixbox.add(notfixbox3);
		notfixbox.add(Box.createVerticalStrut(15));
		notfixbox.add(notfixbox4);
		notfixbox.add(Box.createVerticalStrut(15));

		// 单帧图片
		Box Box_this = Box.createVerticalBox();
		Box_this.setBorder(BorderFactory.createTitledBorder("单帧图片"));
		Box Box_this1 = Box.createHorizontalBox();
		Box Box_this2 = Box.createHorizontalBox();
		Box Box_this3 = Box.createHorizontalBox();

		JLabel labelthis = new JLabel("选择时刻:");
		Box_this1.add(labelthis);
		Box_this1.add(Box.createHorizontalStrut(15));
		thisText = new JTextField(20);
		Box_this1.add(thisText);
		Box_this1.add(Box.createHorizontalStrut(15));
		JButton jthisBtn = new JButton("选择/ms");
		Box_this1.add(jthisBtn);
		Box_this1.add(Box.createHorizontalStrut(15));

		JLabel thislocation = new JLabel("保存位置:");
		Box_this3.add(thislocation);
		Box_this3.add(Box.createHorizontalStrut(15));

		JTextField thispath = new JTextField(20);
		Box_this3.add(thispath);
		Box_this3.add(Box.createHorizontalStrut(15));

		JButton thispathBtn = new JButton("浏览   ");
		Box_this3.add(thispathBtn);
		Box_this3.add(Box.createHorizontalStrut(15));

		JButton thisOkbtn = new JButton("确定");
		Box_this2.add(thisOkbtn);
		Box_this2.add(Box.createHorizontalStrut(90));

		JButton thisCanbtn = new JButton("清空");
		Box_this2.add(thisCanbtn);
		Box_this2.add(Box.createHorizontalStrut(15));

		Box_this.add(Box_this1);
		Box_this.add(Box.createVerticalStrut(15));
		Box_this.add(Box_this3);
		Box_this.add(Box.createVerticalStrut(15));
		Box_this.add(Box_this2);
		Box_this.add(Box.createVerticalStrut(15));

		Box_this.setVisible(false);
		notfixbox.setVisible(false);
		box_fixed.setVisible(true);
		boxbase.add(Box_this);
		boxbase.add(notfixbox);
		boxbase.add(Box.createVerticalStrut(10));
		boxbase.add(box_bottom);
		box_bottom.setVisible(false);
		contentPane.add(boxbase);

		// 给选取时间段按钮添加事件
		notFixedButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Box_this.setVisible(false);
				box_fixed.setVisible(true);
				notfixbox.setVisible(false);
			}
		});
		fixedButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Box_this.setVisible(false);
				box_fixed.setVisible(false);
				notfixbox.setVisible(true);
			}
		});
		thisButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Box_this.setVisible(true);
				box_fixed.setVisible(false);
				notfixbox.setVisible(false);
			}
		});

		// 选择开始时间添加点击事件
		jstartBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jmodeFrame.setVisible(false);
				MainWindow.btnstop_choice.setVisible(true);
				MainWindow.btnSave.setVisible(false);
				MainWindow.btnstart_choice.setVisible(false);
				MainWindow.btnStop.setVisible(false);

				// 改变模式为1
				mode = 1;
			}
		});
		// 选择结束时间添加点击事件
		jstopBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jmodeFrame.setVisible(false);
				MainWindow.btnstop_choice.setVisible(true);
				MainWindow.btnSave.setVisible(false);
				MainWindow.btnstart_choice.setVisible(false);
				MainWindow.btnStop.setVisible(false);

				// 改变模式为1
				mode = 2;
			}
		});
		// 选择结束时间添加点击事件
		jthisBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jmodeFrame.setVisible(false);
				MainWindow.btnstop_choice.setVisible(true);
				MainWindow.btnSave.setVisible(false);
				MainWindow.btnstart_choice.setVisible(false);
				MainWindow.btnStop.setVisible(false);

				// 改变模式为1
				mode = 3;
			}
		});
		// 三个清空按钮
		jcancal.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				startText.setText(null);
				stopText.setText(null);
				filepath.setText(null);
			}
		});
		notFixedCanbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				notfixedText.setText(null);
				notfixedpath.setText(null);

			}
		});
		thisCanbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				thisText.setText(null);
				thispath.setText(null);

			}
		});
		// 三个选择路径按键
		jfileBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 获取项目路径
				File directory = new File("");// 参数为空
				String courseFile = "D:";
				try {
					courseFile = directory.getCanonicalPath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JFileChooser chooser = new JFileChooser("F:\\Remote sensing project\\frame\\normal");
				chooser.setFileSelectionMode(1);// 设定只能选择到文件夹
				chooser.setDialogTitle("选择帧要保存的位置：");
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// 获取保存位置
					File dir = new File(
							chooser.getSelectedFile().getAbsolutePath() + "\\" + System.currentTimeMillis());
					if (!dir.exists()) {
						dir.mkdir();
						imgPath = dir.getAbsolutePath();
						filepath.setText(imgPath);
					}
				}

			}
		});
		notfixedpathbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 获取项目路径
				File directory = new File("");// 参数为空
				String courseFile = "D:";
				try {
					courseFile = directory.getCanonicalPath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JFileChooser chooser = new JFileChooser(courseFile);
				chooser.setFileSelectionMode(1);// 设定只能选择到文件夹
				chooser.setDialogTitle("选择帧要保存的位置：");
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// 获取保存位置
					imgPath = chooser.getSelectedFile().getAbsolutePath();
					notfixedpath.setText(imgPath);
				}

			}
		});
		thispathBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 获取项目路径
				File directory = new File("");// 参数为空
				String courseFile = "D:";
				try {
					courseFile = directory.getCanonicalPath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JFileChooser chooser = new JFileChooser(courseFile);
				chooser.setFileSelectionMode(1);// 设定只能选择到文件夹
				chooser.setDialogTitle("选择帧要保存的位置：");
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// 获取保存位置
					imgPath = chooser.getSelectedFile().getAbsolutePath();
					thispath.setText(imgPath);
				}

			}
		});
		// 三个确定按钮
		// 按自定义时间段拆帧的处理事件
		jok.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				box_bottom.setVisible(true);
				long starttime = Long.parseLong(startText.getText());
				long stoptime = Long.parseLong(stopText.getText());
				String savepath = filepath.getText();
				if (stoptime > starttime) {
					if (savepath != "") {

						// 在开启线程
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									// 创建一个 SwingWorker 线程，用于实时调节进度
									// 注意：创建完毕，最后要 execute() 将它运行起来
									new SwingWorker<String, Integer>() {
										@Override
										protected String doInBackground() throws Exception {
											// while (true) {
											// 总长，以毫秒计
											long total = stoptime - starttime;
											// 处理帧
											System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
											VideoCapture capture = new VideoCapture();
											capture.open(VideoPlayer.videoPath);
											System.out.println(VideoPlayer.videoPath);
											Mat image = new Mat();// 定义一个Mat，用来接收一帧的图像
											capture.read(image);

											VideoWriter writer = new VideoWriter();
											// 读取视频帧
											capture.set(0, starttime);
											// 当前所看时长，以毫秒计

											while (capture.read(image)) {
												double time = capture.get(0);
												long curr = (long) time;
												// 百分比，并强转为 float
												float percent = (float) curr / total;
												publish((int) (percent * 100));
												// System.out.println("当前时间："+time);
												if (time >= stoptime) {
													break;
												}

												Imgcodecs.imwrite(savepath + "/" + time + ".jpg", image);

											}
											box_bottom.setVisible(false);
											return "ok";
										}

										protected void process(java.util.List<Integer> chunks) {
											// 创建int型变量 value 接收 chunks 中的值
											for (int value : chunks) {
												progressBar.setValue(value);
											}
										};
									}.execute();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					} else {
						JOptionPane.showMessageDialog(null, "请输入保存位置！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "开始时间必须大于结束时间！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// 按固定时长拆帧的处理事件
		notFixedOkbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				box_bottom.setVisible(true);
				// 处理帧
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				VideoCapture capture = new VideoCapture();
				capture.open(VideoPlayer.videoPath);
				long starttime = 0;
				long stoptime = (long) capture.get(7);
				String savepath = notfixedpath.getText();
				long splittime = Long.parseLong(notfixedText.getText());
				// 在开启线程
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// 创建一个 SwingWorker 线程，用于实时调节进度
							// 注意：创建完毕，最后要 execute() 将它运行起来
							new SwingWorker<String, Integer>() {
								@Override
								protected String doInBackground() throws Exception {
									// 总长，以毫秒计
									long total = stoptime - starttime;
									Mat image = new Mat();// 定义一个Mat，用来接收一帧的图像
									capture.read(image);

									VideoWriter writer = new VideoWriter();
									// 读取视频帧
									capture.set(0, starttime);
									// 当前所看时长，以毫秒计
									long time_flag = 0;
									long flag = 0;
									String savepath2 = "";
									while (capture.read(image)) {
										double time = capture.get(0);
										long curr = (long) capture.get(1);
										// 百分比，并强转为 float
										float percent = (float) curr / total;
										publish((int) (percent * 100));
										// System.out.println("当前时间："+time);

										if (time % splittime == 0) {
											savepath2 = savepath + "\\" + flag;
											File file = new File(savepath2);
											if (!file.exists()) {
												file.mkdirs();
											}
											flag++;
										}
										Imgcodecs.imwrite(savepath2 + "/" + time + ".jpg", image);

									}
									box_bottom.setVisible(false);
									return "ok";
								}

								protected void process(java.util.List<Integer> chunks) {
									// 创建int型变量 value 接收 chunks 中的值
									for (int value : chunks) {
										progressBar.setValue(value);
									}
								};
							}.execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		// 提取单帧图像的处理事件
		thisOkbtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 处理帧
				box_bottom.setVisible(true);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							// 创建一个 SwingWorker 线程，用于实时调节进度
							// 注意：创建完毕，最后要 execute() 将它运行起来
							new SwingWorker<String, Integer>() {
								@Override
								protected String doInBackground() throws Exception {
									System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
									VideoCapture capture = new VideoCapture();
									capture.open(VideoPlayer.videoPath);
									// 读取视频帧
									capture.set(0, Long.parseLong(thisText.getText()));
									Mat src = new Mat();
									capture.read(src);
									Imgcodecs.imwrite(thispath.getText() + "/" + thisText.getText() + ".jpg", src);
									box_bottom.setVisible(false);
									publish(100);
									return "ok";
								}

								protected void process(java.util.List<Integer> chunks) {
									// 创建int型变量 value 接收 chunks 中的值
									for (int value : chunks) {
										progressBar.setValue(value);
									}
								};
							}.execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
	}
}
