package org.woehlke.computer.kurzweil.mandelbrot.zoom.view;

import lombok.Getter;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.control.ControllerThread;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.LatticePoint;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.canvas.ApplicationCanvas;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.panels.PanelButtons;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.panels.PanelSubtitle;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see ControllerThread
 * @see ApplicationCanvas
 * @see ApplicationModel
 *
 * @see Rectangle
 * @see Dimension
 * @see JFrame
 * @see MouseListener
 * @see ImageObserver
 * @see WindowListener
 *
 * Date: 04.02.2006
 * Time: 18:47:46
 */
public class ApplicationFrame extends JFrame implements ImageObserver,
        MenuContainer,
        Serializable,
        Accessible,
        WindowListener,
        MouseListener {

    final static long serialVersionUID = 242L;

    @Getter
    private final ComputerKurzweilProperties config;

    @Getter
    private volatile ApplicationModel model;

    @Getter
    private volatile ApplicationCanvas canvas;

    @Getter
    private volatile ControllerThread controller;

    private final PanelButtons panelButtons;
    private final PanelSubtitle panelSubtitle;

    private volatile Rectangle rectangleBounds;
    private volatile Dimension dimensionSize;

    public ApplicationFrame(ComputerKurzweilProperties config) {
        super(config.getMandelbrotZoom().getView().getTitle());
        this.config = config;
        this.model = new ApplicationModel(this);
        this.canvas = new ApplicationCanvas(this);
        this.controller = new ControllerThread( this);
        this.panelButtons = new PanelButtons(this);
        this.panelSubtitle = new PanelSubtitle(config.getMandelbrotZoom().getView().getSubtitle());
        BoxLayout layout = new BoxLayout(rootPane, BoxLayout.PAGE_AXIS);
        rootPane.setLayout(layout);
        rootPane.add(panelSubtitle);
        rootPane.add(canvas);
        rootPane.add(panelButtons);
        this.addWindowListener(this);
        this.canvas.addMouseListener(   this);
        this.showMeInit();
    }

    public void start() {
        this.model.start();
        this.controller.start();
        this.canvas.repaint();
        this.repaint();
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
        LatticePoint c = new LatticePoint(e.getX(), e.getY());
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

    public void showMeInit() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double windowWidth = this.rootPane.getWidth();
        double windowHeight  = this.canvas.getHeight() + 180;
        double startX = (screenSize.getWidth() - windowWidth) / 2d;
        double startY = (screenSize.getHeight() - windowHeight) / 2d;
        int myHeight = Double.valueOf(windowHeight).intValue();
        int myWidth = Double.valueOf(windowWidth).intValue();
        int myStartX = Double.valueOf(startX).intValue();
        int myStartY = Double.valueOf(startY).intValue();
        this.rectangleBounds = new Rectangle(myStartX, myStartY, myWidth, myHeight);
        this.dimensionSize = new Dimension(myWidth, myHeight);
        this.setBounds(this.rectangleBounds);
        this.setSize(this.dimensionSize);
        this.setPreferredSize(this.dimensionSize);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        toFront();
    }

    /**
     * TODO write doc.
     */
    public void showMe() {
        this.pack();
        this.setBounds(this.rectangleBounds);
        this.setSize(this.dimensionSize);
        this.setPreferredSize(this.dimensionSize);
        this.setVisible(true);
        this.toFront();
    }

}
