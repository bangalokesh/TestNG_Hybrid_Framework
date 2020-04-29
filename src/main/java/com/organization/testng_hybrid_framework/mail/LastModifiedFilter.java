package com.organization.testng_hybrid_framework.mail;

import java.io.File;
import org.testng.annotations.Listeners;

import com.organization.testng_hybrid_framework.util.Listener;
@Listeners(Listener.class)

public class LastModifiedFilter {
	
	public File getLatestFilefromDir(File folder){
        File dir = (folder);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
           if (lastModifiedFile.lastModified() < files[i].lastModified()) {
               lastModifiedFile = files[i];
           }
        }
        return lastModifiedFile;
    }

}
