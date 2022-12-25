package org.woehlke.computer.kurzweil.mandelbrot.zoom.control;

import lombok.extern.slf4j.Slf4j;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
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
 * @see ApplicationModel
 * @see MandelbrotZoomFrame
 *
 * @see Thread
 * @see Runnable
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
@Slf4j
public class ControllerThread extends Thread implements Runnable {

    private volatile ApplicationModel model;
    private volatile MandelbrotZoomFrame frame;

    private volatile Boolean goOn;
    private final int threadSsleepTime;

    public ControllerThread(ApplicationModel model, MandelbrotZoomFrame frame) {
        this.frame = frame;
        this.model = model;
        this.goOn = Boolean.TRUE;
        this.threadSsleepTime = 1;
    }

    public void run() {
        boolean doIt = goOn.booleanValue();
        do {
            doIt = isRunning();
            if(this.model.step()){
                frame.getCanvas().repaint();
            }
            try { sleep(threadSsleepTime); }
            catch (InterruptedException e) { }
        } while (doIt);
    }

    private synchronized boolean isRunning(){
        return goOn;
    }

    public synchronized void exit() {
        goOn = Boolean.FALSE;
    }

}
