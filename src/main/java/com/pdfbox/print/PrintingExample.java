package com.pdfbox.print;

import java.awt.print.PrinterJob;
import java.io.File;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class PrintingExample {

	public static void main(String args[]) throws Exception {
		PDDocument document = PDDocument.load(new File("D:/test/pdfTest/test.pdf"));
		PrintService myPrintService = findPrintService("Microsoft XPS Document Writer");

		//使用 PrinterJob 列印
		PrinterJob job = PrinterJob.getPrinterJob();

		job.setPageable(new PDFPageable(document));
//		job.setPrintable(new BasicPrinting());

		job.setPrintService(myPrintService);
		job.print();

		//使用 PrintService 列印
		DocPrintJob docPrintJob = myPrintService.createPrintJob();

		docPrintJob.print(new SimpleDoc(new PDFPageable(document), DocFlavor.SERVICE_FORMATTED.PAGEABLE, null), null);
		//docPrintJob.print(new SimpleDoc(new BasicPrinting(), DocFlavor.SERVICE_FORMATTED.PRINTABLE, null), null);
	}

	private static PrintService findPrintService(String printerName) {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printService : printServices) {
			System.out.println(printService.getName().trim());
			if (printService.getName().trim().equals(printerName)) {
				return printService;
			}
		}
		return null;
	}
}
