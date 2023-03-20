package nl.tudelft.jpacman.npc.ui;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.ui.CutScense;
import nl.tudelft.jpacman.ui.PacManUI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CutScenseTest {

    private CutScense cutScense;
    private PacManUI pacManUI;
    private Launcher launcher;
    @BeforeEach
    public void setUp() {

        launcher = new Launcher();
        launcher.launch();
    }

    @AfterEach
    void tearDown() {
        launcher.dispose();
    }

    @Test
    public void testSkipCutScenseToSkyMap() {
        launcher.getPacManUItest().getmain_ui().getHardBtn().doClick();
        launcher.getPacManUItest().getmain_ui().getButtonPlay().doClick();
        launcher.delay(1000);
        JPanel p1 = (JPanel) launcher.getPacManUItest().getContentPanel().getComponent(0);
        System.out.println(p1.getName());
        assertThat(p1.getName()).isEqualTo("src/main/resources/CutScense/sky2Cut.png");
    }

    @Test
    public void testSkipCutScenseToForrestMap() {
        launcher.getPacManUItest().getmain_ui().getHardBtn().doClick();
        launcher.getPacManUItest().getmain_ui().getButtonPlay().doClick();
        launcher.won();
        launcher.delay(1000);
        JPanel p1 = (JPanel) launcher.getPacManUItest().getContentPanel().getComponent(0);
        System.out.println(p1.getName());
        assertThat(p1.getName()).isEqualTo("src/main/resources/CutScense/forrestCut.png");
    }

    @Test
    public void testSkipCutScenseToCaveMap() {
        launcher.getPacManUItest().getmain_ui().getHardBtn().doClick();
        launcher.getPacManUItest().getmain_ui().getButtonPlay().doClick();
        launcher.won();
        launcher.won();
        launcher.delay(1000);
        JPanel p1 = (JPanel) launcher.getPacManUItest().getContentPanel().getComponent(0);
        System.out.println(p1.getName());
        assertThat(p1.getName()).isEqualTo("src/main/resources/CutScense/caveCut.png");
    }

    @Test
    public void testSkipCutScenseToIceMap() {
        launcher.getPacManUItest().getmain_ui().getHardBtn().doClick();
        launcher.getPacManUItest().getmain_ui().getButtonPlay().doClick();
        launcher.won();
        launcher.won();
        launcher.won();
        launcher.delay(1000);
        JPanel p1 = (JPanel) launcher.getPacManUItest().getContentPanel().getComponent(0);
        System.out.println(p1.getName());
        assertThat(p1.getName()).isEqualTo("src/main/resources/CutScense/iceCut.png");
    }

    @Test
    public void testSkipCutScenseToLavaMap() {
        launcher.getPacManUItest().getmain_ui().getHardBtn().doClick();
        launcher.getPacManUItest().getmain_ui().getButtonPlay().doClick();
        launcher.won();
        launcher.won();
        launcher.won();
        launcher.won();
        launcher.delay(1000);
        JPanel p1 = (JPanel) launcher.getPacManUItest().getContentPanel().getComponent(0);
        System.out.println(p1.getName());
        assertThat(p1.getName()).isEqualTo("src/main/resources/CutScense/lavaCut.png");
    }
}
