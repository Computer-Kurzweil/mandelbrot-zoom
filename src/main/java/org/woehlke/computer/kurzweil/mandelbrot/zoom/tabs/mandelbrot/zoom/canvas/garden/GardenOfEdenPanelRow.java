package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.canvas.garden;

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
public class GardenOfEdenPanelRow extends SubTabImpl implements MandelbrotZoom, Updateable, SubTab {

    private static final long serialVersionUID = 242L;

    @ToString.Exclude
    private final MandelbrotZoomContext tabCtx;
    @ToString.Exclude
    private final SimulatedEvolutionModel tabModel;
    private final GardenOfEdenCheckBox gardenOfEdenEnabled;
    private final GardenOfEdenToggleButton buttonToggleGardenOfEden;
    //private final GardenOfEdenPanel gardenOfEdenPanel;

    public GardenOfEdenPanelRow(MandelbrotZoomContext tabCtx) {
        super("Garden of Eden",tabCtx.getCtx().getProperties());
        this.tabCtx = tabCtx;
        this.tabModel = this.tabCtx.getTabModel();
        this.gardenOfEdenEnabled = new GardenOfEdenCheckBox(this.tabCtx);
        this.buttonToggleGardenOfEden = new GardenOfEdenToggleButton(this.tabCtx);
        /*
        this.gardenOfEdenPanel = new GardenOfEdenPanel(this.tabCtx);
        this.gardenOfEdenPanel.add(this.gardenOfEdenEnabled);
        this.gardenOfEdenPanel.add(this.buttonToggleGardenOfEden);
        this.add( this.gardenOfEdenPanel);
        */
        this.add(this.gardenOfEdenEnabled);
        this.add(this.buttonToggleGardenOfEden);
    }

    public void setSelected(boolean selected) {
        this.tabModel.getSimulatedEvolutionParameter().setGardenOfEdenEnabled(selected);
        update();
    }

    public void toggleGardenOfEden() {
        this.tabModel.getSimulatedEvolutionParameter().toggleGardenOfEden();
        update();
    }

    public void addActionListener(TabPanel myTabPanel) {
        this.buttonToggleGardenOfEden.addActionListener(myTabPanel);
    }

    public void update() {
        boolean enabled = tabModel.getSimulatedEvolutionParameter().isGardenOfEdenEnabled();
        this.buttonToggleGardenOfEden.setSelected(enabled);
        this.gardenOfEdenEnabled.setSelected(enabled);
    }
}
