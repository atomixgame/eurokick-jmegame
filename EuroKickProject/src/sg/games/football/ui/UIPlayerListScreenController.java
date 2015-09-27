package sg.games.football.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import sg.games.football.ui.model.EmailMessageModel;
import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import sg.games.football.gameplay.FootballGamePlayManager;
import sg.games.football.gameplay.info.FootballPlayerInfo;
import sg.games.football.managers.FootballGameGUIManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UIPlayerListScreenController extends BaseScreenController {

    private ArrayList selectedList;

    @Inject
    public UIPlayerListScreenController(FootballGameGUIManager gameGUIManager) {
        super(gameGUIManager);
    }

    public void bind(Nifty nifty, Screen screen) {
        if (screen.getScreenId().equals("PlayerListScreen")) {
            selectedList = new ArrayList();
            fillPlayersTable(screen);
        }
    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Fill the listbox with items. In this case with Strings.
     */
    public void fillPlayersTable(Screen screen) {
        ListBox listBox = screen.findNiftyControl("player-table", ListBox.class);
        FootballGamePlayManager gamePlayManager = FootballGamePlayManager.getDefault();

        for (FootballPlayerInfo player : getPlayerList()) {
            listBox.addItem(player);
            System.out.println(" Add " + player.toString());
        }
    }

    public ArrayList<FootballPlayerInfo> getPlayerList() {
        FootballGamePlayManager gamePlayManager = FootballGamePlayManager.getDefault();
        return gamePlayManager.getLeague().getParticipants().get(0).getPlayersList();
    }

    public ArrayList getSelectedList() {
        return selectedList;
    }

    /**
     * When the selection of the ListBox changes this method is called.
     */
    @NiftyEventSubscriber(id = "player-table")
    public void onPlayerListSelectionChanged(final String id, final ListBoxSelectionChangedEvent<EmailMessageModel> event) {
        List<EmailMessageModel> selection = event.getSelection();
        selectedList.clear();
        selectedList.addAll(selection);
        System.out.println(" Selection changed");
        /*
         for (EmailMessageModel m : selection) {
         if (!selectedList.contains(m)) {
         this.selectedList.add(m);
         }
         }
         */
    }
}
