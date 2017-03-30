/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.presentation;

import entity.Player;
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
   
    public String postPlayer() {
        
        String username = player.getUsername();
        String password = player.getPassword();
        
        if (this.playerFacade.login(username, password)) {
            loggedUsername = username;
            FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .addResponseCookie("username", username, null);
            
            return "theend";
        } else {
            return "incorrectLogin";
        }
        
        
//        this.playerFacade.create(player);
//        return "theend";
    }
    
    public String getLoggedUsername() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestCookieMap();
        
        return ((Cookie) requestCookieMap.get("username")).getValue();
    }
    
    public String logout(){
        this.playerFacade.logout(this.getLoggedUsername());
        return "theend";
    }
    
}
