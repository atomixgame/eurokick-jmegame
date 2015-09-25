/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.gameplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cuongnguyen
 */
public class Round {

    int num;
    Date startTime;

    Round(int num) {
        this.num = num;
    }
    /**
     * The schedule list.
     */
    List<FootballMatch> matches = new ArrayList<FootballMatch>();

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<FootballMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<FootballMatch> matches) {
        this.matches = matches;
    }

}
