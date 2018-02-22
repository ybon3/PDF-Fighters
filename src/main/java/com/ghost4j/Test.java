package com.ghost4j;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

/**
 * http://www.ghost4j.org/releasenotes.html
 */
public class Test {
	static String src = "D:/test/pdfTest/test.pdf";
	static String dstDir = "D:/test/pdfTest/";

	public static void main(String[] args) {
		System.load(System.getProperty("user.dir") + "/lib/ghost4j/gsdll64.dll");
//		File srcFolder = new File("D:\\TEMP\\pdfOutput\\dicom");
//		for (File f : srcFolder.listFiles()) {
//			if (!f.isFile()) { continue; }
//
//			convert(f, srcFolder, 200);
//		}

		convert(new File(src), new File(dstDir), 300);
	}

	public static void convert(File src, File outputFolder, int resolution) {
//		GhostscriptRevision lRevision = Ghostscript.getRevision();
//		System.out.println(lRevision.getCopyright());
//		System.out.println(lRevision.getNumber());
//		System.out.println(lRevision.getProduct());
//		System.out.println(lRevision.getRevisionDate());

		// load PDF document
		PDFDocument lDocument = new PDFDocument();

		try {
			lDocument.load(src);

			// create renderer
			SimpleRenderer lRenderer = new SimpleRenderer();

			// set resolution (in DPI)
			lRenderer.setResolution(resolution);

			// render as images
			List<Image> images = lRenderer.render(lDocument);
			String pattern = src.getName();
			pattern = pattern.substring(0, pattern.lastIndexOf("."));
			for (int i = 0; i < images.size(); i++) {
				ImageIO.write(
					(RenderedImage)images.get(i),
					"jpg",
					new File(src.getParent(), pattern + "-" + (i + 1) + ".jpg"));
			}
		} catch (IOException|DocumentException|RendererException e) {
			e.printStackTrace();
		}
	}

}
