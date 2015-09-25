package sg.games.football.gameplay;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballStragegy {

    public String name;
    public String posTitle;
    public String description;

    public FootballStragegy(String name, String posTitle) {
        this.name = name;
        this.posTitle = posTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosTitle() {
        return posTitle;
    }

    public void setPosTitle(String posTitle) {
        this.posTitle = posTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
