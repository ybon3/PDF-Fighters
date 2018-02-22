package com.dtc.pdfboxer;

import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.dtc.pdfboxer.Converter;
import com.dtc.pdfboxer.Converter.ImageFormat;

public class SaveAsJPG {
	static String src = "D:/test/pdfTest/test.pdf";
	static String dstDir = "D:/test/pdfTest/";

	public static void main(String[] args){
		long s = System.currentTimeMillis();
//		File srcFolder = new File("D:\\TEMP\\pdfOutput\\dicom");
//		for (File f : srcFolder.listFiles()) {
//			if (!f.isFile()) { continue; }
//
//			Converter.toJPG(f, srcFolder, 150);
//		}

		Converter.toJPG(new File(src), new File(dstDir), 360);
		System.out.println(System.currentTimeMillis() - s);
	}

	public static boolean convert(File srcFile, File outputFolder, ImageFormat format, float dpi, ImageType type) {

		if (!outputFolder.isDirectory()) { throw new IllegalArgumentException(); }

		try (PDDocument document = PDDocument.load(srcFile)) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);

			// 輸出檔名前綴
			String fnamePrefix = srcFile.getName();
			fnamePrefix = fnamePrefix.substring(0, fnamePrefix.lastIndexOf("."));

			for (int page = 0; page < document.getNumberOfPages(); page++) {
				ImageIO.write(
					pdfRenderer.renderImageWithDPI(page, dpi, type),
					format.name(),
					new File(outputFolder, fnamePrefix + "-pdfbox-" + (page+1) + format.extName)
				);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
