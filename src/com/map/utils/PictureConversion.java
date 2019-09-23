package com.map.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.PNMEncodeParam;
import com.sun.media.jai.codec.TIFFEncodeParam;

public class PictureConversion {
	static public String path;
	final JFrame jf = new JFrame("测试窗口");
	public String sourcePath = "D://images//";
	static File fileFlag = new File("");
	private int saveFlag;
	public String tiffTurnPng(String filePath) {

		  RenderedOp src = JAI.create("fileload", filePath);
		  File tempFile = new File(filePath.trim());
		  String fileName = tempFile.getName();
		  String name = fileName.substring(0, fileName.lastIndexOf("."));
		  path = sourcePath + name + ".png";

		  JFrame frame = new JFrame();
		  JFileChooser jf = new JFileChooser();

		  fileFlag = new File(path);
		  jf.setDialogTitle("保存");
		  jf.setCurrentDirectory(fileFlag);// 设置打开对话框的默认路径
		  jf.setSelectedFile(fileFlag);// 设置选中转换后的文件
		  saveFlag = jf.showSaveDialog(frame);// 显示打开的文件对话框

		  String savePath = jf.getSelectedFile().toString();// 保存时选择的路径
		  if (saveFlag == JFileChooser.APPROVE_OPTION) {
		   try {
		    OutputStream os = new FileOutputStream(savePath + name + ".pnm");
		    PNMEncodeParam param2 = new PNMEncodeParam();
		    ImageEncoder encoder = ImageCodec.createImageEncoder("PNM", os, param2);
		    encoder.encode(src);
		    File inputFile = new File(savePath + name + ".pnm");
		    BufferedImage input = ImageIO.read(inputFile);
		    File outputFile = new File(savePath + name + ".png");
		    path = savePath + name + ".png";
		    ImageIO.write(input, "PNG", outputFile);
		    JOptionPane.showMessageDialog(jf, "转换成功", "消息", JOptionPane.INFORMATION_MESSAGE);
		    os.close();
		    inputFile.delete();
		   } catch (FileNotFoundException e) {
		    e.printStackTrace();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }  
		  }
		  return path;
		 }
	

	public String tiffTurnJpg(String filePath) {
		String destFile = "";
		RenderedOp src = JAI.create("fileload", filePath);
		File tempFile = new File(filePath.trim());
		String fileName = tempFile.getName();
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		path = sourcePath + name + ".jpg";
		try {
			OutputStream os = new FileOutputStream(sourcePath + name + ".jpg");
			JPEGEncodeParam param2 = new JPEGEncodeParam();   
			ImageEncoder encoder = ImageCodec.createImageEncoder("JPEG", os, param2);
			encoder.encode(src);
			JOptionPane.showMessageDialog(jf,
                    "转换成功",
                    "消息",
                    JOptionPane.INFORMATION_MESSAGE
            );
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}


	public String tiffTurnBmp(String filePath) {
		String destFile = "";
		RenderedOp src = JAI.create("fileload", filePath);
		File tempFile = new File(filePath.trim());
		String fileName = tempFile.getName();
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		path = sourcePath + name + ".bmp";
		try {
			OutputStream os = new FileOutputStream(sourcePath + name + ".bmp");
			BMPEncodeParam param = new BMPEncodeParam();
			ImageEncoder encoder = ImageCodec.createImageEncoder("BMP", os, param);
			encoder.encode(src);
			JOptionPane.showMessageDialog(jf,
                    "转换成功",
                    "消息",
                    JOptionPane.INFORMATION_MESSAGE
            );
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}


	public String tiffTurnGif(String filePath) {
		String destFile = "";
		RenderedOp src = JAI.create("fileload", filePath);
		File tempFile = new File(filePath.trim());
		String fileName = tempFile.getName();
		BufferedImage bufferedImage;
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		path = sourcePath + name + ".gif";
		try {
			OutputStream os = new FileOutputStream(sourcePath + name + ".pnm");
			PNMEncodeParam param2 = new PNMEncodeParam();   
			ImageEncoder encoder = ImageCodec.createImageEncoder("PNM", os, param2);
			encoder.encode(src);
			File inputFile = new File(sourcePath + name + ".pnm");
			bufferedImage = ImageIO.read(inputFile);
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			File outputFile = new File(sourcePath + name + ".gif");
	        ImageIO.write(newBufferedImage, "GIF", outputFile);
	        JOptionPane.showMessageDialog(jf,
                    "转换成功",
                    "消息",
                    JOptionPane.INFORMATION_MESSAGE
            );
	        os.close();
	        inputFile.delete();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	

	public String jpgToTif(String filePath) {
		String destFile = "";
		RenderedOp src = JAI.create("fileload", filePath);
		File tempFile = new File(filePath.trim());
		String fileName = tempFile.getName();
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		path = sourcePath + name + ".tif";
		try {
			OutputStream os = new FileOutputStream(sourcePath + name + ".tif");
			TIFFEncodeParam param = new TIFFEncodeParam();
			ImageEncoder encoder = ImageCodec.createImageEncoder("TIFF", os, param);
			encoder.encode(src);
			JOptionPane.showMessageDialog(jf,
                    "转换成功",
                    "消息",
                    JOptionPane.INFORMATION_MESSAGE
            );
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
}