package com.icepdf;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import javax.imageio.ImageIO;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

public class PDFtojpg {
//	static String src = "D:\\TEMP\\pdfOutput\\dicom\\reportViewer2.pdf";
//	static String dstDir = "D:\\TEMP\\pdfOutput\\dicom";
	static String src = "D:/test/pdfTest/test3.pdf";
	static String dstDir = "D:/test/pdfTest/";

	public static void main(String[] args) throws InterruptedException {
		long s = System.currentTimeMillis();
		System.out.println("PDFtojpg Start ...");

//		File srcFolder = new File("D:\\TEMP\\pdfOutput");
//		for (File f : srcFolder.listFiles()) {
//			if (!f.isFile()) { continue; }
//
//			convert(f, srcFolder, "jpg");
//		}

		convert(new File(src), new File(dstDir), "jpg");
		System.out.println(System.currentTimeMillis() - s);
	}

	public static boolean convert(File srcFile, File outputFolder, String format) {
		Document document = new Document();

		try {
			document.setFile(srcFile.getAbsolutePath());
			float scale = 5.0f;
			float rotation = 0f;

			String fnamePrefix = srcFile.getName();
			fnamePrefix = fnamePrefix.substring(0, fnamePrefix.lastIndexOf("."));

			for (int i = 0; i < document.getNumberOfPages(); i++) {

				BufferedImage image = (BufferedImage)
					document.getPageImage(
						i,
						GraphicsRenderingHints.SCREEN,
						Page.BOUNDARY_CROPBOX,
						rotation,
						scale
					);
				RenderedImage rendImage = image;
				File file = new File(outputFolder, fnamePrefix + "-icepdf-pro-" + i + "." + format);
				ImageIO.write(rendImage, format, file);
				image.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		document.dispose();
		return true;
	}
}
