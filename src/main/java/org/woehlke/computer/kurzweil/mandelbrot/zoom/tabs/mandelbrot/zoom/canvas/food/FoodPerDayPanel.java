package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.canvas.food;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.Updateable;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.widgets.SubTab;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.widgets.SubTabImpl;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.TabPanel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoom;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoomContext;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.SimulatedEvolutionModel;

/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
@Getter
@ToString(callSuper = true)
public class FoodPerDayPanel extends SubTabImpl implements MandelbrotZoom, Updateable, SubTab {

    private static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final MandelbrotZoomContext tabCtx;
    private final String foodPerDayBorderLabel;
    private final FoodPerDayLabel foodPerDayLabel;
    private final FoodPerDayTextField foodPerDayTextField;
    private final FoodPerDayIncreaseButton foodPerDayIncreaseButton;
    private final FoodPerDayDecreaseButton foodPerDayDecreaseButton;
    private final SimulatedEvolutionModel tabModel;

    public FoodPerDayPanel(MandelbrotZoomContext tabCtx) {
        super(tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getFoodPerDayLabel(),tabCtx.getCtx().getProperties());
        this.tabCtx = tabCtx;
        this.tabModel = this.tabCtx.getTabModel();
        this.foodPerDayLabel = new FoodPerDayLabel(this.tabCtx);
        this.foodPerDayTextField = new FoodPerDayTextField(this.tabCtx);
        this.foodPerDayIncreaseButton = new FoodPerDayIncreaseButton(this.tabCtx);
        this.foodPerDayDecreaseButton = new FoodPerDayDecreaseButton(this.tabCtx);
        this.foodPerDayBorderLabel = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getFood().getFoodPerDayLabel();
        //this.foodPerDayBorder = this.tabCtx.getCtx().getBorder(this.foodPerDayBorderLabel);
        //this.setBorder(this.foodPerDayBorder);
        this.add(this.foodPerDayLabel);
        this.add(this.foodPerDayTextField);
        this.add(this.foodPerDayIncreaseButton);
        this.add(this.foodPerDayDecreaseButton);
    }

    public void addActionListener(TabPanel myTabPanel) {
        this.foodPerDayIncreaseButton.addActionListener(myTabPanel);
        this.foodPerDayDecreaseButton.addActionListener(myTabPanel);
    }

    public void update() {
        //int foodPerDay = tabModel.getSimulatedEvolutionParameter().getFoodPerDay();
        //this.foodPerDayTextField.setFoodPerDay(foodPerDay);
    }
}
