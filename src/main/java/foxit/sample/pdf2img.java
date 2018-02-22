package foxit.sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.io.IOUtils;

import com.foxit.gsdk.PDFException;
import com.foxit.gsdk.image.Bitmap;
import com.foxit.gsdk.pdf.PDFDocument;
import com.foxit.gsdk.pdf.PDFPage;
import com.foxit.gsdk.pdf.Progress;
import com.foxit.gsdk.pdf.RenderContext;
import com.foxit.gsdk.pdf.Renderer;
import com.foxit.gsdk.utils.Matrix;
import com.foxit.gsdk.utils.Rect;
import com.foxit.gsdk.utils.Size;
import com.foxit.gsdk.utils.SizeF;

import foxit.common.Common;

public class pdf2img
{
	private static String LIB_DIR = System.getProperty("user.dir") + "//lib//foxit//";
	// load Foxit PDF Library
	static
	   {
	      try
	      {
	        String name = System.getProperty("os.name");
	        String arch = System.getProperty("os.arch");
	        System.out.println("os.name = " + name);
	        System.out.println("os.arch = " + arch);
	           if (name.toLowerCase().startsWith("win"))//windows
	        {
	             if (arch.contains("64"))
	           {
	                System.load(LIB_DIR + "fsdk_java_win64.dll");
	           }
	             else {
	                System.load(LIB_DIR + "fsdk_java_win32.dll");
	           }
	        }
	           else {//linux
	           if (arch.contains("64"))
	              System.load(LIB_DIR + "libfsdk_java_linux64.so");
	           else {
	              System.load(LIB_DIR + "libfsdk_java_linux32.so");
	           }
	        }

	      }
	      catch (UnsatisfiedLinkError e)
	      {
	        System.out.println("Native code library failed to load.\n" + e);
	        System.exit(1);
	      }
	   }


	//The default page size
	private final static float pageWidth = 612;
	private final static float pageHeight = 792;
	private final static float margin = 10;

	public static void main(String[] args) throws IOException
	{
		long s = System.currentTimeMillis();
		Common common = new Common();

		String inputSrcFile = "D:/test/pdfTest/test3.pdf";
		String outputFloder = "D:/test/pdfTest/";

		//Judge if to command line arguments.
		//usage:java pdf2img <srcfile> <destfloder>
		if (args.length >= 1)
		{
			inputSrcFile = args[0];
			if (args.length >= 2)
			{
				outputFloder = args[1];
			}

			if (!Common.fileExist(inputSrcFile) || (outputFloder != "" && !Common.folderExist(outputFloder)))
			{
				System.out.println("Failed: The input source file or output floder is not exist!");
				System.exit(1);
			}
		}

		if (outputFloder == "")
		{
			outputFloder = "../output_files/pdf2img";
			Common.createFloder(outputFloder);
		}

		//open and write
//		Common.openLog(outputFloder + "/log.txt");
//		Common.outputLog("Foxit PDF standard JAVA SDK example: pdf2img");

		common.initlize();

		if (inputSrcFile != "")
		{
			PDFToImage(inputSrcFile, outputFloder);
		}
		else {
			//use default path
			inputSrcFile = "../testfiles/";
			PDFToImage(inputSrcFile + "AboutFoxit.pdf", outputFloder);
			PDFToImage(inputSrcFile + "Bookmark.pdf", outputFloder);
			PDFToImage(inputSrcFile + "3BigPreview.pdf", outputFloder);
		}

		//release the resource
		common.release();
		//close log file
//		Common.closeLog();
		System.out.println(System.currentTimeMillis() - s);
	}

	public static void PDFToImage(String inputSrcFile, String outputFloder) throws IOException {
		try {
//			PDFDocument document = Common.openDocument(inputSrcFile, "");
			PDFDocument document = Common.openDocument(IOUtils.toByteArray(new FileInputStream(inputSrcFile)), "");
			int count = document.countPages();
			for(int i = 0;i<count;i++)
			{
				PDFPage page = document.getPage(i);
				Bitmap bitmap = renderPageToBitmap(page);

				String fileName = Common.getFileName(inputSrcFile);
				fileName = Common.getFileNameNoEx(fileName);
				String imageName;
//				imageName =fileName + "_pageIndex_"+ i + ".bmp";
//				saveBitmap(bitmap, outputFloder + "/" + imageName);
//
				imageName =fileName + "-foxit-"+ i + ".jpg";
				saveBitmap(bitmap, outputFloder + "/" + imageName);

//				imageName =fileName + "-foxit-"+ i + ".png";
//				saveBitmap(bitmap, outputFloder + "/" + imageName);

//				imageName =fileName + "_pageIndex_"+ i + ".gif";
//				saveBitmap(bitmap, outputFloder + "/" + imageName);
//
//				imageName =fileName + "_pageIndex_"+ i + ".jpeg";
//				saveBitmap(bitmap, outputFloder + "/" + imageName);
				bitmap.release();
				document.closePage(page);
			}
			document.close();
		}
		catch (PDFException e)
		{
			e.printStackTrace();
			Common.outputErrMsg(e.getLastError(), "Failed to tansform PDF to image document.");
		}
	}

	public static Bitmap renderPageToBitmap(PDFPage pPage){
		Bitmap bmp = null;
		try
		{
			Progress progress = pPage.startParse(PDFPage.RENDERFLAG_NORMAL);
			if(progress != null)
			{
				int ret = Progress.TOBECONTINUED;
				while (ret == Progress.TOBECONTINUED)
				{
					ret = progress.continueProgress(30);
				}
			}
			progress.release();
			SizeF pageSize = pPage.getSize();
			Matrix matrix = new Matrix();
			int width = (int)(pageSize.getWidth()*5);
			int height = (int)(pageSize.getHeight()*5);
			matrix = pPage.getDisplayMatrix(0, 0, width, height, 0);
			bmp = Bitmap.create(new Size(width, height), Bitmap.FORMAT_24BPP_BGR, null, 0);
			Rect rect = new Rect(0, 0, width, height);
			bmp.fillRect(0xffffffffL, rect);
			Renderer render = Renderer.create(bmp);
			RenderContext renderContext = RenderContext.create();
			renderContext.setMatrix(matrix);
			renderContext.setFlags(RenderContext.FLAG_ANNOT);
			Progress renderProgress = pPage.startRender(renderContext, render, 0);
			if(renderContext !=null){
				int ret = Progress.TOBECONTINUED;
				while(ret == Progress.TOBECONTINUED ){
					ret = renderProgress.continueProgress(30);
				}
			}
			renderProgress.release();
		}
		catch(PDFException e)
		{
			e.printStackTrace();
			Common.outputErrMsg(e.getLastError(), "Failed to render page to bitmap.");
		}
		return bmp;
	}

	public static boolean saveBitmap(Bitmap bitmap, String outPath) throws PDFException,IOException {
		if (null == bitmap || null == outPath || "" == outPath)
			return false;

		String outfileFormat = outPath.substring((outPath.length()-3), outPath.length());
		int bmpFormat = bitmap.getFormat();
		String[] supportFormatArray = null;
		switch(bmpFormat)
		{
		case Bitmap.FORMAT_8BPP_GRAY:
			{
				supportFormatArray = new String[] {"bmp", "jpg", "jpeg", "png", "gif"};
				break;
			}
		case Bitmap.FORMAT_24BPP_BGR:
		case Bitmap.FORMAT_24BPP_RGB:
			{
				supportFormatArray = new String[] {"bmp", "jpg", "jpeg", "png", "gif"};
				break;
			}
		case Bitmap.FORMAT_32BPP_BGRA:
		case Bitmap.FORMAT_32BPP_RGBA:
			{
				supportFormatArray = new String[] {"png"};
				break;
			}
		}

		boolean isSupport = false;
		for (int i=0; i<supportFormatArray.length; i++){
			if (0 == outfileFormat.compareTo(supportFormatArray[i])){
				isSupport = true;
				break;
			}
		}
		if (false == isSupport)
			return false;

		BufferedImage finalImage = null;
		try{
			finalImage = bitmap.convertToBufferedImage();
		}
		catch(PDFException e){
			e.printStackTrace();
			return false;
		}

		if (null != finalImage){
			File file = new File(outPath);
			file.createNewFile();

			ImageIO.write(finalImage, outfileFormat, file);
		}
		else
			return false;

		return true;
	}
}
