/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import entity.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kowrishankar
 */
@Stateless
public class PlayerFacade extends AbstractFacade<Player> {

    @PersistenceContext(unitName = "RPS_Game-warPU")
    private EntityManager em;
    
    private static ArrayList<String> usersOnline;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlayerFacade() {
        super(Player.class);
        usersOnline = new ArrayList<String>();
    }
    
    public boolean login(String username, String password) {
        
        if (usersOnline.contains(username)) {
            return true;
        }
        
        List<Player> playerLS = this.findAll();
        
        for (Player playerDB : playerLS) {
            if (playerDB.getPassword().equals(password) && playerDB.getUsername().equals(username)) {
                usersOnline.add(username);
                return true;
            }
        }
        
        return false;
    }
    
    public static int getNumberOfUsersOnline() {
        return usersOnline.size();
    }
    
    public static List<String> getOnlineUsers() {
        return usersOnline;
    }
    
    public static void removeOnlineUser(String userToRemove) {
        if (usersOnline.contains(userToRemove)) {
            usersOnline.remove(userToRemove);
        }
    }
    
    public void logout(String username) {
        PlayerFacade.removeOnlineUser(username);
    }
    
    public boolean registerUser(String username, String password) {
        List<Player> ls = this.findAll();
        
        //checks if username already exists
        for (Player player : ls) {
            if (player.getUsername().equals(username))
                return false;
        }
        
        Player player = new Player();
        player.setUsername(username);
        player.setPassword(password);
        player.setNumberOfDraws("0");
        player.setNumberOfLoss("0");
        player.setNumberOfWins("0");
        
        this.create(player);
        
        return true;
    }
    
    private Player getPlayer(String username) {
        List<Player> ls = this.findAll();
        
        for (Player player : ls) {
            if (player.getUsername().equals(username))
                return player;
        }
        
        return null;
    }
    
    public String getNumberOfWins(String username) {
        Player player = this.getPlayer(username);
        
        return player.getNumberOfWins();
    }
    
    public String getNumberOfLoss(String username) {
        Player player = this.getPlayer(username);
        
        return player.getNumberOfLoss();
    }
    
    public String getNumberOfDraws(String username) {
        Player player = this.getPlayer(username);
        
        return player.getNumberOfDraws();
    }
    
    public List<String> getLeaderBoard() {
        SortedMap<String, Player> ls = new TreeMap<>();
        for (Player player : this.findAll()) {
            ls.put(Integer.parseInt(player.getNumberOfWins())+player.getUsername(), player);
        }
        
        List<String> rank = new ArrayList<String>();
        
        for (String username : ls.keySet()) {
            rank.add(ls.get(username).getUsername()+" "+ls.get(username).getNumberOfWins());
            
        }
        Collections.reverse(rank);
        
        return rank.subList(0, 10);
    }
}
