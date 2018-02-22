package com.pdfbox.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class BasicPrinting implements Printable {

	public static void main(String[] args) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new BasicPrinting());
		boolean doPrint = job.printDialog();

		if (doPrint) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
				// The job did not successfully
				// complete
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {
			 return NO_SUCH_PAGE;
		}

		// User (0,0) is typically outside the
		// imageable area, so we must translate
		// by the X and Y values in the PageFormat
		// to avoid clipping.
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		// Now we perform our rendering
		g.drawString("中文字", 0, 0);

		// tell the caller that this page is part
		// of the printed document
		return PAGE_EXISTS;
	}

}
