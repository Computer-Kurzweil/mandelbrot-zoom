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
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.SimulatedEvolution;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.SimulatedEvolutionContext;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.LifeCycleStatus;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.population.SimulatedEvolutionPopulation;

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
public class PopulationStatisticsElementsPanelLifeCycle extends SubTabImpl implements SimulatedEvolution, SubTab, Updateable {

    private static final long serialVersionUID = 242L;

    private final PopulationStatisticsElement youngCellsElement;
    private final PopulationStatisticsElement youngAndFatCellsElement;
    private final PopulationStatisticsElement fullAgeCellsElement;
    private final PopulationStatisticsElement hungryCellsElement;
    private final PopulationStatisticsElement oldCellsElement;

    private final String borderLabel;

    private final int initialPopulation;
    private final String youngCellsLabel;
    private final String youngAndFatCellsLabel;
    private final String fullAgeCellsLabel;
    private final String hungryCellsLabel;
    private final String oldCellsLabel;

    private final SimulatedEvolutionContext tabCtx;
    private final CompoundBorder border;
    private final FlowLayoutCenter layout;
    private final FlowLayout layoutSubPanel;

    private SimulatedEvolutionPopulation population;

    public PopulationStatisticsElementsPanelLifeCycle(
      SimulatedEvolutionContext tabCtx
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
        youngCellsLabel = cfg.getYoungCellsLabel();
        youngAndFatCellsLabel = cfg.getYoungAndFatCellsLabel();
        fullAgeCellsLabel = cfg.getFullAgeCellsLabel();
        hungryCellsLabel = cfg.getHungryCellsLabel();
        oldCellsLabel = cfg.getOldCellsLabel();
        youngCellsElement = new PopulationStatisticsElement(youngCellsLabel, LifeCycleStatus.YOUNG);
        youngAndFatCellsElement = new PopulationStatisticsElement(youngAndFatCellsLabel, LifeCycleStatus.YOUNG_AND_FAT);
        fullAgeCellsElement = new PopulationStatisticsElement(fullAgeCellsLabel, LifeCycleStatus.FULL_AGE);
        hungryCellsElement = new PopulationStatisticsElement(hungryCellsLabel, LifeCycleStatus.HUNGRY);
        oldCellsElement = new PopulationStatisticsElement(oldCellsLabel, LifeCycleStatus.OLD);
        this.add(youngCellsElement);
        this.add(youngAndFatCellsElement);
        this.add(fullAgeCellsElement);
        this.add(hungryCellsElement);
        this.add(oldCellsElement);
        update();
    }

    public void update() {
        /*
        population = this.tabCtx.getTabModel().getPopulationContainer().getCurrentGeneration();
        youngCellsElement.setText(population.getYoungCells());
        youngAndFatCellsElement.setText(population.getYoungAndFatCells());
        fullAgeCellsElement.setText(population.getFullAgeCells());
        hungryCellsElement.setText(population.getHungryCells());
        oldCellsElement.setText(population.getOldCells());
        */
    }

}
