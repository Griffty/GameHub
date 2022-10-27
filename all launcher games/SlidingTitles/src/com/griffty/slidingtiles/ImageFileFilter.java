package com.griffty.slidingtiles;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {
        boolean accept = false;
        String fileName = file.getName();
        if(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || file.isDirectory()){
            accept = true;
        }
        return accept;
    }

    @Override
    public String getDescription() {
        return "All image files: .jpg, .png, .gif";
    }

}
