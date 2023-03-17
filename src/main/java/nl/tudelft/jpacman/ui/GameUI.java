package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.npc.ghost.Inky;
import nl.tudelft.jpacman.npc.ghost.Pinky;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameUI {

    private List<String> cutScenseName = Arrays.asList("/skyCut.png", "/forrestCut.png", "/caveCut.png","/iceCut.png","/lavaCut.png");
    /**
     * A list of lists representing the game board.
     * */
    private List<List<String>> board =  new ArrayList<List<String>>();
    /**
     *  A list of strings representing the wall sprites.
     * */
    private List<String> Wall = Arrays.asList("/sky2.png", "/Forest.png", "/Stone.png","/ice cave.png","/Lava.png");
    /**
     * A list of strings representing the pellet sprites.
     * */
    private List<String> Pellet = Arrays.asList("/featherpellet.png", "/gem for.png", "/gemStone.png","/gemice.png","/gemlava.png");
    /**
     *  A list of strings representing the background sprites.
     * */
    private List<String> BG = Arrays.asList("/BGsky.png", "/BGForest.png", "/BGcave.png","/BGice.png","/BGlava.png");
    /**
     *  A list of floating-point numbers representing the level multiplier for each level.
     * */
    private List<Float> lavelnum = new ArrayList<Float>();
    /**
     * An integer representing the current level.
     * */
    private int lavel;
    /**
     *An integer representing the current board number.
     * */
    private int BoardNnm;
    /**
     *An integer representing the current theme number.
     * */
    private int themeNnm;
    /**
     * GameUI()
     * */
    private CutScense cutScense;

    public GameUI(){
        BoardNnm = 0;
        themeNnm = 0;
        lavelnum.add(1.25F);
        lavelnum.add(1F);
        lavelnum.add(0.75F);
        lavel = 0;
        setlavelnpc(lavel);
        board.add(Arrays.asList("/skyboardEasy.txt", "/forestEasy.txt", "/caveboardEasy.txt","/iceboardEasy.txt","/lavaboardEasy.txt"));
        board.add(Arrays.asList("/skyboardNormal.txt", "/forestNormal.txt", "/caveboardNormal.txt","/iceboardNormal.txt","/lavaboardNormal.txt"));
        board.add(Arrays.asList("/skyboardHard.txt", "/forestHard.txt", "/caveboardHard.txt","/iceboardHard.txt","/lavaboardHard.txt"));
    }
    /**
     * A function that updates the current board number and
     * theme number if the game is won and advances to the next level or theme,
     * or resets the game if the last level is already completed.
     * */
    public void GemeWon(){
        if(themeNnm<4){
            BoardNnm ++;
            themeNnm ++;
        }else if (lavel!=2){
            GameReset();
            lavel ++;
        }
        else {
            GameReset();
            lavel = 0;
        }
        System.out.println("BoardNnm "+BoardNnm+"\nthemeNnm "+themeNnm);
        System.out.println("lavel "+lavel);
    }
    /**
     * A function that resets the current board and
     * theme numbers to their default values.
     * */
    public void GameReset(){
        BoardNnm = 0;
        themeNnm= 0;
    }
    /**
     *
     * A function that sets the current level to the specified number.
     * */
    public void setLavel(int num){
        lavel = num;
    }
    /**
     *
     * A function that returns the name of the current board.
     * */
    public String getBoardName(){
        return "/Board"+board.get(lavel).get(BoardNnm);
    }
    /**
     *
     * A function that returns the name of the current wall sprite.
     * */
    public String getWallName(){
        return "/sprite"+Wall.get(themeNnm);
    }
    /**
     *
     * : A function that returns the name of the current pellet sprite.
     * */
    public String getPelletName(){
        return "/sprite"+Pellet.get(themeNnm);
    }
    /**
     *A function that returns the name of the current background sprite.
     * */
    public String getBGName(){
        return "src/main/resources/sprite"+BG.get(themeNnm);
    }

    /**
     *A private function that sets the move interval
     *  for each ghost based on the current level.
     * */
    private void setlavelnpc(int num){
        Blinky.setMoveInterval((int) (Blinky.getMoveInterval()*lavelnum.get(num)));
        Clyde.setMoveInterval((int) (Clyde.getMoveInterval()*lavelnum.get(num)));
        Inky.setMoveInterval((int) (Inky.getMoveInterval()*lavelnum.get(num)));
        Pinky.setMoveInterval((int) (Pinky.getMoveInterval()*lavelnum.get(num)));
    }

    public void nextCutScense(PacManUI pacManUI){

        //cutScense.setCutscense("src/main/resources/CutScense/skyCut.png");

        cutScense = new CutScense(pacManUI, getCutScenseName());
        System.out.println(getCutScenseName());
        pacManUI.contentPanel.removeAll();
        pacManUI.contentPanel.add(cutScense.getCutscenseUI(), BorderLayout.CENTER);
        pacManUI.pack();


    }

    public String getCutScenseName(){
        return "src/main/resources/CutScense" + cutScenseName.get(themeNnm);
    }
}