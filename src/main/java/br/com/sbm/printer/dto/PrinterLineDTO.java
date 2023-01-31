package br.com.sbm.printer.dto;

import java.util.List;

import br.com.sbm.printer.enums.LineAlingn;

public class PrinterLineDTO {
	
	private int fontSize;
	private List<PrinterTextDTO> text;
	private LineAlingn align;
	
	public PrinterLineDTO(int fontSize, List<PrinterTextDTO> text, LineAlingn align) {
		this.fontSize = fontSize;
		this.text = text;
		this.align = align;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public List<PrinterTextDTO> getText() {
		return text;
	}
	public void setText(List<PrinterTextDTO> text) {
		this.text = text;
	}
	public LineAlingn getAlign() {
		return align;
	}
	public void setAlign(LineAlingn align) {
		this.align = align;
	}
	
}
