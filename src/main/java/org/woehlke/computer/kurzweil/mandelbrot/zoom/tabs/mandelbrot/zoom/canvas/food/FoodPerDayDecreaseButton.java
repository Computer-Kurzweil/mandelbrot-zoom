package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.canvas.food;

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
public class FoodPerDayDecreaseButton extends JButton implements MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final MandelbrotZoomContext tabCtx;
    private final String labelFoodPerDayDecrease;

    public FoodPerDayDecreaseButton(MandelbrotZoomContext tabCtx) {
        super(tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getButtonFoodPerDayDecrease());
        this.tabCtx = tabCtx;
        this.labelFoodPerDayDecrease = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getButtonFoodPerDayDecrease();
    }
}
