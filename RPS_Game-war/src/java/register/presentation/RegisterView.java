/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register.presentation;

import entity.Player;
import java.util.ArrayList;
import java.util.List;
import login.PlayerFacade;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author kowrishankar
 */
@ManagedBean(name = "RegisterView")
@RequestScoped
public class RegisterView {

    
    @EJB
    private PlayerFacade playerFacade;
    
    Player player;
    
    /**
     * Creates a new instance of RegisterView
     */
    public RegisterView() {
        this.player = new Player();
    }
    
    
    public Player getPlayer() {
        return this.player;
    }
    
    public int getNumberOfPlayers() {
        return PlayerFacade.getNumberOfUsersOnline();
    }
    
    
    public String postPlayerRegister() {
        String username = player.getUsername();
        String password = player.getPassword();
        
        if (this.playerFacade.registerUser(username, password)) {
            return "theend";
        } else {
            return "incorrectLogin";
        }
    }
}
