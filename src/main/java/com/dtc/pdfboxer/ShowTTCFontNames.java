package com.dtc.pdfboxer;

import java.io.IOException;

import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.ttf.TrueTypeCollection.TrueTypeFontProcessor;

public class ShowTTCFontNames {
	public static void main(String[] args) {

		TrueTypeCollection ttc = null;
		try {
			ttc = new TrueTypeCollection(FontUtil.getResourceStream("mingliub.ttc"));
			ttc.processAllFonts(new TrueTypeFontProcessor() {
				@Override
				public void process(TrueTypeFont ttf) throws IOException {
					System.out.println(ttf.getName());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ttc != null) {
				try {
					ttc.close();
				} catch (Exception e){}
			}
		}
	}
}
