package br.com.sbm.printer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.sbm.printer.dto.MeetReceiptDTO;
import br.com.sbm.printer.service.PrinterService;
import br.com.sbm.printer.utils.PrinterUtils;

@RestController
@CrossOrigin(origins = "*")
public class PrinterController {
	
	@Autowired
	private PrinterService printerService;
	
	/**
	 * Print Receipt Jason Array
	 * 
	 * @param List<MeetReceiptDTO
	 * @return ResponseEntity<Response<Boolean>>
	 */
	@PostMapping
	@RequestMapping(value = "/print", method=RequestMethod.POST)
	public ResponseEntity<Boolean> print(@RequestBody List<MeetReceiptDTO> meetList, BindingResult result) throws Exception {
		if(meetList != null && !meetList.isEmpty()) {
			for(MeetReceiptDTO meet : meetList) {
				printerService.printReceipt(PrinterUtils.generateMeetReceipt(meet));
			}
		}
		return ResponseEntity.ok(true);
	}	
	
}
