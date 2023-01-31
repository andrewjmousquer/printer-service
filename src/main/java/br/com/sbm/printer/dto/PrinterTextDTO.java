package br.com.sbm.printer.dto;

public class PrinterTextDTO {

	private String value;
	private boolean bold;
	
	public PrinterTextDTO(String value, boolean bold) {
		this.value = value;
		this.bold = bold;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
}
