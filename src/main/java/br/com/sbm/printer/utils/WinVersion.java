package br.com.sbm.printer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WinVersion {

	public static String getWinVersion() {
		 Runtime rt; 
         Process pr; 
         BufferedReader in;
         String line = "";
         String edition = "";
         String fullOSName = "";
         final String   SEARCH_TERM = "OS Name:";
         final String[] EDITIONS = { "Basic", "Home", "Professional", "Enterprise" };

         try {
            rt = Runtime.getRuntime();
            pr = rt.exec("SYSTEMINFO");
            in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while((line=in.readLine()) != null) {
               if(line.contains(SEARCH_TERM)) {
                  fullOSName = line.substring(line.lastIndexOf(SEARCH_TERM) + SEARCH_TERM.length(), line.length()-1);
                  break;
               } 
            }

            for(String s : EDITIONS) {
               if(fullOSName.trim().contains(s)) {
                  edition = s;
               }
            }

           	return fullOSName + " " + edition;

         } catch(IOException ioe) {
        	 System.err.println(ioe.getMessage());
         }
         
         return null;
	}
}
