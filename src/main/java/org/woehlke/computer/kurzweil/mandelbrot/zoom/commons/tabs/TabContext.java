package org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.tabs;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.has.HasTab;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.has.HasTabCanvas;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.has.HasTabController;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.has.HasTabModel;

/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
public interface TabContext extends
    HasTabController,
    HasTabModel,
    HasTabCanvas,
    HasTab {

}
