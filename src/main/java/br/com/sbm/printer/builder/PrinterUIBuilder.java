package br.com.sbm.printer.builder;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sbm.printer.service.PrinterService;
import br.com.sbm.printer.utils.WinVersion;

@Component
@Scope("prototype")
public class PrinterUIBuilder {
	
	@Autowired
	private PrinterService printerService;

	private TrayIcon trayIcon;
	
	public void initUI() {
		if(!SystemTray.isSupported()){
			return ;
		}

		try {
			UIManager ui = new UIManager();
			ui.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ui.put("OptionPane.background", Color.white);
			ui.put("Panel.background", Color.white);
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
		SystemTray systemTray = SystemTray.getSystemTray();
		PopupMenu trayPopupMenu = new PopupMenu();

		MenuItem actionAbout = new MenuItem("Sobre");
		actionAbout.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JLabel label = new JLabel(generateSBMText());
		    	label.setHorizontalAlignment(SwingConstants.CENTER);
                JOptionPane.showMessageDialog( null, label,"SBM Tecnhology", JOptionPane.INFORMATION_MESSAGE, getImageIcon("images/sbm_logo_small.png"));
		    }
		});     
		trayPopupMenu.add(actionAbout);
		
		MenuItem actionDefaultPrinter = new MenuItem("Impressora padrão");
		actionDefaultPrinter.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
				List<String> printers = printerService.getPrinters();
		    	String printer = (String) JOptionPane.showInputDialog(null, "Escolha a impressora", "Impressora padrão", JOptionPane.QUESTION_MESSAGE, null,  printers.toArray(), printers.get(0));
		    	printerService.setPrinter(printer);
		    }
		});     
		trayPopupMenu.add(actionDefaultPrinter);
		trayPopupMenu.addSeparator();
		
		MenuItem actionClone = new MenuItem("Sair");
		actionClone.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0);             
		    }
		});
		trayPopupMenu.add(actionClone);
		
		trayIcon = new TrayIcon(getImage("images/printer_logo.png"), "SBM Printer Service", trayPopupMenu);
		trayIcon.setImageAutoSize(true);
		
		try{
		    systemTray.add(trayIcon);
		}catch(AWTException awtException){
		    awtException.printStackTrace();
		}
		
		
		sendMessageNotification("Printer Server Iniciado", "Printer Server Iniciado");
	}
	
	private String generateSBMText() {
		StringBuilder sbmText = new StringBuilder();
		sbmText.append("<html><center>");
		sbmText.append("<b>SBM Tecnhology - Printer Server</b><br>");
		sbmText.append("<a href='https://www.sbmtechnology.com.br/'>www.sbmtechnology.com.br</a><br>");
		sbmText.append("2020<br><br>");
		sbmText.append("Versão 1.0 - 10/11/2020");
		sbmText.append("</center></html>");
		return sbmText.toString();
	}
	
	public void sendMessageNotification(String title, String message) {
		if(WinVersion.getWinVersion().contains("Windows 10")) {
			trayIcon.displayMessage(title, message, MessageType.INFO);
		}
	}
	
	public static Image getImage(final String pathAndFileName) {
	    final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
	    return Toolkit.getDefaultToolkit().getImage(url);
	}
	
	public static ImageIcon getImageIcon(final String pathAndFileName) {
	    final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
	    return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
	}
}
