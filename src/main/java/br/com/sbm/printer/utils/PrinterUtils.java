package br.com.sbm.printer.utils;

import java.util.LinkedList;
import java.util.List;

import br.com.sbm.printer.dto.MeetReceiptDTO;
import br.com.sbm.printer.dto.PrinterDocDTO;
import br.com.sbm.printer.dto.PrinterLineDTO;
import br.com.sbm.printer.dto.PrinterTextDTO;
import br.com.sbm.printer.enums.LineAlingn;

public class PrinterUtils {

	public static PrinterDocDTO generateMeetReceipt(MeetReceiptDTO meet) {
		List<PrinterLineDTO> lineLists = new LinkedList<PrinterLineDTO>();
		
		lineLists.add(generatePrinterLineTitle(meet.getHospital() , 10, LineAlingn.CENTER));
		lineLists.add(generatePrinterLineSimple(meet.getAddres(),6, LineAlingn.CENTER));
		
		if(meet.getBed() != null) {
			lineLists.add(generatePrinterLineDetail("Leito: ", meet.getBed(), 9, LineAlingn.LEFT));
		}
				
		if(meet.getName() != null) {
			lineLists.add(generatePrinterLineDetail("Paciente: ", meet.getName(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getBirthdate() != null) {
			lineLists.add(generatePrinterLineDetail("Nascimento: ", meet.getBirthdate(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getRegistry() != null) {
			lineLists.add(generatePrinterLineDetail("Matrícula: ", meet.getRegistry(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getRegistration() != null) {
			lineLists.add(generatePrinterLineDetail("Prontuário: ", meet.getRegistration(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getTransaction() != null) {
			lineLists.add(generatePrinterLineDetail("Registro: ", meet.getTransaction(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getOperator() != null) {
			lineLists.add(generatePrinterLineDetail("Operadora: ", meet.getOperator(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getCoverage() != null) {
			lineLists.add(generatePrinterLineDetail("Plano: ", meet.getCoverage(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getMeet() != null) {
			lineLists.add(generatePrinterLineDetail("Refeição: ", meet.getMeet(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getDate() != null) {
			lineLists.add(generatePrinterLineDetail("Data: ", meet.getDate(), 7, LineAlingn.LEFT));
		}
		
		if(meet.getStatus() != null) {
			lineLists.add(generatePrinterLineDetail("Status: ", meet.getStatus(), 7, LineAlingn.LEFT));
		}
		
		lineLists.add(generatePrinterLineSimple("  ", 15, LineAlingn.CENTER));
		
		lineLists.add(generateSignLine(7, LineAlingn.CENTER));
		
		lineLists.add(generatePrinterLineSimple(meet.getName(), 7, LineAlingn.CENTER));
		
	    return new PrinterDocDTO(lineLists);
	}	
	
	public static PrinterLineDTO generatePrinterLineTitle(String title, int fontSize, LineAlingn align){
		List<PrinterTextDTO> textList = new LinkedList<PrinterTextDTO>();
		textList.add(new PrinterTextDTO(title, true));
		return new PrinterLineDTO(fontSize, textList, align);
	}
	
	public static PrinterLineDTO generatePrinterLineSimple(String text, int fontSize, LineAlingn align){
		List<PrinterTextDTO> textList = new LinkedList<PrinterTextDTO>();
		textList.add(new PrinterTextDTO(text, false));
		return new PrinterLineDTO(fontSize, textList, align);
	}
	
	public static PrinterLineDTO generatePrinterLineDetail(String title, String value, int fontSize, LineAlingn align){
		List<PrinterTextDTO> textList = new LinkedList<PrinterTextDTO>();
		textList.add(new PrinterTextDTO(title, true));
		textList.add(new PrinterTextDTO(value, false));
		return new PrinterLineDTO(fontSize, textList, align);
	}
	
	public static PrinterLineDTO generateSignLine(int fontSize, LineAlingn align){
		List<PrinterTextDTO> textList = new LinkedList<PrinterTextDTO>();
		textList.add(new PrinterTextDTO("-----------------------------------------------------------", false));
		return new PrinterLineDTO(fontSize, textList, align);
	}
}
