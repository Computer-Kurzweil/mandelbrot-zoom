package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing;



import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.TuringPhase.*;

public class TuringTuringPhaseStateMachineEnumTest {

    private TuringPhaseStateMachine turingPhaseStateMachine = new TuringPhaseStateMachine();

    public static Logger log = Logger.getLogger(TuringPositionsTest.class.getName());

    @Test
    public void startTest(){
        log.info("startTest start");
        turingPhaseStateMachine = new TuringPhaseStateMachine();
        assertEquals(turingPhaseStateMachine.getTuringPhase(),SEARCH_THE_SET);
        turingPhaseStateMachine.start();
        assertEquals(turingPhaseStateMachine.getTuringPhase(),SEARCH_THE_SET);
        log.info("startTest done");
    }

    @Test
    public void finishGoToSetTest(){
        log.info("finishGoToSetTest start");
        turingPhaseStateMachine = new TuringPhaseStateMachine();
        turingPhaseStateMachine.start();
        assertEquals(turingPhaseStateMachine.getTuringPhase(),SEARCH_THE_SET);
        turingPhaseStateMachine.finishSearchTheSet();
        assertEquals(turingPhaseStateMachine.getTuringPhase(),WALK_AROUND_THE_SET);
        log.info("finishGoToSetTest done");
    }

    @Test
    public void finishWalkAroundTest() {
        log.info("finishWalkAroundTest start");
        turingPhaseStateMachine = new TuringPhaseStateMachine();
        turingPhaseStateMachine.start();
        turingPhaseStateMachine.finishSearchTheSet();
        assertEquals(turingPhaseStateMachine.getTuringPhase(),WALK_AROUND_THE_SET);
        turingPhaseStateMachine.finishWalkAround();
        assertEquals(turingPhaseStateMachine.getTuringPhase(), FILL_THE_OUTSIDE_WITH_COLOR);
        log.info("finishWalkAroundTest done");
    }

    @Test
    public void finishFillTheOutsideWithColorsTest() {
        log.info("finishFillTheOutsideWithColorsTest start");
        turingPhaseStateMachine = new TuringPhaseStateMachine();
        turingPhaseStateMachine.start();
        turingPhaseStateMachine.finishSearchTheSet();
        turingPhaseStateMachine.finishWalkAround();
        assertEquals(turingPhaseStateMachine.getTuringPhase(), FILL_THE_OUTSIDE_WITH_COLOR);
        turingPhaseStateMachine.finishFillTheOutsideWithColors();
        assertEquals(turingPhaseStateMachine.getTuringPhase(), FINISHED);
        log.info("finishFillTheOutsideWithColorsTest done");
    }
}
