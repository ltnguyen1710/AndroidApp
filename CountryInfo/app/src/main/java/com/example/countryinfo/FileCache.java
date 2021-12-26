package com.example.countryinfo;

import java.io.File;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class FileCache {
    
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"CountryInfo");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists()){

                if(cacheDir.mkdirs()){
                    System.out.println("create success");
                }
                else{
                    System.out.println("error");
                }



        }

    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null){
            System.out.println("none file");
            return;
        }


        for(File f:files)
            f.delete();
    }

}