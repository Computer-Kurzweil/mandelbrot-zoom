package org.woehlke.computer.kurzweil.mandelbrot.zoom.model;

import lombok.extern.slf4j.Slf4j;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal.GaussianNumberPlane;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.state.ApplicationStateMachine;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.MandelbrotTuringMachine;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.MandelbrotZoomFrame;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 *
 * @see GaussianNumberPlane
 * @see MandelbrotTuringMachine
 * @see ApplicationStateMachine
 *
 * @see ComputerKurzweilProperties
 * @see MandelbrotZoomFrame
 *
 * Created by tw on 16.12.2019.
 */
@Slf4j
public class ApplicationModel {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile MandelbrotTuringMachine mandelbrotTuringMachine;
    private volatile ApplicationStateMachine applicationStateMachine;

    private volatile ComputerKurzweilProperties config;
    private volatile MandelbrotZoomFrame frame;

    public ApplicationModel(ComputerKurzweilProperties config, MandelbrotZoomFrame frame) {
        this.config = config;
        this.frame = frame;
        this.gaussianNumberPlane = new GaussianNumberPlane(this);
        this.mandelbrotTuringMachine = new MandelbrotTuringMachine(this);
        this.applicationStateMachine = new ApplicationStateMachine();
    }

    public synchronized boolean click(Point c) {
        applicationStateMachine.click();
        boolean repaint = true;
        this.zoomIn();
        return repaint;
    }

    public synchronized boolean step() {
        boolean repaint = false;
        switch (applicationStateMachine.getApplicationState()) {
            case MANDELBROT:
                repaint = mandelbrotTuringMachine.step();
                break;
            case JULIA_SET:
                break;
        }
        return repaint;
    }

    public void zoomIn(){
        this.gaussianNumberPlane.zoomIn();
    }

    public void zoomOut(){
        this.gaussianNumberPlane.zoomOut();
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x, y);
    }

    public Point getWorldDimensions() {
        int scale = config.getMandelbrotZoom().getView().getScale();
        int width = scale * config.getMandelbrotZoom().getView().getWidth();
        int height = scale * config.getMandelbrotZoom().getView().getHeight();
        return new Point(width, height);
    }

    public GaussianNumberPlane getGaussianNumberPlane() {
        return gaussianNumberPlane;
    }

}
