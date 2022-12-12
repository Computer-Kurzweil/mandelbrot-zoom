package org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.population;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoom;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoomContext;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.Cell;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.model.WorldPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
@Getter
@ToString(callSuper = true, exclude={"tabCtx","statistics","cells"})
@EqualsAndHashCode(exclude={"tabCtx","statistics","cells"})
public class MandelbrotZoomPopulationContainer implements MandelbrotZoom {

    private static final long serialVersionUID = 242L;

    private final MandelbrotZoomContext tabCtx;
    private final int initialPopulation;
    private final ConcurrentLinkedQueue<MandelbrotZoomPopulation> statistics = new ConcurrentLinkedQueue<>();
    private final List<Cell> cells;
    private long worldIteration;
    private final int queueMaxLength;

    public MandelbrotZoomPopulationContainer(
        MandelbrotZoomContext tabCtx
    ) {
        this.tabCtx = tabCtx;
        worldIteration = 0L;
        cells = new ArrayList<>();
        queueMaxLength = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getControl().getQueueMaxLength();
        initialPopulation = this.tabCtx.getCtx().getProperties().getSimulatedevolution().getPopulation().getInitialPopulation();
        createInitialPopulation();
    }

    public void createInitialPopulation() {
        cells.clear();
        WorldPoint max = new WorldPoint(640,400);
        WorldPoint position = new WorldPoint(40,40);
        Random random = new Random((new Date()).getTime());
        for (int i = 0; i < initialPopulation ; i++) {
            Cell cell = new Cell(max,position,random);
            cells.add(cell);
        }
    }

    public void push(MandelbrotZoomPopulation populationCensus) {
        worldIteration++;
        populationCensus.setWorldIteration(worldIteration);
        statistics.add(populationCensus);
        if (statistics.size() > queueMaxLength) {
            statistics.poll();
        }
        log.info(worldIteration + " : " + populationCensus);
    }

    public MandelbrotZoomPopulation getCurrentGeneration() {
        MandelbrotZoomPopulation currentGeneration = statistics.peek();
        if(currentGeneration == null){
            log.info(worldIteration + "statistics.peek() == null ");
            currentGeneration = new MandelbrotZoomPopulation();
        }
        return currentGeneration;
    }
}
