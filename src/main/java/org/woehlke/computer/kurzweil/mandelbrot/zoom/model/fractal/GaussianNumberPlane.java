package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
//import java.util.logging.Logger;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal.ComplexNumber
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point
 * @see org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel
 *
 * @see java.util.Deque
 * @see java.util.ArrayDeque
 *
 * Created by tw on 16.12.2019.
 */
public class GaussianNumberPlane implements Serializable {

    final static long serialVersionUID = 242L;

    private volatile int[][] lattice;

    private final Point worldDimensions;

    public final static int YET_UNCOMPUTED = -1;

    private final static double complexWorldDimensionRealX = 3.2d;
    private final static double complexWorldDimensionImgY = 2.34d;

    private final static double complexCenterForMandelbrotRealX = -2.2f;
    private final static double complexCenterForMandelbrotImgY = -1.17f;

    private final static double complexCenterForJuliaRealX = -1.6d;
    private final static double complexCenterForJuliaImgY =  -1.17d;

    private volatile ComplexNumber complexWorldDimensions;
    private volatile ComplexNumber complexCenterForMandelbrot;
    private volatile ComplexNumber complexCenterForJulia;

    public volatile int zoomLevel;

    private volatile Deque<ComplexNumber> complexCenterForZoomedMandelbrot = new ArrayDeque<>();

    private volatile ComplexNumber zoomCenter;

    private final ApplicationFrame tab;

    public GaussianNumberPlane(ApplicationFrame tab) {
        this.tab = tab;
        this.worldDimensions = tab.getConfig().getWorldDimensions();
        this.lattice = new int[worldDimensions.getWidth()][worldDimensions.getHeight()];
        this.complexWorldDimensions = new ComplexNumber(
            complexWorldDimensionRealX,
            complexWorldDimensionImgY
        );
        this.complexCenterForMandelbrot = new ComplexNumber(
            complexCenterForMandelbrotRealX,
            complexCenterForMandelbrotImgY
        );
        this.complexCenterForJulia = new ComplexNumber(
            complexCenterForJuliaRealX,
            complexCenterForJuliaImgY
        );
        setModeZoom();
        start();
    }

    public void setModeZoom() {
        this.setZoomLevel(1);
        this.setZoomCenter(complexCenterForMandelbrot);
    }

    public synchronized void start(){
        zoomLevel = 1;
        for(int y = 0;y < this.worldDimensions.getY(); y++){
            for(int x=0; x < worldDimensions.getX(); x++){
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public synchronized int getCellStatusFor(int x,int y){
        return (lattice[x][y])<0?0:lattice[x][y];
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoords(Point turingPosition) {
        double realX = (
            ( complexCenterForMandelbrot.getReal() / this.getZoomLevel() )
            + getZoomCenter().getReal()
            + ( complexWorldDimensions.getReal() * turingPosition.getX() )
            / ( worldDimensions.getX() * this.getZoomLevel() )
        );
        double imgY = (
            ( complexCenterForMandelbrot.getImg() / this.getZoomLevel() )
            + getZoomCenter().getImg()
            + ( complexWorldDimensions.getImg() * turingPosition.getY() )
            / ( worldDimensions.getY() * this.getZoomLevel() )
        );
        return new ComplexNumber(realX,imgY);
    }

    public synchronized boolean isInMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoords(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public synchronized void fillTheOutsideWithColors(){
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                if(lattice[x][y] == YET_UNCOMPUTED){
                    this.isInMandelbrotSet(new Point(x, y));
                }
            }
        }
    }

    public void zoomIn(Point zoomPoint) {
        //log.info("zoomIntoTheMandelbrotSet: "+ zoomPoint +" - old:  "+this.getZoomCenter());
        this.inceaseZoomLevel();
        if(this.getZoomLevel() == 2){
            ComplexNumber complexCenter = new ComplexNumber(this.complexCenterForMandelbrot);
            complexCenterForZoomedMandelbrot.push(complexCenter);
            this.setZoomCenter(getComplexNumberFromLatticeCoords(zoomPoint));
        } else {
            this.setZoomCenter(getComplexNumberFromLatticeCoords(zoomPoint));
        }
        complexCenterForZoomedMandelbrot.push(this.getZoomCenter());
        //log.info("zoomPoint:     "+ zoomPoint);
        //log.info("zoomCenterNew: " + this.getZoomCenter() + " - zoomLevel:  "+ this.getZoomLevel());
        for(int y = 0; y < worldDimensions.getY(); y++){
            for(int x = 0; x < worldDimensions.getX(); x++){
                Point p = new Point(x, y);
                this.isInMandelbrotSet(p);
            }
        }
    }

    public void zoomOut() {
        //log.info("zoomOutOfTheMandelbrotSet: " + this.getZoomCenter());
        if(this.getZoomLevel()>1){
            this.deceaseZoomLevel();
            this.setZoomCenter(complexCenterForZoomedMandelbrot.pop());
        }
        //log.info("zoomCenter: " + this.getZoomCenter() + " - zoomLevel:  "+ this.getZoomLevel());
        for(int y = 0; y < worldDimensions.getY(); y++){
            for(int x = 0; x < worldDimensions.getX(); x++){
                Point p = new Point(x, y);
                this.isInMandelbrotSet(p);
            }
        }
    }

    public synchronized int getZoomLevel() {
        return zoomLevel;
    }

    public synchronized int inceaseZoomLevel() {
        return zoomLevel *= 2;
    }

    public synchronized int deceaseZoomLevel() {
        return zoomLevel /= 2;
    }

    public synchronized void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public synchronized ComplexNumber getZoomCenter() {
        return zoomCenter;
    }

    public synchronized void setZoomCenter(ComplexNumber zoomCenter) {
        this.zoomCenter = zoomCenter;
    }

}
