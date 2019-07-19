package com.map.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

public class ReadTiff{
	private String path;

	public String Tiftxt()
	{try {path = "D:\\images\\text\\output.txt";
		System.setOut(new PrintStream("D:\\images\\text\\output.txt"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	gdal.AllRegister();
	String rasterFilePath = "D:\\images\\open\\dili.TIF";
	Dataset dataset = gdal.Open(rasterFilePath,gdalconstConstants.GA_ReadOnly);
	if(dataset==null)
	{
		System.out.println("GDAL read error: " + gdal.GetLastErrorMsg());
	}

	Driver driver = dataset.GetDriver();System.out.println("driver short name: "+driver.getShortName());System.out.println("driver long name: "+driver.getLongName());
	// System.out.println("metadata list: " + driver.GetMetadata_List());//shuju
	// jibenxinxi
	int xsize = dataset.getRasterXSize();
	int ysize = dataset.getRasterYSize();
	int count = dataset.getRasterCount();
	String proj = dataset.GetProjection();// touyingxinxi
	Band band = dataset.GetRasterBand(1);// boduan zhizhen weizhi
	//System.out.println(band.GetXSize());// boduan chicun
	System.out.println("Size is "+xsize+", "+ysize);System.out.println("波段数:"+count);
	// System.out.println(proj);
	// 左上角点坐标 lon lat: transform[0]、transform[3]
	// 像素分辨率 x、y方向 : transform[1]、transform[5]
	// 旋转角度: transform[2]、transform[4])
	double[] transform = dataset.GetGeoTransform();
		System.out.println("lon: " + transform[0]);
		System.out.println("lat: " + transform[3]);
		System.out.println("像素分辨率x方向: " + transform[1]);
		System.out.println("像素分辨率y方向: " + transform[5]);
		System.out.println("旋转角度: " + transform[2]);
		System.out.println("旋转角度: " + transform[4]);
		
	return path;
}}