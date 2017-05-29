
package Model;

import javax.sound.sampled.*;
import java.io.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Edwin
 */

@ManagedBean
@ViewScoped
public class SoundRecorder{
    
    private final int RECORD_TIME = 3;//in seconds    
    private File wavFile;
    private AudioFileFormat.Type fileType;    
    private TargetDataLine line;
 
    public SoundRecorder(){
    }
    
    public SoundRecorder(String name){
        wavFile = new File("voiceAuthentication/"+name+".wav");        
        fileType = AudioFileFormat.Type.WAVE;                
    }
    
    public AudioFormat getAudioFormat(){
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian);
        return format;
    }
     
    public void start(){
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
            System.out.println("Start capturing...");
            AudioInputStream ais = new AudioInputStream(line);                                                    
            System.out.println("Start recording...");                                      
            AudioSystem.write(ais, fileType, wavFile);                        
        }catch (LineUnavailableException ex){
            ex.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
     
    public void finish(){
        line.stop();
        line.close();
        System.out.println("Finished");
    }
 
    public void voiceRecorder(){                
        Thread stopper = new Thread(new Runnable(){
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME*1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                finish();
            }
        });
        stopper.start();
        this.start();        
    }          
    
}
 