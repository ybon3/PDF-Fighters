package com.dtc.pdfboxer;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

public class PDFBoxTest {
	public static final String output = "D:\\TEMP\\pdfOutput\\outputfile.pdf";
	private PDDocument doc = new PDDocument();

	private void run() {
		try {
			//createBlankPDF();
			//helloworld();
			report();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void createBlankPDF() throws IOException {
		PDPage page = new PDPage();
		doc.addPage(page);
		doc.save(output);
	}

	public void helloworld() throws IOException {
		PDPage page = new PDPage();
		PDFont font = PDType1Font.HELVETICA_BOLD;
		doc.addPage(page);
		PDPageContentStream contents = new PDPageContentStream(doc, page);
		contents.beginText();
		contents.setFont(font, 12);
		contents.newLineAtOffset(100, 700);
		contents.showText("Foooooooooooooooooooooooo");
		contents.endText();
		contents.close();
		doc.save(output);
	}

	public void report() throws IOException {
		PDPage page = new PDPage();
		//PDFont font = PDType1Font.HELVETICA_BOLD;
		PDFont font = PDType0Font.load(doc, new File("c:\\windows\\fonts\\kaiu.ttf"));
		//PDFont font = PDType0Font.load(doc, new File("c:\\windows\\fonts\\msjh.ttc"));
		//PDFont font = PDTrueTypeFont.load(doc, new File("c:\\windows\\fonts\\kaiu.ttf"), Encoding.getInstance(COSName.WIN_ANSI_ENCODING));
		//PDFont font = PDTrueTypeFont.loadTTF(doc, new File("c:\\Windows\\Fonts\\kaiu.ttf"));
		doc.addPage(page);
		PDPageContentStream contents = new PDPageContentStream(doc, page);
		contents.beginText();
		contents.setFont(font, 12);
		contents.newLineAtOffset(100, 700);
		contents.showText("你好挖");
		contents.endText();
		contents.close();


		// 多一頁
//		PDPage page2 = new PDPage();
//		doc.addPage(page2);
//		contents = new PDPageContentStream(doc, page2);
//		contents.beginText();
//		contents.setFont(font, 12);
//		contents.newLineAtOffset(100, 750);
//		contents.showText("你好挖");
//		contents.endText();
//		contents.close();
		doc.save(output);
	}

	private void close() {
		try {
			doc.close();
		} catch (Exception e) {}
	}

	public static void main(String[] args) {
		new PDFBoxTest().run();
	}

	private Matrix genMatrix(float width, float height, float x, float y) {
		return new Matrix(
			width, // matrix 的寬度
			0, // 角度（第一象限仰角）
			0,// 斜體角度
			height, // matrix 的高度
			x, // x 座標
			y // y 座標
		);
	}
}
