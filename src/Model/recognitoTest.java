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

            VoicePrint print = recognito.createVoicePrint("Elvis", new File("OldInterview2.wav"));
            VoicePrint prints = recognito.createVoicePrint("Carlos", new File("OldInterview.wav"));
            // handle persistence the way you want, e.g.:
            // myUser.setVocalPrint(print);
            // userDao.saveOrUpdate(myUser);
            // Now check if the King is back
            int seconds = 3;
            String nameAudio = "SomeFatGuy";
            SoundRecorder sr = new SoundRecorder(nameAudio,seconds);
            sr.voiceRecorder();
            List<MatchResult<String>> matches = recognito.identify(new File("SomeFatGuy.wav"));
            MatchResult<String> match = matches.get(0);

            if (match.getKey().equals("Elvis")) {
                System.out.println("Elvis is back !!! " + match.getLikelihoodRatio() + "% positive about it...");
            }
            
            if (match.getKey().equals("Carlos")) {
                System.out.println("Carlos is back !!! " + match.getLikelihoodRatio() + "% positive about it...");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR:"+e);
        }

    }

}
