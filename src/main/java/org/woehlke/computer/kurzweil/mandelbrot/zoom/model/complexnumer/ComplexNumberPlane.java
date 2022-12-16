package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.complexnumer;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing.LatticePoint;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.complexnumer.ComplexNumber
 * @see LatticePoint
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel
 *
 * @see java.util.Deque
 * @see java.util.ArrayDeque
 *
 * Created by tw on 16.12.2019.
 */
public class ComplexNumberPlane implements Serializable {

    final static long serialVersionUID = 242L;

    public final static int ZOOM_LEVEL_START = 1;
    public final static int YET_UNCOMPUTED = -1;

    private final ApplicationFrame tab;
    private volatile LatticePoint worldDimensions;
    private volatile ComplexNumber complexWorldDimensions;
    private volatile ComplexNumber complexCenterForMandelbrot;
    private volatile ComplexNumber zoomCenter;
    private volatile int zoomLevel;

    private volatile int[][] lattice;

    private volatile Deque<ComplexNumber> complexCenterForZoomedMandelbrot = new ArrayDeque<>();

    public ComplexNumberPlane(ApplicationFrame tab) {
        this.tab = tab;
        this.worldDimensions =  this.tab.getConfig().getWorldDimensions();
        System.out.println(this.worldDimensions.toString());
        this.lattice = new int[worldDimensions.getWidth()][worldDimensions.getHeight()];
        this.complexWorldDimensions = new ComplexNumber(
            this.tab.getConfig().getMandelbrotZoom().getModel().getComplexWorldDimension().getRealX(),
            this.tab.getConfig().getMandelbrotZoom().getModel().getComplexWorldDimension().getImgY()
        );
        System.out.println(this.complexWorldDimensions.toString());
        this.complexCenterForMandelbrot = new ComplexNumber(
            this.tab.getConfig().getMandelbrotZoom().getModel().getComplexCenterForMandelbrot().getRealX(),
            this.tab.getConfig().getMandelbrotZoom().getModel().getComplexCenterForMandelbrot().getImgY()
        );
        System.out.println(this.complexCenterForMandelbrot.toString());
        ComplexNumber complexCenterForJulia = new ComplexNumber(
            this.tab.getConfig().getMandelbrotZoom().getModel().getComplexCenterForJulia().getRealX(),
            this.tab.getConfig().getMandelbrotZoom().getModel().getComplexCenterForJulia().getImgY()
        );
        this.setZoomLevel(1);
        this.setZoomCenter(complexCenterForMandelbrot);
        start();
    }

    public synchronized void start(){
        zoomLevel = ZOOM_LEVEL_START;
        for(int y = 0;y < this.worldDimensions.getY(); y++){
            for(int x=0; x < this.worldDimensions.getX(); x++){
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public synchronized int getCellStatusFor(int x,int y){
        return Math.max(lattice[x][y],0);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoords(LatticePoint turingPosition) {
        double myZoomLevel = (10 ^ this.getZoomLevel());
        double realX = (
            ( complexCenterForMandelbrot.getRealX() / myZoomLevel )
            + getZoomCenter().getRealX()
            + ( complexWorldDimensions.getRealX() * turingPosition.getX() )
            / ( worldDimensions.getX() * myZoomLevel )
        );
        double imgY = (
            ( complexCenterForMandelbrot.getImgY() / myZoomLevel )
            + getZoomCenter().getImgY()
            + ( complexWorldDimensions.getImgY() * turingPosition.getY() )
            / ( worldDimensions.getY() * myZoomLevel )
        );
        return new ComplexNumber(realX,imgY);
    }

    public synchronized boolean isInMandelbrotSet(LatticePoint turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoords(turingPosition);
        int x = turingPosition.getX();
        int y = turingPosition.getY();
        //System.out.println("x="+x+", y="+y);
        lattice[x][y] = position.computeMandelbrotSet();
        boolean isInMandelbrotSet = position.isInMandelbrotSet();
        System.out.println("x="+x+", y="+y+" -> "+ lattice[x][y]+", isInMandelbrotSet: "+isInMandelbrotSet);
        return isInMandelbrotSet;
    }

    public synchronized void fillTheOutsideWithColors(){
        for(int y=0; y<this.worldDimensions.getY(); y++){
            for(int x=0; x<this.worldDimensions.getX(); x++){
                if(lattice[x][y] == YET_UNCOMPUTED){
                    this.isInMandelbrotSet(new LatticePoint(x, y));
                }
            }
        }
    }

    public void zoomIn(LatticePoint zoomLatticePoint) {
        //log.info("zoomIntoTheMandelbrotSet: "+ zoomPoint +" - old:  "+this.getZoomCenter());
        this.inceaseZoomLevel();
        if(this.getZoomLevel() != 1){
            ComplexNumber complexCenter = new ComplexNumber(this.complexCenterForMandelbrot);
            complexCenterForZoomedMandelbrot.push(complexCenter);
            this.setZoomCenter(getComplexNumberFromLatticeCoords(zoomLatticePoint));
        } else {
            this.setZoomCenter(getComplexNumberFromLatticeCoords(zoomLatticePoint));
        }
        complexCenterForZoomedMandelbrot.push(this.getZoomCenter());
        //log.info("zoomPoint:     "+ zoomPoint);
        //log.info("zoomCenterNew: " + this.getZoomCenter() + " - zoomLevel:  "+ this.getZoomLevel());
        for(int y = 0; y < worldDimensions.getY(); y++){
            for(int x = 0; x < worldDimensions.getX(); x++){
                LatticePoint p = new LatticePoint(x, y);
                this.isInMandelbrotSet(p);
            }
        }
    }

    public void zoomOut() {
        //log.info("zoomOutOfTheMandelbrotSet: " + this.getZoomCenter());
        if(this.getZoomLevel()>1){
            this.decreaseZoomLevel();
            this.setZoomCenter(complexCenterForZoomedMandelbrot.pop());
        }
        //log.info("zoomCenter: " + this.getZoomCenter() + " - zoomLevel:  "+ this.getZoomLevel());
        for(int y = 0; y < worldDimensions.getY(); y++){
            for(int x = 0; x < worldDimensions.getX(); x++){
                LatticePoint p = new LatticePoint(x, y);
                this.isInMandelbrotSet(p);
            }
        }
    }

    public synchronized int inceaseZoomLevel() {
        return zoomLevel++;
    }

    public synchronized int decreaseZoomLevel() {
        return zoomLevel--;
    }

    public synchronized void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public synchronized int getZoomLevel() {
        return zoomLevel;
    }

    public synchronized ComplexNumber getZoomCenter() {
        return zoomCenter;
    }

    public synchronized void setZoomCenter(ComplexNumber zoomCenter) {
        this.zoomCenter = zoomCenter;
    }

}
