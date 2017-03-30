/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.presentation;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author kowrishankar
 */
@Named(value = "GameView")
@ConversationScoped
public class GameView implements Serializable {

    private String rock;
    private String paper;
    private String scissors;
    
    private String selectedOption;
    
    ArrayList<String> waitingToPlay;
        
    /**
     * Creates a new instance of GameView
     */
    public GameView() {
        this.waitingToPlay = new ArrayList<String>();
    }
    
    public String getRock() {
        return this.rock;
    }
    
    public String getPaper() {
        return this.paper;
    }
    
    public String getScissors() {
        return this.scissors;
    }
    
    
    public void getStartGame() {
        Map<String, Object> map = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
        
        this.waitingToPlay.add(((String) map.get("username")));
    }
    
}
