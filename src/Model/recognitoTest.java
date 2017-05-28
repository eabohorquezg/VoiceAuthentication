/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.bitsinharmony.recognito.MatchResult;
import com.bitsinharmony.recognito.Recognito;
import com.bitsinharmony.recognito.VoicePrint;
import java.io.File;
import java.util.List;

/**
 *
 * @author AndresL
 */
public class recognitoTest {

    public static void main(String[] args) {

        try {
            Recognito<String> recognito = new Recognito<>(16000.0f);
            int seconds = 3;
//            String nameAudio = "tr4";
//            SoundRecorder sr = new SoundRecorder(nameAudio,seconds);
//            sr.voiceRecorder();
//            
//            nameAudio = "te4";
//            SoundRecorder sr1 = new SoundRecorder(nameAudio,seconds);
//            sr1.voiceRecorder();
            
            //VoicePrint printEmpty = recognito.createVoicePrint("Empty", new File("empty.wav"));
            VoicePrint s1 = recognito.createVoicePrint("tr1", new File("train/tr1.wav"));
            VoicePrint s2 = recognito.createVoicePrint("tr2", new File("train/tr2.wav"));
            VoicePrint s3 = recognito.createVoicePrint("tr3", new File("train/tr3.wav"));
            VoicePrint s4 = recognito.createVoicePrint("tr4", new File("train/tr4.wav"));
            VoicePrint s5 = recognito.createVoicePrint("tr5", new File("train/patternVoice.wav"));
////            VoicePrint s5 = recognito.createVoicePrint("s5", new File("train/s5.wav"));
////            VoicePrint s6 = recognito.createVoicePrint("s6", new File("train/s6.wav"));
////            VoicePrint s7 = recognito.createVoicePrint("s7", new File("train/s7.wav"));
////            VoicePrint s8 = recognito.createVoicePrint("s8", new File("train/s8.wav"));
            
            
            for (int i = 5; i >=1 ; i--){
                List<MatchResult<String>> matches = recognito.identify(new File("test/te"+Integer.toString(i)+".wav"));
                MatchResult<String> match = matches.get(0);
                System.out.println("s"+i+"-"+match.getKey());
                System.out.println("is back !!! " + match.getLikelihoodRatio() + "% positive about it...");              
            }
            
            
            //VoicePrint print = recognito.createVoicePrint("A Felipe", new File("OldInterview2.wav"));
            //VoicePrint prints = recognito.createVoicePrint("F Andrés", new File("OldInterview.wav"));
            // handle persistence the way you want, e.g.:
            // myUser.setVocalPrint(print);
            // userDao.saveOrUpdate(myUser);
            // Now check if the King is 
            
//            nameAudio = "SomeFatGuy";
//            SoundRecorder sr1 = new SoundRecorder(nameAudio,seconds);
//            sr1.voiceRecorder();
//                                    
//            List<MatchResult<String>> matches = recognito.identify(new File("SomeFatGuy.wav"));
//            MatchResult<String> match = matches.get(0);
//            
//            System.out.println(match.getKey());
//            System.out.println("is back !!! " + match.getLikelihoodRatio() + "% positive about it...");
//            
//            if (match.getKey().equals("A Felipe")) {
//                System.out.println("A Felipe is back !!! " + match.getLikelihoodRatio() + "% positive about it...");
//            }
//            
//            if (match.getKey().equals("F Andrés")) {
//                System.out.println("F Andrés is back !!! " + match.getLikelihoodRatio() + "% positive about it...");
//            }
//            
        } catch (Exception e) {
            System.out.println("ERROR:"+e);
        }

    }

}
