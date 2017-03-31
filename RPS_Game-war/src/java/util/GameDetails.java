/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Game;

public class GameDetails {
    private String playerA;
    private String playerB;
    private String playerAOption;
    private String playerBOption;
    private String winner;
    private boolean draw;
    
    public GameDetails(String pA, String pB) {
        this.playerA = pA;
        this.playerB = pB;
    }

    public String getPlayerAOption() {
        return playerAOption;
    }

    public void setPlayerAOption(String playerAOption) {
        this.playerAOption = playerAOption;
    }

    public String getPlayerBOption() {
        return playerBOption;
    }

    public void setPlayerBOption(String playerBOption) {
        this.playerBOption = playerBOption;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getPlayerA() {
        return playerA;
    }

    public void setPlayerA(String playerA) {
        this.playerA = playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public void setPlayerB(String playerB) {
        this.playerB = playerB;
    }
    
    public boolean hasBothPlayersChosen() {
        return this.playerAOption != null && this.playerBOption != null;
    }
}
