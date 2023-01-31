package br.com.sbm.printer.dto;

import java.util.List;

public class PrinterDocDTO {

	private List<PrinterLineDTO> lines;
	
	public PrinterDocDTO() {}
	
	public PrinterDocDTO(List<PrinterLineDTO> lines) {
		this.lines = lines;
	}
	
	public List<PrinterLineDTO> getLines() {
		return lines;
	}
	public void setLines(List<PrinterLineDTO> lines) {
		this.lines = lines;
	}
}
