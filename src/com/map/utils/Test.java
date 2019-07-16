package com.map.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
public class Test {
	public static void fileChooser() {
		Locale.setDefault(Locale.ENGLISH);//设置语言
		try{
		org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();} 
		catch(Exception e){}
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
		chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showSaveDialog(new JPanel());
	    if (returnVal == JFileChooser.APPROVE_OPTION)   {
	    	System.out.println("你打开的文件夹是: " + chooser.getSelectedFile().getPath());
	    	String path = chooser.getSelectedFile().getPath();
	    	System.out.println("path:"+path);
	    	try {
	    		File f = new File(path + "\\" + ".txt");
	    		System.out.println(f.getAbsolutePath());
	    		f.createNewFile();
	    		FileOutputStream out = new FileOutputStream(f);
	    		out.write("测试样例".getBytes());
	    		out.close();
	    	} 
	    	catch (Exception e) {e.printStackTrace();}
		}}
	public static void main(String[] args) {
		fileChooser();}}
