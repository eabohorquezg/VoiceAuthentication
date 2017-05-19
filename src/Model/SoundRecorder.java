
package Model;

import javax.sound.sampled.*;
import java.io.*;

/**
 *
 * @author Edwin
 */

public class SoundRecorder{
    
    private int recordTime;//in seconds    
    private File wavFile;
    private AudioFileFormat.Type fileType;    
    private TargetDataLine line;
 
    public SoundRecorder(String name, int recordTime){
        wavFile = new File(name+".wav");
        this.recordTime = recordTime*1000;            
        fileType = AudioFileFormat.Type.WAVE;                
    }
            
    public int getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(int recordTime) {
        this.recordTime = recordTime;
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
        }catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }catch (IOException ioe) {
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
                    Thread.sleep(recordTime);
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
