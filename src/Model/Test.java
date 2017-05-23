
package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author Edwin
 */

public class Test{
       
    public static double[] voiceRecorder(String nameAudio) throws UnsupportedAudioFileException, IOException{
        int seconds = 3;
        SoundRecorder sr = new SoundRecorder(nameAudio,seconds);
        sr.voiceRecorder();        
        File soundFile = new File(nameAudio+".wav");
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
    
    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, InterruptedException{
        JOptionPane.showMessageDialog (null, "Registre su voz", "Mensaje del sistema", JOptionPane.INFORMATION_MESSAGE);
        double[] coefficientsPatternVoice = Test.voiceRecorder("OldInterview");
        double[] coefficientsPatternVoices = Test.voiceRecorder("OldInterview2");
        JOptionPane.showMessageDialog (null, "Autentiquese", "Mensaje del sistema", JOptionPane.INFORMATION_MESSAGE);
        double[] coefficientsTestVoice = Test.voiceRecorder("SomeFatGuy");
        DTW dtw = new DTW(coefficientsPatternVoice, coefficientsTestVoice);                                                                                              
        System.out.println("distance = "+dtw.warpingDistance);
        if( dtw.warpingDistance <= 80 ){
            JOptionPane.showMessageDialog (null, "ACCESO CONCEDIDO", "Mensaje de sistema", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog (null, "ACCESO DENEGADO", "Mensaje de sistema", JOptionPane.ERROR_MESSAGE);
        }                                                                                                                
        System.exit(0);
    }                            
    
}
