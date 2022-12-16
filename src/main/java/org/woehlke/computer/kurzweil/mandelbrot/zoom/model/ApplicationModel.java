package org.woehlke.computer.kurzweil.mandelbrot.zoom.model;

import lombok.Getter;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.complexnumer.ComplexNumberPlane;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.LatticePoint;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.TuringPhase;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.TuringPhaseStateMachine;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.TuringPositions;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2015 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see ComplexNumberPlane
 * @see TuringPositions
 * @see TuringPhase
 * @see TuringPhaseStateMachine
 *
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame
 *
 * Created by tw on 16.12.2019.
 */
@Getter
public class ApplicationModel {

    private final ApplicationFrame tab;

    private volatile ComplexNumberPlane complexNumberPlane;
    private volatile TuringPositions turingPositions;
    private volatile TuringPhaseStateMachine turingPhaseStateMachine;


    public ApplicationModel(ApplicationFrame tab) {
        this.tab = tab;
        this.turingPositions = new TuringPositions(this.tab.getConfig().getWorldDimensions());
        this.complexNumberPlane = new ComplexNumberPlane(this.tab);
        this.complexNumberPlane = this.getComplexNumberPlane();
        this.turingPhaseStateMachine = new TuringPhaseStateMachine();
    }

    public void start() {
        this.turingPhaseStateMachine.start();
        this.complexNumberPlane.start();
        this.turingPositions.start();
    }

    public synchronized boolean click(LatticePoint c) {
        boolean repaint = true;
        //complexNumberPlane.zoomIn(c);
        return repaint;
    }

    public void zoomOut() {
        complexNumberPlane.zoomOut();
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return complexNumberPlane.getCellStatusFor(x, y);
    }

    public synchronized boolean step() {
        boolean repaint = true;
        switch(turingPhaseStateMachine.getTuringPhase()){
            case SEARCH_THE_SET:
                stepGoToSet();
                repaint=false;
                break;
            case WALK_AROUND_THE_SET:
                stepWalkAround();
                break;
            case FILL_THE_OUTSIDE_WITH_COLOR:
                fillTheOutsideWithColors();
                break;
            case FINISHED:
            default:
                repaint=false;
                break;
        }
        return repaint;
    }

    private void stepGoToSet(){
        if(this.complexNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
            this.turingPositions.markFirstSetPosition();
            this.turingPhaseStateMachine.finishSearchTheSet();
        } else {
            this.turingPositions.goForward();
        }
    }

    private void stepWalkAround(){
        if(complexNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
            this.turingPositions.turnRight();
        } else {
            this.turingPositions.turnLeft();
        }
        this.turingPositions.goForward();
        if(this.turingPositions.isFinishedWalkAround()){
            this.turingPhaseStateMachine.finishWalkAround();
        }
    }

    private void fillTheOutsideWithColors(){
        this.complexNumberPlane.fillTheOutsideWithColors();
        this.turingPhaseStateMachine.finishFillTheOutsideWithColors();
    }

}
