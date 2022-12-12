package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.canvas.garden;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoom;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoomContext;

import javax.swing.*;

/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
@Getter
@ToString(callSuper = true)
public class GardenOfEdenCheckBox extends JCheckBox implements MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final MandelbrotZoomContext tabCtx;
    private final String gardenOfEdenEnabledString;
    private final boolean gardenOfEdenEnabledSelected;

    public GardenOfEdenCheckBox(MandelbrotZoomContext tabCtx) {
        super(
            tabCtx.getCtx().getProperties().getSimulatedevolution().getGardenOfEden().getGardenOfEdenEnabledString(),
            tabCtx.getCtx().getProperties().getSimulatedevolution().getGardenOfEden().getGardenOfEdenEnabled()
        );

        this.gardenOfEdenEnabledSelected = tabCtx.getCtx().getProperties().getSimulatedevolution().getGardenOfEden().getGardenOfEdenEnabled();
        this.gardenOfEdenEnabledString = tabCtx.getCtx().getProperties().getSimulatedevolution().getGardenOfEden().getGardenOfEdenEnabledString();
        this.tabCtx = tabCtx;
    }
}
