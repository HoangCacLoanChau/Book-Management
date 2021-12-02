/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chauhcl.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Web application lifecycle listener.
 *
 * @author hoang
 */

public class ContextListener implements ServletContextListener {

    private final Logger LOGGER = Logger.getLogger(ContextListener.class);
    Map<String,String> Map;
   
    private Map<String,String> readRoadMapFromFile(String path){
        FileReader fr = null; 
        BufferedReader br = null;
        Map = new HashMap<>();
        try {
            fr = new FileReader(new File(path));
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine())!= null){
                int index = line.lastIndexOf("=");
                String functionName = line.substring(0, index);
                String url = line.substring(index + 1);
                Map.put(functionName, url);
            }
        } catch (IOException ex) {
            LOGGER.error(ex);
        }finally{
             try {
                if(br != null){
                 br.close();
             }
             
             if(fr != null){
                 fr.close();
             }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        return Map;
    }
    
    private void loadLog4j(ServletContext context){
        String path = context.getInitParameter("log4j-config-location");
        String realPath = context.getRealPath("/" + path);
        
        //initialize LOG4J
        PropertyConfigurator.configure(realPath);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
          ServletContext context = sce.getServletContext();
        String pathRoadMap = context.getInitParameter("roadmap");
        String path = sce.getServletContext().getRealPath(pathRoadMap);
        Map = readRoadMapFromFile(path);
        context.setAttribute("ROAD", Map);
        loadLog4j(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
