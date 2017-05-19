
package Model;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Edwin
 */

public class Test{
    
    public static void voiceRecorder(String nameAudio) throws UnsupportedAudioFileException, IOException{
        int seconds = 3;
        SoundRecorder sr = new SoundRecorder(nameAudio,seconds);
        sr.voiceRecorder();        
        File soundFile = new File(nameAudio+".wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);                                    
        AudioPreProcessor ap = new AudioPreProcessor(ais);   
        MFCC mfcc = new MFCC(11025.0f);                
        Vector<double[]> coefficients = mfcc.process(ap);            
        System.out.println("size = "+coefficients.size());
        System.out.println("coefficients : ");
        for (int t = 0; t < coefficients.size(); t++) {
            for (int i = 0; i < coefficients.get(t).length; i++) {        
                System.out.print(coefficients.get(t)[i]+" ");                
            }            
            System.out.println("");
        }                                               
    }
    
    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, InterruptedException{
        
        Test.voiceRecorder("patternVoice");
        System.out.println("-----------------------------------------");
        Thread.sleep(3000);
        Test.voiceRecorder("testVoice");
        
//        TODO: Implement DTW for to compare coefficients 
        
        
    }
    
}
