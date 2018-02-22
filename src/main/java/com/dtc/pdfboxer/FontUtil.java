package com.dtc.pdfboxer;

import java.io.IOException;
import java.io.InputStream;

import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class FontUtil {
	private static TrueTypeCollection msjh;
	private static TrueTypeCollection mingliu;

	//標楷體
	public static PDFont loadKaiu(PDDocument doc) throws IOException {
		System.out.println("loadKaiu");
		return PDType0Font.load(doc, getResourceStream("kaiu.ttf"));
	}

	//細明體
	public static PDFont loadMingLiu(PDDocument doc) throws IOException {
		System.out.println("loadMingLiu");
		if (mingliu == null) {
			mingliu = new TrueTypeCollection(getResourceStream("mingliu.ttc"));
		}

		return PDType0Font.load(
			doc,
			mingliu.getFontByName("MingLiU"),
			true
		);

	}

	//細明體
	public static PDFont loadPMingLiu(PDDocument doc) throws IOException {
		System.out.println("loadPMingLiu");
		if (mingliu == null) {
			mingliu = new TrueTypeCollection(getResourceStream("mingliu.ttc"));
		}

		return PDType0Font.load(
			doc,
			mingliu.getFontByName("PMingLiU"),
			true
		);

	}

	//微軟正黑體 - 粗
	public static PDFont loadMsjh(PDDocument doc) throws IOException {
		if (msjh == null) {
			msjh = new TrueTypeCollection(getResourceStream("msjhbd.ttc"));
		}

		return PDType0Font.load(
			doc,
			msjh.getFontByName("MicrosoftJhengHeiBold"),
			true
		);
	}

	public static void close() {
		if (msjh != null) {
			try {
				msjh.close();
			} catch (Exception e){}
		}
		msjh = null;

		if (mingliu != null) {
			try {
				mingliu.close();
			} catch (Exception e){}
		}
		mingliu = null;
	}


	public static InputStream getResourceStream(String name) {
		return Context.class.getClassLoader().getResourceAsStream(name);
	}
}
