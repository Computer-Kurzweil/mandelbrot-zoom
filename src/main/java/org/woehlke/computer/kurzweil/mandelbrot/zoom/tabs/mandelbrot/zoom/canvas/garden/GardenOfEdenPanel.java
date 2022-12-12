package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.canvas.garden;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoom;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoomContext;

import javax.swing.*;
import java.awt.*;

/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
@Getter
@ToString(callSuper = true)
public class GardenOfEdenPanel extends JPanel implements MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final MandelbrotZoomContext tabCtx;
    private final String gardenOfEdenPanelBorderLabel;
    //private final CompoundBorder gardenOfEdenPanelBorder;

    public GardenOfEdenPanel(MandelbrotZoomContext tabCtx) {
        super(new FlowLayout());
        this.tabCtx=tabCtx;
        this.gardenOfEdenPanelBorderLabel = tabCtx.getCtx().getProperties().getSimulatedevolution().getGardenOfEden().getPanelGardenOfEden();
        //this.gardenOfEdenPanelBorder = this.tabCtx.getCtx().getBorder(gardenOfEdenPanelBorderLabel);
        //this.setBorder(gardenOfEdenPanelBorder);
    }
}
