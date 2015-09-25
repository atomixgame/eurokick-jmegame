/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.gameplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sg.games.football.gameplay.info.FootballPlayerInfo;

/**
 *
 * @author cuongnguyen
 */
public class FootballSeason {

    int currentRoundNum = 1;
    Date startTime;
    Date endTime; // of the whole season
    List<Round> rounds;
    private final League league;

    FootballSeason(League league) {
        this.league = league;
        this.rounds = new ArrayList<Round>();
    }

    public Date getStartTime() {
        return startTime;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Date getEndTime() {
        return endTime;
    }

    public FootballPlayerInfo getBestPlayer() {
        return null;
    }
}
