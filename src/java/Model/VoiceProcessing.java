/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Edwin
 */

public class VoiceProcessing {
    
    public static int num_speakers;
    public static String speakerIdentified;
    
    public static double[] getCoefficients(String nameAudio) throws UnsupportedAudioFileException, IOException{                
        File soundFile = new File("voiceAuthentication/"+nameAudio+".wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);                                    
        AudioPreProcessor ap = new AudioPreProcessor(ais);   
        MFCC mfcc = new MFCC(11025.0f);   
        List<Double> list =  new ArrayList<Double>();               
        Vector<double[]> coefficients = mfcc.process(ap);                    
        for (int t = 0; t < coefficients.size(); t++) {
            for (int i = 0; i < coefficients.get(t).length; i++) {                        
                list.add(1.0*coefficients.get(t)[i]);
            }                        
        }                
        return list.stream().mapToDouble(Double::doubleValue).toArray();        
    }
    
    public static List<String> getSpeakers(){
        num_speakers = 0;
        String path = 
        new File("voiceAuthentication/TestVoice.wav").getAbsolutePath().substring
        (0,new File("voiceAuthentication/TestVoice.wav").getAbsolutePath().length()-13);        
        String files;
        File folder = new File(path);        
        List<String> list = new ArrayList<>();
        File[] listOfFiles = folder.listFiles(); 
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            if (listOfFiles[i].isFile()) 
            {
                files = listOfFiles[i].getName();
                if (files.endsWith(".txt") || files.endsWith(".TXT"))
                {
                    num_speakers += 1;
                    list.add(files.substring(0, files.length()-4));
                }
            }
        }        
        return list;
    }
    
    
    public static boolean compareVoices() throws UnsupportedAudioFileException, IOException{
        boolean resp = false;        
        List<String> listOfSpeakers = getSpeakers();
        System.out.println("Aquiiiiiiiiiiiii<----------------------");
        System.out.println(listOfSpeakers);
        double[] arr1 = getCoefficients("TestVoice");                                                                   
        double min = Integer.MAX_VALUE;               
        for (int j=0; j<num_speakers; j++){                            
            double[] arr2 = getCoefficients(listOfSpeakers.get(j));                                                  
            DTW dtw = new DTW(arr1, arr2);
            System.out.println("Dis: "+dtw.warpingDistance+"|"+dtw.warpingDistance);
            if( dtw.warpingDistance <= min && dtw.warpingDistance <=0.4 ){
                System.out.println("Distancias: "+dtw.warpingDistance+"|"+dtw.warpingDistance);
                resp = true;
                min = dtw.warpingDistance;
                speakerIdentified = listOfSpeakers.get(j);
            }                 
        }
        return resp;
    }      
    
}

