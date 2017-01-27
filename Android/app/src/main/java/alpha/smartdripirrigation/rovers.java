package alpha.smartdripirrigation;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
            String line,temp="";

            while ((line = br.readLine()) != null) {
                String[] separated = line.split("\\$");
                if(IP.equals(separated[1])){

                    while ((line = br.readLine()) != null) {
                        separated = line.split("\\$");
                        temp += separated[0] + "$" + separated[1] + "\n"  ;
                    }
                    deleteFile(context);
                    FileOutputStream outputStream;
                    outputStream = context.openFileOutput(filename, MODE_APPEND);
                    outputStream.write(temp.getBytes());
                    outputStream.close();
                    return 1;
                }
                temp += separated[0] + "$" + separated[1] + "\n"  ;
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

    public static int deleteFile(Context context){
        File file = new File(context.getFilesDir(), filename);
        file.delete();
        return -1;
    }

    public static void makeActive(Application app,String value){
        SharedPreferences sharedPref = app.getSharedPreferences("",0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(app.getApplicationContext().getString(R.string.active_rover), value);
        editor.commit();
    }

    public static String getActive(Application app){
        SharedPreferences sharedPref = app.getSharedPreferences("",0);
        return sharedPref.getString(app.getApplicationContext().getString(R.string.active_rover), "none");
    }

    public static ArrayList<String> getList(Application app){
        ArrayList<String> tempList = new ArrayList<>();
        File file = new File(app.getApplicationContext().getFilesDir(), filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] separated = line.split("\\$");
                String temp = "Rover name: "+separated[0]+"\n"+"IP: "+separated[1];
                if(separated[0]==getActive(app)){
                    temp+= "\t\t ACTIVE";
                }

                tempList.add(temp);
            }
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return tempList;
    }


}








