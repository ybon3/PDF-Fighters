package com.dtc.pdfboxer;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class ShowPageSize {
	public static void main(String[] args) {
		PDRectangle rect = PDRectangle.A4;

		System.out.println(rect.toString());
		System.out.println("Width * Height: " + rect.getWidth() + " * " + rect.getHeight());
		System.out.println("LEFT-RIGHT position X-Y: " + rect.getLowerLeftX() + " - " + rect.getUpperRightY());

	}

}
