package br.com.sbm.printer;

import java.util.TimeZone;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sbm.printer.builder.PrinterUIBuilder;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PrinterApplication {

	public static void main(String[] args) {
	    ConfigurableApplicationContext context = new SpringApplicationBuilder(PrinterApplication.class).headless(false).run(args);
	    PrinterUIBuilder uiBuilder = context.getBean(PrinterUIBuilder.class);
   
		SwingUtilities.invokeLater(() -> {
			uiBuilder.initUI();
		});
	}
	
	@Autowired
    public void configureJackson(ObjectMapper objectMapper) {
        objectMapper.setTimeZone(TimeZone.getDefault());
    }
	
}
