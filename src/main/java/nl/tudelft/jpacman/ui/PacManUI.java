package nl.tudelft.jpacman.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;

import static nl.tudelft.jpacman.ui.GameUI.level;
import static nl.tudelft.jpacman.ui.GameUI.themeNnm;

/**
 * The default JPacMan UI frame. The PacManUI consists of the following
 * elements:
 *
 * <ul>
 * <li>A score panel at the top, displaying the score of the player(s).
 * <li>A board panel, displaying the current level, i.e. the board and all units
 * on it.
 * <li>A button panel, containing all buttons provided upon creation.
 * </ul>
 *
 * @author Jeroen Roosen
 *
 */
public class PacManUI extends JFrame {
    private Launcher l;
    /**
     * Default serialisation UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The desired frame rate interval for the graphics in milliseconds, 40
     * being 25 fps.
     */
    private static final int FRAME_INTERVAL = 40;

    /**
     * The panel displaying the player scores.
     */
    private ScorePanel scorePanel;

    /**
     * The panel displaying the game.
     */
    private BoardPanel boardPanel;
    /**
     *contentPanel
     * */
    Container contentPanel = getContentPane();
    /**
     * scoreFormatter
     *
     * */
    private ScoreFormatter scoreFormatter;

    /**
     * Creates a new UI for a JPacman game.
     *
     * @param game
     *            The game to play.
     * @param buttons
     *            The map of caption-to-action entries that will appear as
     *            buttons on the interface.
     * @param keyMappings
     *            The map of keyCode-to-action entries that will be added as key
     *            listeners to the interface.
     * @param scoreFormatter
     *            The formatter used to display the current score.
     */

    private final PacKeyListener keys;
    private JPanel buttonPanel;
    private Map<String, Action> buttonkey;
    private final Main_UI main_ui = new Main_UI(this);

    private List<Color> colors = new ArrayList<Color>();
    public PacManUI(final Game game, final Map<String, Action> buttons,
                    final Map<Integer, Action> keyMappings,
                    ScoreFormatter scoreFormatter,String nameFileBG) {
        super("JPacman EiEi");
        assert game != null;
        assert buttons != null;
        assert keyMappings != null;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        keys = new PacKeyListener(keyMappings);
        addKeyListener(keys);
        buttonkey = buttons;
        buttonPanel= new ButtonPanel(buttons, this);
        if (scoreFormatter != null) {
            scorePanel.setScoreFormatter(scoreFormatter);
        }
        scorePanel = new ScorePanel(game.getPlayers());
        contentPanel.setLayout(new BorderLayout());
        boardPanel = new BoardPanel(game,nameFileBG);
        contentPanel.add(main_ui.getMain_UI());
        pack();
        colors.add(new Color(135,206,250));
        colors.add(new Color(46,139,87));
        colors.add(new Color(105 ,105,105));
        colors.add(new Color(173,216,230));
        colors.add(new Color(255,99,71));
    }



    public void newMap(Game game,String nameFileBG){

        buttonPanel = new ButtonPanel(buttonkey, this);
        scorePanel = new ScorePanel(game.getPlayers());
        scorePanel.setBackground(colors.get(themeNnm));
        if (scoreFormatter != null) {
            scorePanel.setScoreFormatter(scoreFormatter);
        }
        boardPanel = new BoardPanel(game,nameFileBG);
        boardPanel.setName("theme: "+themeNnm+"\nlevel: "+level);
        buttonPanel.setName("Start Stop resource from index "+themeNnm);
    }

    /**
     * Starts the "engine", the thread that redraws the interface at set
     * intervals.
     */
    public void start() {
        setLocationRelativeTo(null);
        setVisible(true);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::nextFrame, 0, FRAME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     * Draws the next frame, i.e. refreshes the scores and game.
     */

    public void nextFrame() {
        boardPanel.repaint();
        scorePanel.refresh();
    }

    public Main_UI getmain_ui(){
        return  main_ui;
    }
    public String getNameBG(){return getBoardPanel().getBGName();}
    public JPanel getButtonPanel(){return buttonPanel;}
    public ScorePanel getScorePanel(){return scorePanel;}
    public BoardPanel getBoardPanel(){return this.boardPanel;}
    public void setl(Launcher l){
        this.l = l;
        main_ui.setLauncher(l);
    }

    public Container getContentPanel(){
        return contentPanel;
    }
}
