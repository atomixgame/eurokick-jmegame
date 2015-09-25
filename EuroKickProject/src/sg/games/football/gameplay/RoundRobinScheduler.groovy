package sg.games.football.gameplay

import sg.games.football.gameplay.info.FootballClubInfo

/**
 *
 * @author cuongnguyen
 */
public class RoundRobinScheduler {
    /**
     * Creates one round, i.e. a set of matches where each team plays once.
     *
     * @param round Round number.
     * @param list List of teams
     */
    private void createOneRound(int roundIndex, ArrayList clubs) {
        int size = clubs.size();
        int mid = size / 2;
        // Split list into two

        def l1 = [],l2 = [],l3 =[],l4 =[],l5 =[]
        
        l4.addAll(clubs.subList(0, mid))
        l4.addAll(clubs.subList(mid, size).reverse()) //println l4
        l3.add(l4[0])
        l5 = l4.subList(1, size)
        Collections.rotate(l5, roundIndex)
        l3.addAll(l5)
        l1.addAll(l3.subList(0, mid))
        l2.addAll(l3.subList(mid, size))
        
        Round newRound = new Round(roundIndex);
        newRound.startTime = this.startTime + 7 * roundIndex(0..mid - 1).each
        {
            i
            -> FootballMatch newMatch = new FootballMatch(l1[i], l2[i]);
            newMatch.startTime = newRound.startTime + i
            newRound.matches << newMatch
            
        }
        rounds << newRound
    }

    public FootballMatch getNextMatch(FootballClubInfo club) {
        List roundMatches = getRoundMatch(currentRoundNum);
        return roundMatches.find
        {
            FootballMatch match -> (match.clubA == club || match.clubB == club )};
    }
}

