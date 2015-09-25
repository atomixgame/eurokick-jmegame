package sg.games.football.gameplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sg.games.football.gameplay.info.FootballClubInfo;
import sg.games.football.gameplay.info.FootballPlayerInfo;

/**
 * @author cuong.nguyenmanh2
 */
public class League {

    String orgName;
    String orgLogo;
    String leagueName;

    List<FootballClubInfo> participants;
    // Season time
    int seasonNum = 30;
    private FootballSeason season;

    League(String leagueName) {
        this.leagueName = leagueName;
        this.participants = new ArrayList<FootballClubInfo>();
    }

    public void init() {
        scheduleSeason();
    }

    public void fillRandomClub() {
        for (int i = 0; i < 10; i++) {
            FootballClubInfo newClub = new FootballClubInfo("Club " + i);
            newClub.fillPlayerList();
            //newClub.hireACoach();
            participants.add(newClub);
        }
    }

    public void scheduleSeason() {
        this.season = new FootballSeason(this);
        createSchedule(participants);
    }

    /**
     * Returns the match list (schedule)
     */
//    public List<FootballMatch> getListOfMatches(FootballClub club){
//        def l=[]
//        rounds.each{round->
//            l<<round.matches.find{match->(match.clubA==club || match.clubB==club)}
//        }
//        return l;
//    }
//    
//    public List<FootballMatch> getRoundMatch(int roundIndex){
//        return rounds[roundIndex].matches
//    }
    public void updateRankTable() {

    }

    /**
     * Creates the entire schedule
     *
     * @param participants List of participants for whom to create the schedule.
     */
    private void createSchedule(List<FootballClubInfo> participants) {
        if (participants.size() % 2 == 1) {
            // Number of teams uneven ->  add the bye team.
        }
        for (int i = 1; i < participants.size(); i++) {
//            createOneRound(i, participants);
        }
    }

//    public List<FootballMatch> ifJoin(FootballClubInfo club) {
//
//    }
//    public registerJoin(FootballClubInfo club) {
//
//    }
    public String getLeagueName() {
        return leagueName;
    }

    public List<FootballClubInfo> getParticipants() {
        return participants;
    }

}
