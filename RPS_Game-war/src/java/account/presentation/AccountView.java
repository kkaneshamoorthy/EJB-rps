/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.presentation;

import entity.Player;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import login.PlayerFacade;
import login.presentation.LoginView;

/**
 *
 * @author kowrishankar
 */
@ManagedBean(name = "AccountView")
@javax.faces.bean.RequestScoped
public class AccountView {

    @EJB
    private PlayerFacade playerFacade;
    
    Player player;
    /**
     * Creates a new instance of AccountView
     */
    public AccountView() {
        this.player = new Player();
    }
    
    public String getNumberOfWins() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginView loginView = (LoginView) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "LoginView");
        
        String username = loginView.getLoggedUsername();
        
        if (username == null) {
            return "Login to view";
        }
        
        return this.playerFacade.getNumberOfWins(username);
    }
    
    public String getNumberOfLoss() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginView loginView = (LoginView) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "LoginView");
        
        String username = loginView.getLoggedUsername();
        if (username == null) {
            return "Login to view";
        }
        
        return this.playerFacade.getNumberOfLoss(username);
    }
    
    public String getNumberOfDraw() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginView loginView = (LoginView) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "LoginView");
        
        String username = loginView.getLoggedUsername();
        
        if (username == null) {
            return "Login to view";
        }
        
        return this.playerFacade.getNumberOfDraws(username);
    }
    
}
