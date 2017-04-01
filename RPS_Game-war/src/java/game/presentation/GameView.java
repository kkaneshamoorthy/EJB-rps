/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.presentation;

import entity.Game;
import entity.Player;
import game.GameFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import login.presentation.LoginView;
import util.GameDetails;

/**
 *
 * @author kowrishankar
 */
@ManagedBean(name = "GameView")
@RequestScoped
public class GameView implements Serializable {
    @EJB
    private GameFacade gameFacade;
       
    private String selectedOption;
    
    private String message;
    
    private boolean displayGame;

    public boolean isDisplayGame() {
        return displayGame;
    }

    public void setDisplayGame(boolean displayGame) {
        this.displayGame = displayGame;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
        
    /**
     * Creates a new instance of GameView
     */
    public GameView() {
        
    }
    
    public String startGame() throws IOException {
        
        Map<String, Object> map = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
        
        String username = (String) map.get("username");
        
        if (username == null) {
            this.message = "You must log in to play game";
            return "game";
        }
        
        if (this.gameFacade.gameStarted(username)) {
            this.message = "Game already started";
            return "";
        }
        
        boolean started = this.gameFacade.startGame();
       
        if (started) {
            this.displayGame = true;
            this.message = "Game started. You are connected to: "+ this.gameFacade.getOtherPlayer(username);
            return "game";
        } else {
           this.message = "There is currently no player to play with. Waiting for a player to join... Game will start when a player joins";
           this.displayGame = true;
           return "game";
        }
    }
    
    public String submitOption() {
        Map<String, Object> map = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
        
        String username = (String) map.get("username");
        boolean hasGameStarted = this.gameFacade.gameStarted(username);
        
        if (hasGameStarted) {
            GameDetails gameData = this.gameFacade.getGame(username);
            this.message = "You selected:"+this.selectedOption+". Waiting for other player to select";
            
            if (gameData.getPlayerA().equals(username)) {
                gameData.setPlayerAOption(this.selectedOption);
            } else {
                gameData.setPlayerBOption(this.selectedOption);
            }
            
            if (this.gameFacade.hasBothPlayerChosen(username)) {
                Game game = new Game();
                game.setPlayerA(gameData.getPlayerA());
                game.setPlayerB(gameData.getPlayerB());
                
                String winner = this.gameFacade.getWinner(game, gameData);
                
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                LoginView loginView = (LoginView) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "LoginView");
                Player playerA = loginView.getPlayer(game.getPlayerA());
                Player playerB = loginView.getPlayer(game.getPlayerB());
                
                if (winner == null) { 
                    this.message = "The winner is: Both";
                    
                    int scoreA = Integer.parseInt(playerA.getNumberOfDraws())+1;
                    playerA.setNumberOfDraws(scoreA+"");
                    
                    int scoreB = Integer.parseInt(playerB.getNumberOfDraws())+1;
                    playerB.setNumberOfDraws(scoreB+"");
                } else {
                    this.message = "The winner is: "+winner;

                    if (winner.equals(playerA.getUsername())) {
                        int scoreA = Integer.parseInt(playerA.getNumberOfWins())+1;
                        playerA.setNumberOfWins(scoreA+"");

                        int scoreB = Integer.parseInt(playerB.getNumberOfLoss())+1;
                        playerA.setNumberOfLoss(scoreB+"");
                    } else {
                        int scoreB = Integer.parseInt(playerB.getNumberOfWins())+1;
                        playerB.setNumberOfWins(scoreB+"");

                        int scoreA = Integer.parseInt(playerA.getNumberOfLoss())+1;
                        playerA.setNumberOfLoss(scoreA+"");
                    }
                }
                
                this.gameFacade.endGame(game);
                loginView.editPlayer(playerA);
                loginView.editPlayer(playerB);
        
                return "game";
            } else {
                
            }
            
        } else {
            this.message = "No player joined your game. Wait for other player to join and press Start Game...";
        }
        return "game";
    }
    
    
}
