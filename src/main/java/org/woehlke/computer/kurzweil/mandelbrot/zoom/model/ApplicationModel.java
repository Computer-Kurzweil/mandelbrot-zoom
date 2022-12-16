package org.woehlke.computer.kurzweil.mandelbrot.zoom.model;

import lombok.Getter;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal.GaussianNumberPlane;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.MandelbrotTuringMachine;
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
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal.GaussianNumberPlane
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.MandelbrotTuringMachine
 *
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame
 *
 * Created by tw on 16.12.2019.
 */
@Getter
public class ApplicationModel {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile MandelbrotTuringMachine mandelbrotTuringMachine;
    private volatile ComputerKurzweilProperties config;
    private volatile ApplicationFrame tab;

    public ApplicationModel(ApplicationFrame tab) {
        this.tab = tab;
        this.config = tab.getConfig();
        this.gaussianNumberPlane = new GaussianNumberPlane(tab);
        this.mandelbrotTuringMachine = new MandelbrotTuringMachine(tab);
    }

    public void start(){
        this.gaussianNumberPlane.start();
        this.mandelbrotTuringMachine.start();
    }

    public synchronized boolean click(Point c) {
        gaussianNumberPlane.zoomIn(c);
        boolean repaint = true;
        return repaint;
    }

    public synchronized boolean step() {
        boolean repaint = mandelbrotTuringMachine.step();
        return repaint;
    }

    public void zoomOut() {
        gaussianNumberPlane.zoomOut();
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x, y);
    }

}
