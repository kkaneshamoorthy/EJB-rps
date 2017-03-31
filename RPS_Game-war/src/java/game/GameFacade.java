/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import entity.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.GameDetails;

/**
 *
 * @author kowrishankar
 */
@Stateless
public class GameFacade extends AbstractFacade<Game> {

    @PersistenceContext(unitName = "RPS_Game-warPU2")
    private EntityManager em;
    
    ArrayList<String> waitingToPlay;
    HashMap<String, GameDetails> userGameMap;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GameFacade() {
        super(Game.class);
        this.waitingToPlay = new ArrayList<String>();
        this.userGameMap = new HashMap<String, GameDetails>();
    }
    
    public GameDetails getGame(String username) {
        for (String user : this.userGameMap.keySet()) {
            GameDetails game = this.userGameMap.get(user);
            
            if (game.getPlayerA().equals(username)  || game.getPlayerB().equals(username)) {
                return game;
            }
        } 
        
        return null;
    }
    
    public boolean startGame() {
        Map<String, Object> map = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
       
       String playerA = (String) map.get("username");
       String playerB = "";
       
       if (this.waitingToPlay.size() > 0) {
           playerB = this.waitingToPlay.get(0);
           this.waitingToPlay.remove(0);
           
           GameDetails game = new GameDetails(playerA, playerB);
           
           this.userGameMap.put(playerA, game);
           return true;
       } else {
           this.waitingToPlay.add(playerA);
           return false;
       }
    }
    
    public boolean gameStarted(String username) {
        for (String user : this.userGameMap.keySet()) {
            GameDetails game = this.userGameMap.get(user);
            
            if (game.getPlayerA().equals(username)  || game.getPlayerB().equals(username)) {
                return true;
            }
        } 
        
        return false;
    }
    
    public String getOtherPlayer(String username) {
        GameDetails game = this.getGame(username);
        
        if (game != null) {
            if (game.getPlayerA().equals(username))
                return game.getPlayerB();
            else
                return game.getPlayerA();
        } else {
            return "";
        }
    }
    
    public boolean hasBothPlayerChosen(String username) {
        GameDetails game = this.getGame(username);
        
        return game.hasBothPlayersChosen();
    }
    
    public String getWinner(Game game, GameDetails gameData) {
        String playerAOption = gameData.getPlayerAOption();
        String playerBOption = gameData.getPlayerBOption();
        
        if (playerAOption.equals(playerBOption)) {
            game.setDraw(true);
        } else if (playerAOption.equals("Scissors") && playerBOption.equals("Paper")) {
            game.setWinner(game.getPlayerA());
        } else if (playerBOption.equals("Scissors") && playerAOption.equals("Paper")) {
            game.setWinner(game.getPlayerB());
        } else if (playerAOption.equals("Paper") && playerBOption.equals("Rock")) {
            game.setWinner(game.getPlayerA());
        } else if (playerBOption.equals("Paper") && playerAOption.equals("Rock")) {
            game.setWinner(game.getPlayerB());
        } else if (playerAOption.equals("Sicssors") && playerBOption.equals("Rock")) {
            game.setWinner(game.getPlayerB());
        } else if (playerBOption.equals("Sicssors") && playerAOption.equals("Rock")) {
            game.setWinner(game.getPlayerA());
        } else if (playerAOption.equals("Sic") && playerBOption.equals("Rock")) {
            game.setWinner(game.getPlayerA());
        } else if (playerBOption.equals("Paper") && playerAOption.equals("Rock")) {
            game.setWinner(playerBOption);
        }
        
        return game.getWinner();
    }
    
    public void endGame(Game game) {
        this.userGameMap.remove(game.getPlayerA());
        this.userGameMap.remove(game.getPlayerB());
        
        this.create(game);
    }
}
