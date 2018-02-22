package foxit.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.foxit.gsdk.PDFException;
import com.foxit.gsdk.PDFLibrary;
import com.foxit.gsdk.pdf.PDFDocument;
import com.foxit.gsdk.pdf.PDFPage;
import com.foxit.gsdk.pdf.Progress;
import com.foxit.gsdk.utils.FileHandler;

public class Common
{
	/** license file */
	private static String license_id = "RELKJ/ZT7XA8dF30RvPqFjhyt2QUBVa6IkkKKWyWTlA/kCEcLF4CsQ==";
	private static String unlockCode = "8f3o18ONtRkJBDdKtFJS8bag4Zn2J5lPL5P89frofMuehbsNePDaYRWJd5AcWCBqycEQ7W5zV0WRKXhc3NZK0/jlg7YD/t2PYjuw9bV7FWAaTO6yr3MP+Ao2HLYH7JBEhVqzdpcePKlllZc5+gYH5J4G2wIXRWoCABUQiRdpoJsxrxzj1opMJqDOTwk06fOMJ8GoCOzHig94H2WMeK1dWvN6b0/KBoIRSXOfaOXrvMbjOE3DDc1kdBbymB2JtHt9NX8tkw/AW8/HHkT86FZ06Wl3zpzzovA5CcItvuM0gdeYsb/UOyBHcUPrX3vnr7XiALSZlly+4JCbP/cg7Efr3P7LNg2KW+Bjyt5l+aRjA/yrFLB1ms8hdzGFw2bNEu+mJ6f236IEFQ5wCfyRRsxROjIB284pLi5NJjgq6bBAmlUoPSDiauouL8GgF/KHOyfDEdx+NwEczZiGt/s11Xdw/KIUL+khDG46n+ab+cPfdVHzUUQSuzGn27HOJ2MQUJ7ZgCeCVb0JvxAwAkRhm/0sWJsq+JQss9Wn/hxhifr3x69x1c1pFACmljIrszLG44MecV29bJIC0qsv31guSFZ+yMQIMXHYZk5JAyj+5M/j7T1e19JLn/9h6wtZupbcLkEx4ZzSB2qDxiHUyNxVN8Alk8nIw8R/fJ8yecTQ4VLH+xbFxfyiMxZ9RWN7JcVAyWIBlbvljBqRqwCgTvPq7qUBweBmKndEOzin50dNwp6nTyB77GCGuYAGBJhCXrqBLBK4nFtWsGZThBJXrSSTRK6Q7morf/tB/2r0507ZIsnOlmSMXEwrDOtRHdRpBh8s1uPxmULqoMLa5W5UQQOVrjvKHQMT7vd6647uY/VaJXevLL6RuD7ABRsED1X8etGEsHMdTuJ86EK75FT3M80qAnxsM7+39g5n2DXpdmx3vvu7KkTAvWLv9Ax39zOnUQvzHT2NLjnoV5wN7WthlpnymSD97tJ74cTPA3Etp5Dgpj6lMjeYI6cfS2PEY7WE/wNEI/XP3ApNsKPFxf5SdwiScLi5TlGS6NOKsRmoe/iogXj0AboJv9Us+PqatQ+58yRsEIC+ZQk9Cbu4EyIyn1zQVnpSFMYFn9IQb/gpB9kKMPgL9odoFoC4Oox9Ex6Z88WyljXO5COp7N/Yf16k+QcoEFFa9MVu+b9O7ignEuNrJDoc7Mj3YWUSaF1Z9w/8szxOKBFBJqLj9PsLwmlM8lCSFbGSdQ0CZfTv42U3lPFoFodMYu/3LqWD6c8CFTBAdKHoe4L++RS3ZDFXebV9H87fv/R9uyvMJfFf4zxnICHqYaq9EolFN17GLnVqJEMTed5JlLIXETqPknCwJ8wOPnbLg7lPIJEUD53cSOZQ9VlIP5mciZwXMG3Dx9AAkzaq8NkkfSgOfX7jM8KfPwranj71uOLzlkJpgVh2eEFuB/Wk1MZZECmZcdspPpFWr9rbhHzUAA5Pp7S2T8jQG3tAs7BJ8wzDFUvw/giTT8Ed4F0bk0K+cDGvY2Di93IKZYNm/fF4OqlmnkI2Z5OaFHqoaon6RFsXO8An4Pr98+cGri6p8CdHwXwjoban69G3ZqCKR7c4QZjFDLle+ier1Ogx8kp7IGpBulyERfcaREM70g7P9aVvvoXoI2KRp/EScR3RzoyNdwqRiRacQkl1M84rvvZ5BGoS2u0h9B6IsyFGNnAgkxcT8bZeVtRztFnZwGcvIkLhMdAGIEMJLbrxo8OqD6Nb4CyRw9tXCrQ+juLZQ1RlXqhtklw6";

	private int memorySize = 10 * 1024 * 1024;
	private boolean scaleable = true;

	private static FileHandler handler = null;
	private static FileOutputStream stream = null;

	public static void openLog(String path)
	{
		File file = new File(path);

		try
		{
			if (!file.exists() || file.isDirectory())
			{
				file.createNewFile();
			}

			stream = new FileOutputStream(file, true);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void outputLog(String log)
	{
		if (log == null || log.length() < 1)
			System.out.println("Please input log content.");

		System.out.println(log);

		if (stream == null)
			return;
		synchronized (stream)
		{

			try
			{
				stream.write(log.getBytes());
				stream.write("\r\n".getBytes());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void closeLog()
	{
		if (stream == null)
			return;
		try
		{
			stream.flush();
			stream.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stream = null;
	}

	//this function will output the specified message according to parameter, mainly used in PDFException catching.
	public static void outputErrMsg(int err, String msg) {
		if (err == PDFException.ERRCODE_INVALIDLICENSE)
			outputLog("Invalid license!!! Please check whether the license has related module!!!");
		else
			outputLog(msg + " Error code:" + err);
	}

	public void initlize()
	{
		PDFLibrary pdfLibrary = PDFLibrary.getInstance();
		try
		{
			pdfLibrary.initialize(memorySize, scaleable);
			pdfLibrary.unlock(license_id, unlockCode);
			outputLog("Success: Initialize and unlock the library.");
			int type = pdfLibrary.getLicenseType();
	    	if(type == PDFLibrary.LICENSETYPE_EXPIRED || type == PDFLibrary.LICENSETYPE_INVALID)
	    	{
	    		outputLog("License is invalid or expired!!!");
	    		System.exit(1);
	    	}
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			outputErrMsg(e.getLastError(), "Failed to initlize and unlock the library");
			System.exit(1);// exit
		}
	}

	public void release()
	{
		if (handler != null)
		{
			try
			{
				handler.release();
			}
			catch (PDFException e)
			{
				e.printStackTrace();
				outputErrMsg(e.getLastError(), "Failed to rlease file handle.");
			}
			finally
			{
				PDFLibrary pdfLibrary = PDFLibrary.getInstance();
				pdfLibrary.destroy();
			}
		}
		else {
			PDFLibrary pdfLibrary = PDFLibrary.getInstance();
			pdfLibrary.destroy();
		}
	}

	public static PDFDocument createDocument()
	{
		PDFDocument pdfDocument = null;
		try
		{
			pdfDocument = PDFDocument.create();
			outputLog("Success: Create a PDF document.");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			outputErrMsg(e.getLastError(), "Failed to create PDF Doument.");
			System.exit(1);// exit
		}

		return pdfDocument;
	}

	public static PDFDocument openDocument(byte[] content, String password)
	{
		PDFDocument pdfDocument = null;
		try
		{
			handler = FileHandler.create(content, FileHandler.FILEMODE_READONLY);
			if (password == null)
			{
				pdfDocument = PDFDocument.open(handler, null);
			}
			else {
				pdfDocument = PDFDocument.open(handler, password.getBytes());
			}
			outputLog("Success: Open an existing PDF document file.");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			outputErrMsg(e.getLastError(), "Failed to open PDF Doument.");
			System.exit(1);// exit
		}

		return pdfDocument;
	}

	public static PDFDocument openDocument(String filePath, String password)
	{
		PDFDocument pdfDocument = null;
		try
		{
			handler = FileHandler.create(filePath, FileHandler.FILEMODE_READONLY);
			if (password == null)
			{
				pdfDocument = PDFDocument.open(handler, null);
			}
			else {
				pdfDocument = PDFDocument.open(handler, password.getBytes());
			}
			outputLog("Success: Open an existing PDF document file.");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			outputErrMsg(e.getLastError(), "Failed to open PDF Doument.");
			System.exit(1);// exit
		}

		return pdfDocument;
	}

	public static PDFPage getPage(PDFDocument pdfDocument, int index)
	{
		PDFPage page = null;
		try
		{
			page = pdfDocument.getPage(index);
			outputLog("Success: Get a PDF page.");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			outputErrMsg(e.getLastError(), "Failed to get PDF Page.");
			System.exit(1);// exit
		}

		return page;
	}

	public static PDFPage createPage(PDFDocument pdfDocument, int index)
	{
		PDFPage page = null;
		try
		{
			page = pdfDocument.createPage(index);
			outputLog("Success: Create a PDF page.");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			outputErrMsg(e.getLastError(), "Failed to create PDF Page.");
			System.exit(1);// exit
		}
		return page;
	}

	public static boolean fileExist(String path)
	{
		File file = new File(path);
		if (file.exists() && file.isFile())
			return true;
		else {
			return false;
		}
	}

	public static boolean folderExist(String path)
	{
		File file = new File(path);
		if (file.exists() && file.isDirectory())
			return true;
		else {
			return false;
		}
	}

	public static String getFileName(String path)
	{
		int index = path.lastIndexOf("/");

		String fileName = path.substring(index + 1, path.length());
		return fileName;
	}

	public static String getFileNameNoEx(String filename) {
		String pFileName = null;
		if ((filename != null) && (filename.length() > 0)) {
			File tempFile =new File( filename.trim());
			pFileName = tempFile.getName();
			int dot = pFileName.lastIndexOf('.');
			if ((dot >-1) && (dot < (pFileName.length()))) {
				return pFileName.substring(0, dot);
			}
		}
		return pFileName;
	}

	public static boolean createFloder(String path)
	{
		File file = new File(path);
		if (file.exists() && file.isDirectory()) return true;
		boolean bCreate = false;
		bCreate = file.mkdir();

		return bCreate;
	}

	public static void saveDocument(PDFDocument pdfDocument, String path)
	{
		FileHandler handler = null;
		try
		{
			handler = FileHandler.create(path, FileHandler.FILEMODE_TRUNCATE);
			Progress progress = pdfDocument.startSaveToFile(handler, PDFDocument.SAVEFLAG_OBJECTSTREAM);
			if (progress != null)
			{
				progress.continueProgress(0);
			}

			progress.release();
			handler.release();
			outputLog("Success: Save PDF document.");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			if (handler != null)
			{
				try
				{
					handler.release();
				}
				catch (PDFException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			outputErrMsg(e.getLastError(), "Failed to save PDF Document.");
			System.exit(1);// exit
		}
	}
}
