package com.map.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class PictureCut extends JFrame implements ActionListener {
	private JButton start, cancel;
	private JPanel c;
	private BufferedImage get;
	private JTabbedPane jtp;// 一个放置很多份图片
	private int index;// 一个一直会递增的索引,用于标认图片
	private JRadioButton java, system;// JAVA界面,系统界面

	/** Creates a new instance of CaptureScreen */

	public PictureCut() {
		super("屏幕截取");
		initWindow();
		initOther();
		// initTrayIcon();
	}

	private void initOther() {
		jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private void initWindow() {
		JPanel buttonJP = new JPanel();
		c = new JPanel(new BorderLayout());
		c.setBackground(Color.WHITE);
		JPanel all = new JPanel();
		all.add(buttonJP);
		this.getContentPane().add(c, BorderLayout.CENTER);
		this.getContentPane().add(all, BorderLayout.SOUTH);
		this.setTitle("遥感图像超分辨率重建软件系统");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				PictureCut.this.setVisible(false);
			}
		});
	}

	/*
	 * private void initTrayIcon() { try { SystemTray st =
	 * SystemTray.getSystemTray(); Image im =
	 * ImageIO.read(this.getClass().getResource("bg.gif")); PopupMenu pm = new
	 * PopupMenu("弹出菜单"); pm.addSeparator(); pm.add(new
	 * MenuItem("退出程序")).addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent ae) { PictureCut.this.setVisible(false); } });
	 * TrayIcon ti = new TrayIcon(im, "JAVA屏幕截取"); st.add(ti);
	 * ti.addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent ae) { PictureCut.this.setVisible(true); } }); }
	 * catch (Exception exe) { exe.printStackTrace(); } }
	 */
	private void updates() {
		this.setVisible(true);
		if (get != null) {
			if (index == 0) {
				c.removeAll();
				c.add(jtp, BorderLayout.CENTER);
			} else {
			}
			PicPanel pic = new PicPanel(get);
			jtp.addTab("图片" + (++index), pic);
			jtp.setSelectedComponent(pic);
			SwingUtilities.updateComponentTreeUI(c);
		}
	}

	public void doStart() {
		try {
			UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
			this.setVisible(false);
			Thread.sleep(500);// 睡500毫秒是为了让主窗完全不见
			Robot ro = new Robot();
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension di = tk.getScreenSize();
			Rectangle rec = new Rectangle(0, 0, di.width, di.height);
			BufferedImage bi = ro.createScreenCapture(rec);
			JFrame jf = new JFrame();
			Temp temp = new Temp(jf, bi, di.width, di.height);
			jf.getContentPane().add(temp, BorderLayout.CENTER);
			// jf.setLocationRelativeTo(null);
			jf.setUndecorated(true);
			jf.setSize(di);
			jf.setVisible(true);
			jf.setAlwaysOnTop(true);

		} catch (Exception exe) {

			exe.printStackTrace();

		}

	}

	public void doSave(BufferedImage get) {

		try {

			if (get == null) {

				JOptionPane.showMessageDialog(this, "图片不能为空!!", "错误", JOptionPane.ERROR_MESSAGE);

				return;

			}

			JFileChooser jfc = new JFileChooser("D:\\images\\cut");

			jfc.addChoosableFileFilter(new GIFfilter());

			jfc.addChoosableFileFilter(new BMPfilter());

			jfc.addChoosableFileFilter(new JPGfilter());

			jfc.addChoosableFileFilter(new PNGfilter());

			int i = jfc.showSaveDialog(this);

			if (i == JFileChooser.APPROVE_OPTION) {

				File file = jfc.getSelectedFile();

				String about = "PNG";

				String ext = file.toString().toLowerCase();

				javax.swing.filechooser.FileFilter ff = jfc.getFileFilter();

				if (ff instanceof JPGfilter) {

					about = "JPG";

					if (!ext.endsWith(".jpg")) {

						String ns = ext + ".jpg";

						file = new File(ns);

					}

				} else if (ff instanceof PNGfilter) {

					about = "PNG";

					if (!ext.endsWith(".png")) {

						String ns = ext + ".png";

						file = new File(ns);

					}

				} else if (ff instanceof BMPfilter) {

					about = "BMP";

					if (!ext.endsWith(".bmp")) {

						String ns = ext + ".bmp";

						file = new File(ns);

					}

				} else if (ff instanceof GIFfilter) {

					about = "GIF";

					if (!ext.endsWith(".gif")) {

						String ns = ext + ".gif";

						file = new File(ns);

					}

				}

				if (ImageIO.write(get, about, file)) {

					JOptionPane.showMessageDialog(this, "保存成功！");

				} else

					JOptionPane.showMessageDialog(this, "保存失败！");

			}

		} catch (Exception exe) {

			exe.printStackTrace();

		}

	}

	private void doClose(Component c) {

		jtp.remove(c);

		c = null;

		System.gc();

	}

	public void actionPerformed(ActionEvent ae) {

		Object source = ae.getSource();

		if (source == start) {

			doStart();

		} else if (source == cancel) {

			PictureCut.this.dispose();

		}
	}

	private class PicPanel extends JPanel implements ActionListener {

		JButton save, copy, close;// 表示保存,复制,关闭的按钮

		BufferedImage get;// 得到的图片

		public PicPanel(BufferedImage get) {

			super(new BorderLayout());

			this.get = get;

			initPanel();

		}

		public BufferedImage getImage() {

			return get;

		}

		private void initPanel() {

			save = new JButton("保存(S)");

			// copy = new JButton("复制到剪帖板(C)");

			close = new JButton("关闭(X)");

			save.setMnemonic('S');

			close.setMnemonic('X');

			JPanel buttonPanel = new JPanel();

			buttonPanel.add(save);

			buttonPanel.add(close);

			JLabel icon = new JLabel(new ImageIcon(get));

			this.add(new JScrollPane(icon), BorderLayout.CENTER);

			this.add(buttonPanel, BorderLayout.SOUTH);

			save.addActionListener(this);

			// copy.addActionListener(this);

			close.addActionListener(this);

		}

		public void actionPerformed(ActionEvent e) {

			Object source = e.getSource();

			if (source == save) {

				doSave(get);

			} else if (source == close) {

				get = null;

				doClose(this);

			}

		}

	}

	// 保存BMP格式的过滤器

	private class BMPfilter extends javax.swing.filechooser.FileFilter {

		public BMPfilter() {

		}

		public boolean accept(File file) {

			if (file.toString().toLowerCase().endsWith(".bmp") ||

					file.isDirectory()) {

				return true;

			} else

				return false;

		}

		public String getDescription() {

			return "*.BMP(BMP图像)";

		}

	}

	// 保存JPG格式的过滤器

	private class JPGfilter extends javax.swing.filechooser.FileFilter {

		public JPGfilter() {

		}

		public boolean accept(File file) {

			if (file.toString().toLowerCase().endsWith(".jpg") ||

					file.isDirectory()) {

				return true;

			} else

				return false;

		}

		public String getDescription() {

			return "*.JPG(JPG图像)";

		}

	}

	// 保存GIF格式的过滤器

	private class GIFfilter extends javax.swing.filechooser.FileFilter {

		public GIFfilter() {

		}

		public boolean accept(File file) {

			if (file.toString().toLowerCase().endsWith(".gif") ||

					file.isDirectory()) {

				return true;

			} else

				return false;

		}

		public String getDescription() {

			return "*.GIF(GIF图像)";

		}

	}

	// 保存PNG格式的过滤器

	private class PNGfilter extends javax.swing.filechooser.FileFilter {

		public boolean accept(File file) {

			if (file.toString().toLowerCase().endsWith(".png") ||

					file.isDirectory()) {

				return true;

			} else

				return false;

		}

		public String getDescription() {

			return "*.PNG(PNG图像)";

		}

	}

	// 一个暂时类，用于显示当前的屏幕图像

	private class Temp extends JPanel implements MouseListener, MouseMotionListener {

		private BufferedImage bi;

		private int width, height;

		private int startX, startY, endX, endY, tempX, tempY;

		private JFrame jf;

		private Rectangle select = new Rectangle(0, 0, 0, 0);// 表示选中的区域

		private Cursor cs = new Cursor(Cursor.CROSSHAIR_CURSOR);// 表示一般情况下的鼠标状态

		private States current = States.DEFAULT;// 表示当前的编辑状态

		private Rectangle[] rec;// 表示八个编辑点的区域

		// 下面四个常量,分别表示谁是被选中的那条线上的端点

		public static final int START_X = 1;

		public static final int START_Y = 2;

		public static final int END_X = 3;

		public static final int END_Y = 4;

		private int currentX, currentY;// 当前被选中的X和Y,只有这两个需要改变

		private Point p = new Point();// 当前鼠标移的地点

		private boolean showTip = true;// 是否显示提示.如果鼠标左键一按,则提示不再显了

		public Temp(JFrame jf, BufferedImage bi, int width, int height) {

			this.jf = jf;

			this.bi = bi;

			this.width = width;

			this.height = height;

			this.addMouseListener(this);

			this.addMouseMotionListener(this);

			initRecs();

		}

		private void initRecs() {

			rec = new Rectangle[8];

			for (int i = 0; i < rec.length; i++) {

				rec[i] = new Rectangle();

			}

		}

		public void paintComponent(Graphics g) {

			g.drawImage(bi, 0, 0, width, height, this);

			g.setColor(Color.RED);

			g.drawLine(startX, startY, endX, startY);

			g.drawLine(startX, endY, endX, endY);

			g.drawLine(startX, startY, startX, endY);

			g.drawLine(endX, startY, endX, endY);

			int x = startX < endX ? startX : endX;

			int y = startY < endY ? startY : endY;

			select = new Rectangle(x, y, Math.abs(endX - startX), Math.abs(endY - startY));

			int x1 = (startX + endX) / 2;

			int y1 = (startY + endY) / 2;

			g.fillRect(x1 - 2, startY - 2, 5, 5);

			g.fillRect(x1 - 2, endY - 2, 5, 5);

			g.fillRect(startX - 2, y1 - 2, 5, 5);

			g.fillRect(endX - 2, y1 - 2, 5, 5);

			g.fillRect(startX - 2, startY - 2, 5, 5);

			g.fillRect(startX - 2, endY - 2, 5, 5);

			g.fillRect(endX - 2, startY - 2, 5, 5);

			g.fillRect(endX - 2, endY - 2, 5, 5);

			rec[0] = new Rectangle(x - 5, y - 5, 10, 10);

			rec[1] = new Rectangle(x1 - 5, y - 5, 10, 10);

			rec[2] = new Rectangle((startX > endX ? startX : endX) - 5, y - 5, 10, 10);

			rec[3] = new Rectangle((startX > endX ? startX : endX) - 5, y1 - 5, 10, 10);

			rec[4] = new Rectangle((startX > endX ? startX : endX) - 5, (startY > endY ? startY : endY) - 5, 10, 10);

			rec[5] = new Rectangle(x1 - 5, (startY > endY ? startY : endY) - 5, 10, 10);

			rec[6] = new Rectangle(x - 5, (startY > endY ? startY : endY) - 5, 10, 10);

			rec[7] = new Rectangle(x - 5, y1 - 5, 10, 10);

			if (showTip) {

				g.setColor(Color.CYAN);

				g.fillRect(p.x, p.y, 170, 20);

				g.setColor(Color.RED);

				g.drawRect(p.x, p.y, 170, 20);

				g.setColor(Color.BLACK);

				g.drawString("请按住鼠标左键不放选择截图区", p.x, p.y + 15);

			}

		}

		// 根据东南西北等八个方向决定选中的要修改的X和Y的座标

		private void initSelect(States state) {

			switch (state) {

			case DEFAULT:

				currentX = 0;

				currentY = 0;

				break;

			case EAST:

				currentX = (endX > startX ? END_X : START_X);

				currentY = 0;

				break;

			case WEST:

				currentX = (endX > startX ? START_X : END_X);

				currentY = 0;

				break;

			case NORTH:

				currentX = 0;

				currentY = (startY > endY ? END_Y : START_Y);

				break;

			case SOUTH:

				currentX = 0;

				currentY = (startY > endY ? START_Y : END_Y);

				break;

			case NORTH_EAST:

				currentY = (startY > endY ? END_Y : START_Y);

				currentX = (endX > startX ? END_X : START_X);

				break;

			case NORTH_WEST:

				currentY = (startY > endY ? END_Y : START_Y);

				currentX = (endX > startX ? START_X : END_X);

				break;

			case SOUTH_EAST:

				currentY = (startY > endY ? START_Y : END_Y);

				currentX = (endX > startX ? END_X : START_X);

				break;

			case SOUTH_WEST:

				currentY = (startY > endY ? START_Y : END_Y);

				currentX = (endX > startX ? START_X : END_X);

				break;

			default:

				currentX = 0;

				currentY = 0;

				break;

			}

		}

		public void mouseMoved(MouseEvent me) {

			doMouseMoved(me);

			initSelect(current);

			if (showTip) {

				p = me.getPoint();

				repaint();

			}

		}

		// 特意定义一个方法处理鼠标移动,是为了每次都能初始化一下所要选择的地区

		private void doMouseMoved(MouseEvent me) {

			if (select.contains(me.getPoint())) {

				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));

				current = States.MOVE;

			} else {

				States[] st = States.values();

				for (int i = 0; i < rec.length; i++) {

					if (rec[i].contains(me.getPoint())) {

						current = st[i];

						this.setCursor(st[i].getCursor());

						return;

					}

				}

				this.setCursor(cs);

				current = States.DEFAULT;

			}

		}

		public void mouseExited(MouseEvent me) {

		}

		public void mouseEntered(MouseEvent me) {

		}

		public void mouseDragged(MouseEvent me) {

			int x = me.getX();

			int y = me.getY();

			if (current == States.MOVE) {

				startX += (x - tempX);

				startY += (y - tempY);

				endX += (x - tempX);

				endY += (y - tempY);

				tempX = x;

				tempY = y;

			} else if (current == States.EAST || current == States.WEST) {

				if (currentX == START_X) {

					startX += (x - tempX);

					tempX = x;

				} else {

					endX += (x - tempX);

					tempX = x;

				}

			} else if (current == States.NORTH || current == States.SOUTH) {

				if (currentY == START_Y) {

					startY += (y - tempY);

					tempY = y;

				} else {

					endY += (y - tempY);

					tempY = y;

				}

			} else if (current == States.NORTH_EAST || current == States.NORTH_EAST ||

					current == States.SOUTH_EAST || current == States.SOUTH_WEST) {

				if (currentY == START_Y) {

					startY += (y - tempY);

					tempY = y;

				} else {

					endY += (y - tempY);

					tempY = y;

				}

				if (currentX == START_X) {

					startX += (x - tempX);

					tempX = x;

				} else {

					endX += (x - tempX);

					tempX = x;

				}

			} else {

				startX = tempX;

				startY = tempY;

				endX = me.getX();

				endY = me.getY();

			}

			this.repaint();

		}

		public void mousePressed(MouseEvent me) {

			showTip = false;

			tempX = me.getX();

			tempY = me.getY();

		}

		public void mouseReleased(MouseEvent me) {

			if (me.isPopupTrigger()) {

				if (current == States.MOVE) {

					showTip = true;

					p = me.getPoint();

					startX = 0;

					startY = 0;

					endX = 0;

					endY = 0;

					repaint();

				} else {

					jf.dispose();

					updates();

				}

			}

		}

		public void mouseClicked(MouseEvent me) {

			if (me.getClickCount() == 2) {

				// Rectangle rec=new
				// Rectangle(startX,startY,Math.abs(endX-startX),Math.abs(endY-startY));

				Point p = me.getPoint();

				if (select.contains(p)) {

					if (select.x + select.width < this.getWidth() && select.y + select.height < this.getHeight()) {

						get = bi.getSubimage(select.x, select.y, select.width, select.height);

						jf.dispose();

						updates();

					} else {

						int wid = select.width, het = select.height;

						if (select.x + select.width >= this.getWidth()) {

							wid = this.getWidth() - select.x;

						}

						if (select.y + select.height >= this.getHeight()) {

							het = this.getHeight() - select.y;

						}

						get = bi.getSubimage(select.x, select.y, wid, het);

						jf.dispose();

						updates();

					}

				}

			}

		}

	}

	public static void main(String args[]) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new PictureCut();

			}

		});

	}

}

// 一些表示状态的枚举

enum States {

	NORTH_WEST(new Cursor(Cursor.NW_RESIZE_CURSOR)), // 表示西北角

	NORTH(new Cursor(Cursor.N_RESIZE_CURSOR)),

	NORTH_EAST(new Cursor(Cursor.NE_RESIZE_CURSOR)),

	EAST(new Cursor(Cursor.E_RESIZE_CURSOR)),

	SOUTH_EAST(new Cursor(Cursor.SE_RESIZE_CURSOR)),

	SOUTH(new Cursor(Cursor.S_RESIZE_CURSOR)),

	SOUTH_WEST(new Cursor(Cursor.SW_RESIZE_CURSOR)),

	WEST(new Cursor(Cursor.W_RESIZE_CURSOR)),

	MOVE(new Cursor(Cursor.MOVE_CURSOR)),

	DEFAULT(new Cursor(Cursor.DEFAULT_CURSOR));

	private Cursor cs;

	States(Cursor cs) {

		this.cs = cs;

	}

	public Cursor getCursor() {

		return cs;

	}
}
