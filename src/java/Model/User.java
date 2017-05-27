/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Edwin
 */

@ManagedBean
@ViewScoped
public class User{
    
    private String name;
    private int document;
    private String career;
    private String message;   
    private String authenticationMessage;   

    public String getAuthenticationMessage() {
        return authenticationMessage;
    }

    public void setAuthenticationMessage(String authenticationMessage) {
        this.authenticationMessage = authenticationMessage;
    }
    
    public User(){
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void createAccount(){
        try{
        File data = new File(getName().toLowerCase()+".txt");        
        FileWriter profile = new FileWriter(data,true);
        profile.write(getName()+"\n");
        profile.write(getDocument()+"\n");
        profile.write(getCareer());
        profile.close();
        }catch(IOException io){
            setMessage("No se pudo crear cuenta!");    
        }
        setMessage("Su cuenta fue creada satisfactoriamente!");
    }
    
    public void recordPatternVoice() throws UnsupportedAudioFileException, IOException{                
        new SoundRecorder(getName()).voiceRecorder();                
        //si todo salio bien enviar un mensaje al usuario diciendo que se grabo bien!
    }
    
    public void recordTestVoice() throws UnsupportedAudioFileException, IOException{
            new SoundRecorder("TestVoice").voiceRecorder();                
            VoiceProcessing.compareVoices();
            File archivo = new File (VoiceProcessing.name+".txt");
            FileReader fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String name = br.readLine();
            String document = br.readLine();
            String career = br.readLine();
            setAuthenticationMessage("Hemos reconocido tu voz, puedes ingresar!");            
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.getSessionMap().put("name", name);
            ec.getSessionMap().put("document", document);
            ec.getSessionMap().put("career", career);
            String url = ec.encodeActionURL(
            FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL
            (FacesContext.getCurrentInstance(),"/user_profile.xhtml"));
            ec.redirect(url);            
    }
    
}
