package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.WorldPoint;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;


/**
 * The Container for running the Simulation.
 * It containes a World Data Model, a Controller Thread and a WorldCanvas View.
 *
 * (C) 2013 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 * Date: 04.02.2006
 * Time: 18:33:14
 */
@SuppressWarnings({"deprecation"})
@Log4j2
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MandelbrotZoomApplet extends JApplet implements ImageObserver, MenuContainer, Serializable, Accessible, MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    private Label title = new Label("      Artificial Life Simulation of Bacteria Motion depending on DNA - (C) 2013 Thomas Woehlke");

    /**
     * ControllerThread for Interachtions between Model and View (MVC-Pattern).
     */
    private MandelbrotZoomController simulatedEvolutionController;

    /**
     * The View for the World. Food and Cells are painted to the Canvas.
     */
    private MandelbrotZoomCanvas canvas;

    /**
     * Data Model for the Simulation. The World contains the Bacteria Cells and the Food.
     */
    private MandelbrotZoomModel mandelbrotZoomModel;

    public void init() {
        int scale = 2;
        int width = 320 * scale;
        int height = 234 * scale;
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        simulatedEvolutionController = new MandelbrotZoomController();
        WorldPoint worldDimensions = new WorldPoint(width,height);
        mandelbrotZoomModel = new MandelbrotZoomModel(worldDimensions);
        canvas = new MandelbrotZoomCanvas(worldDimensions);
        canvas.setTabModel(mandelbrotZoomModel);
        this.add(canvas, BorderLayout.CENTER);
        simulatedEvolutionController.setCanvas(canvas);
        simulatedEvolutionController.setMandelbrotZoomModel(mandelbrotZoomModel);
        simulatedEvolutionController.start();
    }

    public void destroy() {
    }

    public void stop() {
    }

    public WorldPoint getCanvasDimensions() {
        return canvas.getWorldDimensions();
    }
}
