package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests the various methods provided by the {@link Navigation} class.
 *
 * @author Jeroen Roosen
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals"})
class NavigationTest {

    /**
     * Map parser used to construct boards.
     */
    private MapParser parser;

    /**
     * Set up the map parser.
     */

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        parser = new MapParser(levelFactory, new BoardFactory(sprites));
    }

    /**
     * Verifies that the path to the same square is empty.
     */
    @DisplayName("TC2701")
    @Test
    void testShortestPathEmpty() {
        Board b = parser.parseMap(Lists.newArrayList(" ")).getBoard();
        Square s1 = b.squareAt(0, 0);
        Square s2 = b.squareAt(0, 0);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).isEmpty();
    }

    /**
     * Verifies that if no path exists, the result is <code>null</code>.
     */
    @DisplayName("TC2801")
    @Test
    void testNoShortestPath() {
        Board b = parser
            .parseMap(Lists.newArrayList("#####", "# # #", "#####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(3, 1);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).isNull();
    }

    /**
     * Verifies that having no traveller ignores terrain.
     */
    @DisplayName("TC2901")
    @Test
    void testNoTraveller() {
        Board b = parser
            .parseMap(Lists.newArrayList("#####", "# # #", "#####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(3, 1);
        List<Direction> path = Navigation.shortestPath(s1, s2, null);
        assertThat(path).containsExactly(Direction.EAST, Direction.EAST);
    }

    /**
     * Tests if the algorithm can find a path in a straight line.
     */
    @DisplayName("TC3001")
    @Test
    void testSimplePath() {
        Board b = parser.parseMap(Lists.newArrayList("####", "#  #", "####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(2, 1);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).containsExactly(Direction.EAST);
    }


    /**
     * Verifies that the algorithm can find a path when it has to take corners.
     */
    @DisplayName("TC3101")
    @Test
    void testCornerPath() {
        Board b = parser.parseMap(
            Lists.newArrayList("####", "#  #", "## #", "####")).getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(2, 2);
        List<Direction> path = Navigation
            .shortestPath(s1, s2, mock(Unit.class));
        assertThat(path).containsExactly(Direction.EAST, Direction.SOUTH);
    }

    /**
     * Verifies that the nearest object is detected.
     */
    @DisplayName("TC3201")
    @Test
    void testNearestUnit() {
        Board b = parser
            .parseMap(Lists.newArrayList("#####", "# ..#", "#####"))
            .getBoard();
        Square s1 = b.squareAt(1, 1);
        Square s2 = b.squareAt(2, 1);
        Square result = Navigation.findNearest(Pellet.class, s1).getSquare();
        assertThat(result).isEqualTo(s2);
    }

    /**
     * Verifies that there is no such location if there is no nearest object.
     */
    @DisplayName("TC3301")
    @Test
    void testNoNearestUnit() {
        Board b = parser.parseMap(Lists.newArrayList(" ")).getBoard();
        Square s1 = b.squareAt(0, 0);
        Unit unit = Navigation.findNearest(Pellet.class, s1);
        assertThat(unit).isNull();
    }

    /**
     * Verifies that there is ghost on the default board
     * next to cell [1, 1].
     *
     * @throws IOException if board reading fails.
     */

    @DisplayName("TC3401")
    @Test
    void testFullSizedLevel() throws IOException {
        try (InputStream i = getClass().getResourceAsStream("/forest.txt")) {
            Board b = parser.parseMap(i).getBoard();
            Square s1 = b.squareAt(1, 1);
            Unit unit = Navigation.findNearest(Ghost.class, s1);
            assertThat(unit).isNotNull();
        }
    }
}
