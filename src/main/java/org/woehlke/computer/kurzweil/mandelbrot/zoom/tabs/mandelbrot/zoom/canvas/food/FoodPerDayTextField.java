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
public class FoodPerDayTextField extends JTextField implements MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final MandelbrotZoomContext tabCtx;
    private final String foodPerDayTextFieldString;
    private final int foodPerDayTextFieldCols;

    public FoodPerDayTextField(MandelbrotZoomContext tabCtx) {
        super(
            tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getFoodPerDay()+"",
            tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getFoodPerDayFieldColumns()
        );
        this.tabCtx = tabCtx;
        this.foodPerDayTextFieldString = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getFoodPerDay()+"";
        this.foodPerDayTextFieldCols = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getFoodPerDayFieldColumns();
    }

    public void setFoodPerDay(int foodPerDay){
        this.setText(""+foodPerDay);
    }
}
