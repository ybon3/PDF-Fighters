package com.dtc.pdfboxer;

public class Context {
	public static final String OUTPUT_FILEPATH = "D:\\TEMP\\pdfOutput\\outputfile.pdf";
	public static final String[] ARGS = new String[]{OUTPUT_FILEPATH};
	public static final String[] ARGS_2 = new String[]{
			OUTPUT_FILEPATH, OUTPUT_FILEPATH};
	public static final String[] ARGS_3 = new String[]{
			OUTPUT_FILEPATH,
			"C:\\Users\\Administrator\\Desktop\\icons\\uknow2much.jpg",
			OUTPUT_FILEPATH};

	//https://transfonter.org/ttc-unpack
	//https://onlinefontconverter.com/
	public static final String FONT_PATH = ".\\";
}
