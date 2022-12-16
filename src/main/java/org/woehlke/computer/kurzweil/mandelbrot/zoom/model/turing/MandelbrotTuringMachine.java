package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal.GaussianNumberPlane;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see GaussianNumberPlane
 * @see TuringPositions
 * @see TuringPhaseStateMachine
 * @see ApplicationModel
 *
 * Date: 28.08.13
 * Time: 12:39
 */
public class MandelbrotTuringMachine {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile TuringPositions turingPositions;
    private volatile TuringPhaseStateMachine turingPhaseStateMachine;
    private final ApplicationModel model;

    public MandelbrotTuringMachine(ApplicationModel model) {
        this.model = model;
        this.gaussianNumberPlane = this.model.getGaussianNumberPlane();
        this.turingPhaseStateMachine = new TuringPhaseStateMachine();
        this.turingPositions = new TuringPositions(this.model.getConfig().getWorldDimensions());
    }

    public void start() {
        this.turingPhaseStateMachine.start();
        this.gaussianNumberPlane.start();
        this.turingPositions.start();
    }

    public synchronized boolean step() {
        boolean repaint=true;
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
        if(this.gaussianNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
            this.turingPositions.markFirstSetPosition();
            this.turingPhaseStateMachine.finishSearchTheSet();
        } else {
            this.turingPositions.goForward();
        }
    }

    private void stepWalkAround(){
        if(gaussianNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
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
        this.gaussianNumberPlane.fillTheOutsideWithColors();
        this.turingPhaseStateMachine.finishFillTheOutsideWithColors();
    }
}
