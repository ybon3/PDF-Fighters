package com.dtc.pdfboxer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.dtc.pdfboxer.TextArea;
import com.dtc.pdfboxer.TextLine;

public class MyPage extends PDPage {

	private PDDocument document;
	private PDPageContentStream contentStream;
	private PDRectangle mediaBox;
	private TextArea fixedTextArea;
	private TextLine textLine;

	public MyPage(PDRectangle mediaBox, PDDocument doc) throws IOException {
		this(mediaBox, doc, AppendMode.OVERWRITE, true);
	}

	public MyPage(PDRectangle mediaBox, PDDocument doc, AppendMode appendContent, boolean compress) throws IOException {
		super(mediaBox);
		this.mediaBox = mediaBox;
		this.document = doc;
		this.contentStream = new PDPageContentStream(doc, this, appendContent, compress);
		this.fixedTextArea = new TextArea(contentStream);
		this.textLine = new TextLine(contentStream);
	}


	public void showText(String text) {
		//...
	}

	/**
	 * 設定分隔線，如果要設定線的樣式可以參考 {@link PDPageContentStream#setLineDashPattern(float[], float)}
	 */
	public void addDivide(float y, float xMargin)
			throws IOException {
		contentStream.setLineWidth(0.5f);
		contentStream.moveTo(xMargin, y);
		contentStream.lineTo(mediaBox.getUpperRightX()-xMargin, y);
		contentStream.stroke();
	}

	public void addImage(String imagePath, float scale, float x, float y) throws IOException {
		PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
		contentStream.drawImage(
			pdImage,
			x,
			y,
			pdImage.getWidth() * scale,
			pdImage.getHeight() * scale
		);
	}
}
