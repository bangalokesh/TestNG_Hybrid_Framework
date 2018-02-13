package com.deloitte.testng_hybrid_framework.mail;

import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import com.deloitte.testng_hybrid_framework.GenericKeywords;
import com.deloitte.testng_hybrid_framework.util.Listener;
@Listeners(Listener.class)

public class FileFilterDateIntervalUtils implements FilenameFilter {
    String dateStart;
    String dateEnd;
    SimpleDateFormat sdf;
    private static final Logger logger = Logger.getLogger(GenericKeywords.class.getName());

    public FileFilterDateIntervalUtils(String dateStart, String dateEnd) {
    	try{
    		logger.info("Generate File Filter by Dates");
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            sdf = new SimpleDateFormat("yyyy-MM-dd");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public boolean accept(File dir, String name) {
        Date d = new Date(new File(dir, name).lastModified());
        String current = sdf.format(d);
        return ((dateStart.compareTo(current) < 0
                && (dateEnd.compareTo(current) >= 0)));
    }
}