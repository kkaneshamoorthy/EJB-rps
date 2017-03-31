/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.presentation;

import entity.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import login.PlayerFacade;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

/**
 *
 * @author kowrishankar
 */
@ManagedBean(name = "LoginView")
@RequestScoped
public class LoginView {

    @EJB
    private PlayerFacade playerFacade;
    
    Player player;
    
    String loggedUsername="";
    
    /**
     * Creates a new instance of LoginView
     */
    public LoginView() {
        this.player = new Player();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public int getNumberOfPlayers() {
        return PlayerFacade.getNumberOfUsersOnline();
    }
    
    public String getOnlineUsers() {
        StringBuilder sb = new StringBuilder();
        for (String username : PlayerFacade.getOnlineUsers()) {
            sb.append(username);
        }
        
        return sb.toString();
    }
    
    public String postPlayer() {
        
        String username = player.getUsername();
        String password = player.getPassword();
        
        if (this.playerFacade.login(username, password)) {
            loggedUsername = username;
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("username", username);
            
            return "index";
        } else {
            return "incorrectLogin";
        }
    }
    
    public String getLoggedUsername() {
        Map<String, Object> map = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
//        
        return ((String) map.get("username"));
    }
    
    public String logout() throws IOException{
        this.playerFacade.logout(this.getLoggedUsername());
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "theend";
    }
    
    public Player getPlayer(String username) {
        List<Player> ls = this.playerFacade.findAll();
        
        for (Player player : ls) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        
        return null;
    }
    
    public void editPlayer(Player player) {
        this.playerFacade.edit(player);
    }
    
}
