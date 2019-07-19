package com.map.utils;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.map.ui.InitUI;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation {
	private static String filepath = "";
	private String turnPath = "";
	static File fileFlag = new File("");
	private int saveflag;

	public String openFile(String str) {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		JFileChooser jf = new JFileChooser(str);
		jf.showOpenDialog(frame);// 显示打开的文件对话框
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		File selectFile = jf.getSelectedFile();// 使用文件类获取选择器选择的文件
		filepath = selectFile.getAbsolutePath();// 打开文件的路径
		return filepath;
	}
	
	public String openFile() {
		JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		frame.setAlwaysOnTop(true);
		JFileChooser jf = new JFileChooser();
		jf.showOpenDialog(frame);// 显示打开的文件对话框
		File selectFile = jf.getSelectedFile();// 使用文件类获取选择器选择的文件
		filepath = selectFile.getAbsolutePath();// 打开文件的路径
		return filepath;
	}

	public void saveFile() throws IOException {
		InitUI initUI = new InitUI();

		JFrame frame = new JFrame();
		JFileChooser jf = new JFileChooser();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
		jf.setDialogTitle("另存");
		PictureConversion conversion = new PictureConversion();

		if (initUI.flag == 1) {
			turnPath = conversion.path;
		} else {
			turnPath = filepath;
		}
		
		fileFlag = new File(turnPath);
		jf.setCurrentDirectory(fileFlag);// 设置打开对话框的默认路径
		jf.setSelectedFile(fileFlag);// 设置选中原来的文件
		saveflag = jf.showSaveDialog(frame);
		String resavePath = jf.getSelectedFile().toString();// 另存时选择的路径
		if (resavePath.indexOf(".") != -1) {
			resavePath = resavePath.substring(0, resavePath.indexOf("."));
		}
		// 以下两句是获得原文件的扩展名
		int flag = turnPath.lastIndexOf(".");
		String kuozhan = turnPath.substring(flag);
		if(saveflag == JFileChooser.APPROVE_OPTION){
		// 这里改用了文件流
		FileInputStream input;
		try {
			input = new FileInputStream(turnPath);
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
	}}
}