package com.serio.core.utils;

/**
 * this class contains used to read input stream of process(stdout or error) 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressTool implements Runnable{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<String> command = new ArrayList<String>();
    Map<String, String> params = new HashMap<String, String>();
    private volatile ThreadListener listener = null;
        
    public ProgressTool(List<String> command, Map<String, String> params) {
        this.command = command;
        this.params = params;
    }
    
    public void addList(ThreadListener listener) {  
      this.listener = listener;  
    }
    
    public void removeList() {  
      this.listener = null;  
    }  
    
    public void run() {
//      while(listener != null) {
        try {
          ProcessBuilder builder = new ProcessBuilder(this.command);
          builder.redirectErrorStream(true);
          Process p = builder.start();
          waitfor(p);
          p.destroy();
          logger.info("run command {} successfully", this.command.toString());
          
          if (listener != null)
            listener.threadCBFunc(params);
//          removeList();
        } catch(Exception e) {
          logger.info("Exception when run {}", this.command.toString());
        }
//      }
    }
        
    public static List<String> waitfor(Process p) throws InterruptedException,
            IOException {        
      BufferedReader r = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
      String line = "";
      List<String> allLine = new ArrayList<String>();
      while ((line = r.readLine()) != null) {
//        allLine.add(line);
      }
      return allLine;
    }
}
