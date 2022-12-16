package org.woehlke.computer.kurzweil.mandelbrot.zoom.control;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame
 *
 * @see java.lang.Thread
 * @see java.lang.Runnable
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
public class ControllerThread extends Thread implements Runnable {

    private volatile ApplicationModel model;
    private volatile ApplicationFrame view;

    private final int threadSleepTime;

    private volatile Boolean goOn;

    public ControllerThread(ApplicationModel model, ApplicationFrame view) {
        this.view = view;
        this.model = model;
        this.goOn = Boolean.TRUE;
        this.threadSleepTime = 1;
    }

    public void run() {
        boolean doIt;
        do {
            doIt = isRunning();
            if(this.model.step()){
                view.getCanvas().repaint();
                view.repaint();
            }
            try { sleep(threadSleepTime); }
            catch (InterruptedException e) { }
        } while (doIt);
    }

    public synchronized boolean isRunning() {
        return this.goOn;
    }

    public synchronized void exit() {
        goOn = Boolean.FALSE;
    }

}
