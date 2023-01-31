package br.com.sbm.printer.service;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sbm.printer.dto.PrinterDocDTO;
import br.com.sbm.printer.dto.PrinterLineDTO;
import br.com.sbm.printer.dto.PrinterTextDTO;
import br.com.sbm.printer.enums.LineAlingn;

@Service
public class PrinterService implements Printable {

	private PrinterDocDTO printerDoc;
	
	private int defaultXDrawStart = 15;
	private int defaultYDrawStart = 11;
	
	private double pageWidth = 265;
	
	@Value("${printer.name}")
	private String printerName;
	
	@Value("${printer.font}")
	private String fonte;

    public List<String> getPrinters(){
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }
    
    public void setPrinter(String printer) {
    	this.printerName = printer;
    }

    public void printString(String text) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService service = findPrintService(printService);
        DocPrintJob job = service.createPrintJob();

        try {
            byte[] bytes = text.getBytes("ISO-8859-1");
            Doc doc = new SimpleDoc(bytes, flavor, null);
            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void printBytes(byte[] bytes) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService service = findPrintService(printService);

        DocPrintJob job = service.createPrintJob();

        try {
            Doc doc = new SimpleDoc(bytes, flavor, null);
            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cutPaper() {
		byte[] cutP = new byte[] { 0x1d, 'V', 1 };
	    printBytes(cutP);
	}
    
    public void printReceipt(PrinterDocDTO printerDoc) throws PrintException, PrinterException {
		this.printerDoc = printerDoc;
		    	
    	DocAttributeSet das = new HashDocAttributeSet();
    	das.add(new MediaPrintableArea(0, 0, 85, 100, MediaPrintableArea.MM));
    	
	    DocPrintJob job = getPrinterJob();
	    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
	    SimpleDoc doc = new SimpleDoc(this, flavor, das);
	    job.print(doc, null);
	}

    @Override
    public int print(Graphics g, PageFormat pf, int page) {
	    if (page > 0) {
	        return NO_SUCH_PAGE;
	    }
	    
	    pf.getWidth();

	    int y = this.defaultYDrawStart;
	    for(PrinterLineDTO lineDto : this.printerDoc.getLines()) {
	    	int x = this.defaultXDrawStart;
	    	if(lineDto.getAlign().equals(LineAlingn.CENTER)) {
	    		int textWidth = 0;
	    		for(PrinterTextDTO textDto : lineDto.getText()) {
	    			g.setFont(new Font(this.fonte, (textDto.isBold() ? Font.BOLD : Font.PLAIN), lineDto.getFontSize()));
	    			textWidth += g.getFontMetrics().stringWidth(textDto.getValue());
	    		}
	    		if(textWidth < this.pageWidth  ) {
	    			x = (int) ((this.pageWidth - textWidth) / 2 ) - this.defaultXDrawStart ;
	    			if(x < this.defaultXDrawStart) {
	    				x = this.defaultXDrawStart;
	    			}
	    		}
	    	} else {
	    		x = this.defaultXDrawStart;
	    	}
	    	
	    	for(PrinterTextDTO textDto : lineDto.getText()) {
			   g.setFont(new Font(this.fonte, (textDto.isBold() ? Font.BOLD : Font.PLAIN), lineDto.getFontSize()));
		       g.drawString(textDto.getValue(), x, y);
		       x += g.getFontMetrics().stringWidth(textDto.getValue());
	    	}
	    	y += this.defaultYDrawStart;
	    	
	    } 

	    return PAGE_EXISTS;
	}
    
    private PrintService findPrintService(PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(this.printerName)) {
            	return service;
            }
        }

        return null;
    }
    
    private DocPrintJob getPrinterJob() {
	    DocPrintJob job = null;
	    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
	    for (PrintService printer : printServices) {
	        if (printer.getName().equals(this.printerName)) {
	            job = printer.createPrintJob();
	        }
	    }
	    return job;
	}
}