package com.dtc.pdfboxer;

public class ShowCharSize {
	public static void main(String[] args) {
		String alphabetic = "WTF";
		String chinese = "你好你好";
		System.out.println(alphabetic.length());
		System.out.println(chinese.length());
		System.out.println();
		System.out.println(alphabetic.getBytes().length);
		System.out.println(chinese.getBytes().length);
	}

}
