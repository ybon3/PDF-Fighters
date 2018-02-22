package com.jpedal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jpedal.examples.images.ConvertPagesToHiResImages;
import org.jpedal.examples.images.ConvertPagesToImages;
import org.jpedal.exception.PdfException;

/**
 * http://www.ghost4j.org/releasenotes.html
 */
public class Test {
	static String src = "D:/test/pdfTest/test3.pdf";
	static String dstDir = "D:/test/pdfTest/";

	public static void main(String[] args) throws PdfException, IOException {
		long s = System.currentTimeMillis();
//		File srcFolder = new File("D:\\TEMP\\pdfOutput");
//		for (File f : srcFolder.listFiles()) {
//			if (!f.isFile()) { continue; }
//
//			convert2(f, srcFolder);
//		}
		convert(new File(src), new File(dstDir));
		System.out.println(System.currentTimeMillis() - s);
	}

	public static void convert(File src, File dest) throws PdfException, IOException {
		ConvertPagesToImages converter = new ConvertPagesToImages(src.getAbsolutePath());
		if (converter.openPDFFile()) {
			converter.setPageScaling(5.0f);
			int pageCount = converter.getPageCount();
			String pattern = src.getName();
			pattern = pattern.substring(0, pattern.lastIndexOf("."));
			for (int page = 1; page <= pageCount; page++) {
				BufferedImage image = converter.getPageAsImage(page, false);
				ImageIO.write(
					image,
					"jpg",
					new File(dest, pattern + "-jpedal-" + page + ".jpg"));
			}
		}
		converter.closePDFfile();
	}

	/**
	 * 很扯的 HiRes ... 不要用吧
	 */
	public static void convert2(File src, File dest) throws PdfException, IOException {
		System.out.println("processing: " + src.getName());
		ConvertPagesToHiResImages converter = new ConvertPagesToHiResImages(src.getAbsolutePath());
		if (converter.openPDFFile()) {
			int pageCount = converter.getPageCount();
			HashMap options = new HashMap(); //see https://javadoc.idrsolutions.com/org/jpedal/constants/JPedalSettings.html
			String pattern = src.getName();
			pattern = pattern.substring(0, pattern.lastIndexOf("."));
			for (int page = 1; page <= pageCount; page++) {
				System.out.println("page: " + page);
				BufferedImage image = converter.getPageAsHiResImage(page, false, options);
				ImageIO.write(
					image,
					"jpg",
					new File(dest, pattern + "-jpedal-" + page + ".jpg"));
			}
		}
		converter.closePDFfile();
	}

}
