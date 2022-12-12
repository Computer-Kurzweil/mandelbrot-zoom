package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.application.ComputerKurzweilContext;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.tabs.TabContext;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.WorldPoint;

import java.util.concurrent.ForkJoinTask;

import static java.lang.Thread.State.NEW;
/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
@Getter
@ToString(callSuper = false, exclude={"ctx","controller","tab"})
@EqualsAndHashCode(callSuper = false, exclude={"ctx","controller","tab"})
public class MandelbrotZoomContext extends ForkJoinTask<Void> implements TabContext, MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    private final ComputerKurzweilContext ctx;
    private final MandelbrotZoomTab tab;
    private final MandelbrotZoomCanvas canvas;
    private final MandelbrotZoomModel tabModel;

    @Setter
    private MandelbrotZoomController controller;

    public MandelbrotZoomContext(
        MandelbrotZoomTab tab,
        ComputerKurzweilContext ctx
    ) {
       this.tab = tab;
       this.ctx = ctx;
        int scale = 2;
        int width = 320 * scale;
        int height = 234 * scale;
        WorldPoint worldDimensions = new WorldPoint(width,height);
       this.canvas = new MandelbrotZoomCanvas(  worldDimensions );
       this.tabModel = this.canvas.getTabModel();
       this.controller = new MandelbrotZoomController();
    }

    @Override
    public void stopController() {
        this.controller.exit();
        this.controller = null;
        this.controller = new MandelbrotZoomController();
    }

    @Override
    public void startController() {
        if(this.controller == null){
            this.controller = new MandelbrotZoomController();
        } else {
            if(this.controller.getState() != NEW){
                this.stopController();
            }
        }
    }

    @Override
    public Void getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(Void value) {

    }

    @Override
    protected boolean exec() {
        this.tab.update();
        this.tab.repaint();
        return true;
    }
}
