package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing;

import lombok.Getter;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 *
 * Created by tw on 16.12.2019.
 */
public class TuringPhaseState {

    @Getter
    private volatile TuringPhase turingPhase;

    public TuringPhaseState() {
        start();
    }

    public void start(){
        this.turingPhase = TuringPhase.SEARCH_THE_SET;
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
