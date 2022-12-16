package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point;

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

    public final static int YET_UNCOMPUTED = -1;

    private volatile ComplexNumber complexWorldDimensions;
    private volatile ComplexNumber complexCenterForMandelbrot;
    private volatile ComplexNumber complexCenterForJulia;

    public volatile int zoomLevel;

    private volatile Deque<ComplexNumber> complexCenterForZoomedMandelbrot = new ArrayDeque<>();

    private volatile ComplexNumber zoomCenter;

    private final ApplicationModel model;
    private volatile Point worldDimensions;

    public GaussianNumberPlane(ApplicationModel model) {
        this.model = model;
        this.worldDimensions = this.model.getWorldDimensions();
        this.lattice = new int[worldDimensions.getWidth()][worldDimensions.getHeight()];
        this.complexWorldDimensions = new ComplexNumber(
            this.model.getConfig().getMandelbrotZoom().getModel().getComplexWorldDimension().getRealX(),
            this.model.getConfig().getMandelbrotZoom().getModel().getComplexWorldDimension().getImgY()
        );
        this.complexCenterForMandelbrot = new ComplexNumber(
            this.model.getConfig().getMandelbrotZoom().getModel().getComplexCenterForMandelbrot().getRealX(),
            this.model.getConfig().getMandelbrotZoom().getModel().getComplexCenterForMandelbrot().getImgY()
        );
        this.complexCenterForJulia = new ComplexNumber(
            this.model.getConfig().getMandelbrotZoom().getModel().getComplexCenterForJulia().getRealX(),
            this.model.getConfig().getMandelbrotZoom().getModel().getComplexCenterForJulia().getImgY()
        );
        this.setZoomLevel(1);
        this.setZoomCenter(complexCenterForMandelbrot);
        start();
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
        return Math.max(lattice[x][y],0);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoords(Point turingPosition) {
        double realX = (
            ( complexCenterForMandelbrot.getRealX() / this.getZoomLevel() )
            + getZoomCenter().getRealX()
            + ( complexWorldDimensions.getRealX() * turingPosition.getX() )
            / ( worldDimensions.getX() * this.getZoomLevel() )
        );
        double imgY = (
            ( complexCenterForMandelbrot.getImgY() / this.getZoomLevel() )
            + getZoomCenter().getImgY()
            + ( complexWorldDimensions.getImgY() * turingPosition.getY() )
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
        if(this.getZoomLevel() != 1){
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
