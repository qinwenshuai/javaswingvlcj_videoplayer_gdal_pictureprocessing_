package com.map.video;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FileMutiu2 extends JFrame {

	private JPanel contentPane;
	private Object[] field = { "原数据路径", "数据信息" };
	 //private Object [][]data = {{"qinddddddddddddddddddddddddd", "wen1111111111111111111111111111111111111"}};
	private Object[][] data;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String str) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileMutiu2 frame = new FileMutiu2(str);
					frame.setVisible(true);
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileMutiu2(String str) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 300, 602, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(300);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerLocation(450);
		splitPane.setLeftComponent(splitPane_1);

		DefaultTableModel mod = new DefaultTableModel(data, field);
		DefaultTableModel mod1 = new DefaultTableModel(data, field);

		JPanel panel = new JPanel();
		splitPane_1.setRightComponent(panel);

		JButton btnNewButton = new JButton("添加数据");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser("F:\\Remote sensing project\\images");
				jfc.setMultiSelectionEnabled(true);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showOpenDialog(contentPane);
				File[] files = jfc.getSelectedFiles();
				// if(data!=null)
				// data = new productData().data(files, data);
				// else
				// data = new productData().data(files);
				Vector<String> vv;
				for (File file : files) {
					vv = new productData().getVec(file);
					tableModel.addRow(vv);
					System.out.println(tableModel.getRowCount());
				}
				// System.out.println(data);
				// table.setModel(new DefaultTableModel(data, field));
				table.getColumnModel().getColumn(0).setMinWidth(300);
				// System.out.println(new productData().getData(table).length);
				System.out.println(tableModel.getRowCount());

			}
		});
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("删除数据");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectRaws = table.getSelectedRows();
				if (selectRaws.length < 1) {
					JOptionPane.showMessageDialog(contentPane, "请至少选择一项", "提示", JOptionPane.NO_OPTION);
				} else {
					for (int i = selectRaws.length; i > 0; i--) {
						tableModel.removeRow(table.getSelectedRow());
					}
				}
				System.out.println(tableModel.getRowCount());
			}
		});
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("输出目录");
		panel.add(btnNewButton_2);

		JScrollPane scrollPane = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPane);

		table = new JTable();
		table.setShowHorizontalLines(false);
		// table.setRowHeight(100);
		tableModel = new DefaultTableModel(data, field);
		table.setModel(tableModel);
		table.getTableHeader().setForeground(Color.RED);
		System.out.println(table.getColumnModel().getColumn(1).getHeaderValue());
		table.getColumnModel().getColumn(0).setMinWidth(300);
		scrollPane.setViewportView(table);

		JButton button = new JButton("开始融合图像");
		splitPane.setRightComponent(button);
		System.out.println(data);
		System.out.println(new productData().getData(table).length);
		this.setTitle(str);
		this.setVisible(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
	}

}

class productData {

	public Object[][] data(File[] files, Object[][] data) {

		Object[][] test = new Object[files.length][2];
		for (int i = 0; i < files.length; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0)
					test[i][j] = (Object) files[i].getAbsolutePath();
				else
					test[i][j] = (Object) files[i].getName();
			}
		}

		Object[][] c = new Object[test.length + data.length][2];
		int i = 0;
		for (int j = 0; j < test.length; j++) {
			c[i++] = test[j];
		}
		for (int j = 0; j < data.length; j++) {
			c[i++] = data[j];
		}
		return c;
	}

	public Object[][] data(File[] files) {
		// TODO Auto-generated method stub
		Object[][] test = new Object[files.length][2];
		for (int i = 0; i < files.length; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0)
					test[i][j] = (Object) files[i].getAbsolutePath();
				else
					test[i][j] = (Object) files[i].getName();
			}
		}
		return test;
	}

	public String[] getData(JTable table) {
		String str[] = new String[table.getRowCount()];
		for (int i = 0; i < str.length; i++) {
			str[i] = table.getValueAt(i, 1).toString();
		}
		return str;
	}

	public Vector<String> getVec(File file) {
		Vector<String> v = new Vector<String>();
		v.addElement(file.getAbsolutePath());
		v.addElement(file.getName());
		return v;
	}

}

class MyTableModel extends AbstractTableModel {

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
