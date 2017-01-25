package alpha.smartdripirrigation;


import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import static android.content.Context.MODE_APPEND;


/**
 * Created by loga on 1/24/2017.
 */

public class rovers {

    private static String filename = "rovers.ini";
    private String name , ip ;
    rovers(){
        name=ip="";
    }
    rovers(String NAME,String IP){
        this.name = NAME;
        this.ip = IP;
    }

    public static int addRover(String NAME, String IP, Context context){

        String temp = NAME + "$" +IP + "\n"  ;
        FileOutputStream outputStream;
        if(search_ip(IP,context)== 0 && search_rover_name(NAME,context)== 0) {
            try {
                outputStream = context.openFileOutput(filename, MODE_APPEND);
                outputStream.write(temp.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }
        else if ( search_ip(IP,context)!= 0 && search_rover_name(NAME,context)!=0 ){
            return 0 ;
        }
        else if (search_rover_name(NAME,context)!=0){
            return  -1;
        }
        else{
            return  -2;
        }

    }

    public static int deleteRover(String IP,Context context){
        File file = new File(context.getFilesDir(), filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] separated = line.split("\\$");
                if(IP.equals(separated[1])){
                    String temp ="";
                    while ((line = br.readLine()) != null) {
                        separated = line.split("\\$");
                        temp+= separated[0] + "$" + separated[1] + "\n"  ;
                    }
                    deleteFile(context);
                    FileOutputStream outputStream;
                    outputStream = context.openFileOutput(filename, MODE_APPEND);
                    outputStream.write(temp.getBytes());
                    outputStream.close();
                    break;
                }

            }
            br.close();

        }
        catch (IOException e) {

        }
        return 0;
    }

    public static int search_ip(String IP,Context context){

        File file = new File(context.getFilesDir(), filename);
        int count = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] separated = line.split("\\$");
                if(IP.equals(separated[1])){
                    return count;
                }
                count++;
            }
            br.close();

        }
        catch (IOException e) {

        }
        return 0;
    }

    public static int search_rover_name(String NAME,Context context){

        File file = new File(context.getFilesDir(), filename);
        int count = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] separated = line.split("\\$");
                if(NAME.equals(separated[0])){
                    return count;
                }
                count++;
            }
            br.close();
        }
        catch (IOException e) {

        }
        return 0;
    }

    public static void deleteFile(Context context){
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }

}








