package org.woehlke.computer.kurzweil.mandelbrot.zoom.view;

import lombok.extern.slf4j.Slf4j;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.control.ControllerThread;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.canvas.ApplicationCanvas;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.labels.PanelCopyright;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.labels.PanelSubtitle;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.Serial;
import java.io.Serializable;

/**
 * Mandelbrot Set drawn by a Turing Machine. Click to see corresponding Julia set.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see ControllerThread
 * @see ApplicationCanvas
 * @see ApplicationModel
 * @see PanelSubtitle
 * @see PanelCopyright
 *
 * @see JFrame
 * @see ImageObserver
 * @see WindowListener
 * @see MouseListener
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 *
 * Date: 04.02.2006
 * Time: 18:47:46
 */
@Slf4j
public class ApplicationFrame extends JFrame implements ImageObserver,
        MenuContainer,
        Serializable,
        Accessible,
        WindowListener,
        MouseListener {

    @Serial
    private final static long serialVersionUID = 242L;

    private final PanelSubtitle panelSubtitle;
    private final PanelCopyright panelCopyright;

    private volatile ControllerThread controller;
    private volatile ApplicationCanvas canvas;
    private volatile ApplicationModel model;
    private volatile Rectangle rectangleBounds;
    private volatile Dimension dimensionSize;

    public ApplicationFrame(ComputerKurzweilProperties config) {
        super(config.getMandelbrotJulia().getView().getTitle());
        this.model = new ApplicationModel(config,this);
        this.canvas = new ApplicationCanvas(model);
        this.controller = new ControllerThread(model, this);
        this.panelSubtitle = new PanelSubtitle(config.getMandelbrotJulia().getView().getSubtitle());
        this.panelCopyright = new PanelCopyright(config.getMandelbrotJulia().getView().getCopyright());
        BoxLayout layout = new BoxLayout(rootPane, BoxLayout.PAGE_AXIS);
        rootPane.setLayout(layout);
        rootPane.add(panelSubtitle);
        rootPane.add(canvas);
        rootPane.add(panelCopyright);
        this.addWindowListener(this);
        this.canvas.addMouseListener(   this);
        this.showMeInit();
        this.setModeSwitch();
        this.controller.start();
    }

    public void windowOpened(WindowEvent e) {
        showMe();
    }

    public void windowClosing(WindowEvent e) {
        this.controller.exit();
    }

    public void windowClosed(WindowEvent e) {
        this.controller.exit();
    }

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {
        showMe();
    }

    public void windowActivated(WindowEvent e) {
        showMe();
    }

    public void windowDeactivated(WindowEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        Point c = new Point(e.getX(), e.getY());
        this.model.click(c);
        showMe();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Things to do, to show the Application Window started by Constructor
     */
    public void showMeInit() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = this.rootPane.getWidth();
        double height  = this.canvas.getHeight() + 120;
        double startX = (screenSize.getWidth() - width) / 2d;
        double startY = (screenSize.getHeight() - height) / 2d;
        int myheight = Double.valueOf(height).intValue();
        int mywidth = Double.valueOf(width).intValue();
        int mystartX = Double.valueOf(startX).intValue();
        int mystartY = Double.valueOf(startY).intValue();
        this.rectangleBounds = new Rectangle(mystartX, mystartY, mywidth, myheight);
        this.dimensionSize = new Dimension(mywidth, myheight);
        this.setBounds(this.rectangleBounds);
        this.setSize(this.dimensionSize);
        this.setPreferredSize(this.dimensionSize);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        toFront();
    }

    /**
     * Things to do, to show the Application Window again.
     */
    public void showMe() {
        this.pack();
        this.setBounds(this.rectangleBounds);
        this.setSize(this.dimensionSize);
        this.setPreferredSize(this.dimensionSize);
        this.setVisible(true);
        this.toFront();
    }

    public void setModeSwitch() {
        canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    public ApplicationCanvas getCanvas() {
        return canvas;
    }

    public void start() {
    }
}
