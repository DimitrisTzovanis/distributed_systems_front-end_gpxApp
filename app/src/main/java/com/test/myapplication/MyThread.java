package com.test.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyThread extends Thread{
    File file;
    String arg;
    Handler handler;

    public MyThread(String arg, Handler handler, File file){
        this.arg = arg;
        this.handler = handler;
        this.file = file;
    }

    @Override
    public void run() {
        try {

            sleep(500);
            Message msg = new Message();
            Bundle bundle = new Bundle();

            try{
                //establish connection with backend


                String ipAdress = null;
                try {
                    InetAddress localHost = InetAddress.getLocalHost();
                    ipAdress = localHost.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                Socket s = new Socket();
                int timeout = 500; // 5 seconds
                s.connect(new InetSocketAddress("192.168.1.21", 10090), timeout);
                //send file to master
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(s.getInputStream()));

                int bytes = 0;
                int id = 247;
                dataOutputStream.writeInt(id);
                dataOutputStream.flush();
                dataOutputStream.writeUTF(file.getName());
                FileInputStream fileInputStream = new FileInputStream(file);
                dataOutputStream.writeLong(file.length());
                byte[] buffer = new byte[4*1024];
                while ((bytes=fileInputStream.read(buffer))!=-1){
                    dataOutputStream.write(buffer,0,bytes);
                    dataOutputStream.flush();
                }
                fileInputStream.close();

                //receive result arraylist from masterthread
                ObjectInputStream obj = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));



                ArrayList<Double> listOfData = (ArrayList<Double>) obj.readObject();



                //put strings from arraylist into bundle
                for (int i = 0; i < listOfData.size(); i++) {
                    String key = "string_" + i;
                    Double value = listOfData.get(i);
                    bundle.putString(key, String.valueOf(value));
                }

                segmentTimes segmentTimes = com.test.myapplication.segmentTimes.getInstance();
                segmentTimes.name = "";
                segmentTimes.times = new ArrayList<>();
                if(listOfData.get(11)!=0.0){
                    segmentTimes.name = String.valueOf((int) Math.abs(listOfData.get(11)));
                    for(int j = 12; listOfData.get(j)!= 0.0 ; j+=2){
                        segmentTimes.times.add("User: " + (int) Math.round(listOfData.get(j)) + "  Time: " + listOfData.get(j+1));
                    }
                }

                dataOutputStream.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            bundle.putString("result","This is the result of: "+arg);
            msg.setData(bundle);
            handler.sendMessage(msg);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}