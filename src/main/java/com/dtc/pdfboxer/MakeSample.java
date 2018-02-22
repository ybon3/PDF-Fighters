package com.dtc.pdfboxer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.dtc.pdfboxer.TextArea;
import com.dtc.pdfboxer.TextLine;

public class MakeSample {
	public static final String output = "D:\\TEMP\\pdfOutput\\20170518-sample.pdf";
	public static final String imagePath = MakeSample.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "title.png";
	private PDDocument doc = new PDDocument();
	private static final String TEST_STR =
		"影像所见：\n" +
		"双侧肺野清晰，未见明确实质性或间质性病变；双肺纹理走向分布正常；双侧肺门不大，纵隔无增宽；双侧膈面光整，双侧肋膈角锐利。两侧胸廓对称。\n" +
		"心影大小、形态、位置未见异常。" +
		"\n\n\n\n"+
		"意见：\n" +
		"心肺膈未见明确异常。" +
		"\n\n\n\n"+
		"Before returning to Washington, the former president dove into an aquatic and athletic challenge with his friend, Virgin Group founder and billionaire Richard Branson, while vacationing on the British Virgin Islands with former first lady Michelle Obama." +
		"\n"+
		"Now that Obama is free from many Secret Service-imposed restrictions that did not allow him to partake in activities like surfing, Branson offered Obama the chance to learn how to kitesurf.";

	private void run() {
		try {
			report();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void report() throws IOException {
		PDPage page = new PDPage(PDRectangle.A4);//new PDPage();
		doc.addPage(page);
		PDFont font = FontUtil.loadKaiu(doc);

		PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, false);
		TextLine textLine = new TextLine(contentStream, font, 23f);


		//放射科数字X线检查报告单
		textLine.showText("放射科数字X线检查报告单", 170, 750);

		textLine.setFontSize(13f);
		textLine.showText("检查时间(摄片时间):2016-11-07 16:29:00", 40, 725);
		textLine.showText("RIS号：509897", 320, 725);
		textLine.showText("X线号：2060128", 450, 725);
		addDivide(contentStream, page.getMediaBox(), 720, 30f);//分隔線

		textLine.setFontSize(14f);
		textLine.showText("患者名称：周妹英", 35, 705);
		textLine.showText("性别：女", 200, 705);
		textLine.showText("年龄：41岁", 280, 705);
		textLine.showText("诊疗卡号：", 400, 705);

		textLine.setFontSize(13f);
		textLine.showText("科室：乳腺中心", 40, 680);
		textLine.showText("床号：41", 280, 680, FontUtil.loadMsjh(doc), 13f);
		textLine.showText("住院号：192798", 400, 680, FontUtil.loadMsjh(doc), 13f);
		addDivide(contentStream, page.getMediaBox(), 670, 30f);//分隔線

		textLine.setFontSize(14f);
		textLine.showText("检查方法：DR胸部正侧位片", 35, 645);

		// main body
		TextArea fta = new TextArea(contentStream);
		fta.setFont(FontUtil.loadMingLiu(doc));
		fta.setFontSize(16f);
		fta.setLineSpacing(5f);
		fta.showText(TEST_STR, TextArea.create(590, 35, page.getMediaBox()));

		// footer
		textLine.showText("报告医师：", 35, 60);
		textLine.showText("审核医师：", 280, 60);
		addDivide(contentStream, page.getMediaBox(), 50, 30f);//分隔線
		textLine.showText("报告时间：2016-11-07 17:14:00", 30, 35);
		textLine.showText("审核时间：2016-11-07 17:23:00", 280, 35);

		//image
		PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
		float scale = 0.75f;
		contentStream.drawImage(
			pdImage,
			17,
			770,
			pdImage.getWidth() * scale,
			pdImage.getHeight() * scale
		);

		contentStream.close();
		FontUtil.close();
		doc.save(output);
	}

	private void close() {
		try {
			doc.close();
		} catch (Exception e) {}
	}

	public static void main(String[] args) {
		new MakeSample().run();
	}

	/**
	 * 設定分隔線，如果要設定線的樣式可以參考 {@link PDPageContentStream#setLineDashPattern(float[], float)}
	 */
	private void addDivide(PDPageContentStream contentStream, PDRectangle rect, float y, float xMargin)
			throws IOException {
		contentStream.setLineWidth(0.5f);
		contentStream.moveTo(xMargin, y);
		contentStream.lineTo(rect.getUpperRightX()-xMargin, y);
		contentStream.stroke();
	}
}
