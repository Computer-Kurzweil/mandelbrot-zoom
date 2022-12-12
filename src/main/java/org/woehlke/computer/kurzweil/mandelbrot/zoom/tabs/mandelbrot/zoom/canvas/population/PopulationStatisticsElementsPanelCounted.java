package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.canvas.population;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.application.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.Updateable;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.layouts.FlowLayoutCenter;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.widgets.SubTab;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.commons.widgets.SubTabImpl;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoom;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoomContext;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.LifeCycleStatus;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.population.MandelbrotZoomPopulation;

import javax.swing.border.CompoundBorder;
import java.awt.*;


/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
@Getter
@ToString(callSuper = true,exclude = {"tabCtx","border","layout","layoutSubPanel"})
@EqualsAndHashCode(callSuper=true,exclude = {"tabCtx","border","layout","layoutSubPanel"})
public class PopulationStatisticsElementsPanelCounted extends SubTabImpl implements MandelbrotZoom, SubTab, Updateable {

    private static final long serialVersionUID = 242L;

    private final PopulationStatisticsElement populationElement;
    private final PopulationStatisticsElement generationOldestElement;
    private final PopulationStatisticsElement generationYoungestElement;
    private final String borderLabel;

    private final int initialPopulation;
    private final String populationLabel;
    private final String generationOldestLabel;
    private final String generationYoungestLabel;

    private final MandelbrotZoomContext tabCtx;
    private final CompoundBorder border;
    private final FlowLayoutCenter layout;
    private final FlowLayout layoutSubPanel;

    private MandelbrotZoomPopulation population;

    public PopulationStatisticsElementsPanelCounted(
        MandelbrotZoomContext tabCtx
    ) {
        super(tabCtx.getCtx().getProperties().getSimulatedevolution().getPopulation().getPanelPopulationStatistics(),tabCtx.getCtx().getProperties());
        this.tabCtx = tabCtx;
        layoutSubPanel = new FlowLayout();
        this.setLayout(layoutSubPanel);
        borderLabel = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getPopulation().getPanelPopulationStatistics();
        layout = new FlowLayoutCenter();
        border = tabCtx.getCtx().getBottomButtonsPanelBorder(borderLabel);
        this.setLayout(layout);
        this.setBorder(border);
        ComputerKurzweilProperties.SimulatedEvolution.Population cfg = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getPopulation();
        initialPopulation = cfg.getInitialPopulation();
        populationLabel = cfg.getPopulationLabel();
        generationOldestLabel = cfg.getGenerationOldestLabel();
        generationYoungestLabel = cfg.getGenerationYoungestLabel();
        populationElement = new PopulationStatisticsElement(populationLabel, LifeCycleStatus.POPULATION);
        generationYoungestElement = new PopulationStatisticsElement(generationYoungestLabel, LifeCycleStatus.POPULATION);
        generationOldestElement = new PopulationStatisticsElement(generationOldestLabel, LifeCycleStatus.POPULATION);
        PopulationStatisticsElement[] elements = {
            populationElement,
            generationYoungestElement,
            generationOldestElement
        };
        for(PopulationStatisticsElement ps : elements){
            this.add(ps);
        }
        update();
    }

    public void update() {
        /*
        population = this.tabCtx.getTabModel().getPopulationContainer().getCurrentGeneration();
        populationElement.setText(population.getPopulation());
        generationYoungestElement.setText(population.getGenerationYoungest());
        generationOldestElement.setText(population.getGenerationOldest());
        */
    }
}
