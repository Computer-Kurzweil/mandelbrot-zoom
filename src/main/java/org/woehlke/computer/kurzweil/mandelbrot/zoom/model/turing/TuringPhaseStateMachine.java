package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing;

import lombok.Getter;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see TuringPhase
 *
 * Created by tw on 16.12.2019.
 */
public class TuringPhaseStateMachine {

    @Getter
    private volatile TuringPhase turingPhase;

    public TuringPhaseStateMachine() {
        startSearchTheSet();
    }

    public void start(){
        startSearchTheSet();
    }

    public void startSearchTheSet(){
        turingPhase = TuringPhase.SEARCH_THE_SET;
    }

    public void finishSearchTheSet(){
        turingPhase = TuringPhase.WALK_AROUND_THE_SET;
    }

    public void finishWalkAround() {
        turingPhase = TuringPhase.FILL_THE_OUTSIDE_WITH_COLOR;
    }

    public void finishFillTheOutsideWithColors() {
        turingPhase = TuringPhase.FINISHED;
    }

}
