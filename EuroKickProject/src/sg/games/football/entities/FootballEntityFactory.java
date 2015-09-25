/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.entities;

import sg.games.football.gameplay.PlayerRole;
import sg.games.football.gameplay.SoccerTeam;
import sg.games.football.gameplay.info.FootballPlayerInfo;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballEntityFactory {

    public PlayerBase createPlayer(SoccerTeam team, PlayerRole role) {
        FootballPlayerInfo info = new FootballPlayerInfo(null, null, role);
        if (role.equals(PlayerRole.GoalKeeper)) {
            return new GoalKeeper(team, info);

        } else {
            return new FieldPlayer(team, role, info);
        }
    }
}
