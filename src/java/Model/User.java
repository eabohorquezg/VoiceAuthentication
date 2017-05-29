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
    private String document;
    private String career;
    private String voiceLogMessage;   
    private String authenticationMessage;
    private String accountCreationMessage;
    private String Script;

    public String getScript() {
        return Script;
    }

    public void setScript(String Script) {
        this.Script = Script;
    }
    
    public User(){
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }    
    
    public String getVoiceLogMessage() {
        return voiceLogMessage;
    }

    public void setVoiceLogMessage(String voiceLogMessage) {
        this.voiceLogMessage = voiceLogMessage;
    }
    
    public String getAuthenticationMessage() {
        return authenticationMessage;
    }

    public void setAuthenticationMessage(String authenticationMessage) {
        this.authenticationMessage = authenticationMessage;
    }
    
    public String getAccountCreationMessage() {
        return accountCreationMessage;
    }

    public void setAccountCreationMessage(String accountCreationMessage) {
        this.accountCreationMessage = accountCreationMessage;
    }
    
    public void createAccount(){
        Script = "";
        try{
            File data = new File("voiceAuthentication/"+getName().toLowerCase()+".txt");  
            System.out.println("PATH_-------------------------->");
            System.out.println(data.getAbsolutePath()); 
            FileWriter profile = new FileWriter(data);
            profile.write(getName()+"\n");
            profile.write(getDocument()+"\n");
            profile.write(getCareer());
            profile.close();
        }catch(IOException io){
            setAccountCreationMessage("No se pudo crear cuenta!");    
        }        
        emptyMessage();
        setAccountCreationMessage("Su cuenta fue creada satisfactoriamente!");
    }
    
    public void recordPatternVoice(){   
        Script = "";
        try{
            new SoundRecorder(getName()).voiceRecorder();
            
            //createAccount();
        }catch(Exception e){
            setVoiceLogMessage("No se pudo registrar su voz");
            //setAccountCreationMessage("No se pudo crear cuenta!");
        }        
        setVoiceLogMessage("Su voz fue registrada satisfactoriamente!");
        //setAccountCreationMessage("Su cuenta fue creada satisfactoriamente!");
    }
    
    public void emptyMessage(){
        authenticationMessage = "";
        setAccountCreationMessage("");
        setAuthenticationMessage("");
        setCareer("");
        setDocument("");
        setName("");
        setVoiceLogMessage("");
    }
    
    public void processAuthenticationVoice() throws UnsupportedAudioFileException, IOException{
            Script = "";
            File miDir = new File (".");            
            System.out.println(miDir.getCanonicalPath()); 
            new SoundRecorder("TestVoice").voiceRecorder();
            
            if ( VoiceProcessing.compareVoices() ){ //si las voces son del mismo hablante
                setAuthenticationMessage("Hemos reconocido tu voz, puedes ingresar!");            
                File userData = new File("voiceAuthentication/"+VoiceProcessing.speakerIdentified+".txt");
                FileReader fr = new FileReader(userData);
                BufferedReader br = new BufferedReader(fr);
                String name = br.readLine();
                String document = br.readLine();
                String career = br.readLine();            
                showUserProfile(name,document,career);            
            }else{
                setAuthenticationMessage("ACESSO DENEGADO"); 
                Script =  "$.jGrowl('"+getAuthenticationMessage()+"',{ life : 900})";
            }
            emptyMessage();
            
    }
    
    public void showUserProfile(String name, String document, String career) throws IOException{
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
