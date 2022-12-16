package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.fractal;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.common.Point;


/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-julia">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-julia/">Maven Project Repository</a>
 *
 * @see ComplexNumber
 * @see Point
 *
 * Created by tw on 16.12.2019.
 */
public class GaussianNumberPlane {

    private volatile int[][] lattice;

    private final Point worldDimensions;

    public final static int YET_UNCOMPUTED = -1;

    private final static double complexWorldDimensionRealX = 3.2d;
    private final static double complexWorldDimensionImgY = 2.34d;
    private final static double complexCenterForMandelbrotRealX = -2.2f;
    private final static double complexCenterForMandelbrotImgY = -1.17f;
    private final static double complexCenterForJuliaRealX = -1.6d;
    private final static double complexCenterForJuliaImgY = -1.17d;

    private volatile ComplexNumber complexWorldDimensions;
    private volatile ComplexNumber complexCenterForMandelbrot;
    private volatile ComplexNumber complexCenterForJulia;

    public GaussianNumberPlane(ApplicationModel model) {
        this.worldDimensions = model.getWorldDimensions();
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
        start();
    }

    public synchronized void start() {
        for (int y = 0; y < this.worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return (lattice[x][y]) < 0 ? 0 : lattice[x][y];
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForJulia(Point turingPosition) {
        double realX = complexCenterForJulia.getReal()
            + (complexWorldDimensions.getReal() * turingPosition.getX()) / worldDimensions.getX();
        double imgY = complexCenterForJulia.getImg()
            + (complexWorldDimensions.getImg() * turingPosition.getY()) / worldDimensions.getY();
        return new ComplexNumber(realX, imgY);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForMandelbrot(Point turingPosition) {
        double realX = (
            complexCenterForMandelbrot.getReal()
                + (complexWorldDimensions.getReal() * turingPosition.getX())
                / worldDimensions.getX()
        );
        double imgY = (
            complexCenterForMandelbrot.getImg()
                + (complexWorldDimensions.getImg() * turingPosition.getY())
                / worldDimensions.getY()
        );
        return new ComplexNumber(realX, imgY);
    }

    public synchronized boolean isInMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForMandelbrot(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public synchronized void fillTheOutsideWithColors() {
        for (int y = 0; y < worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                if (lattice[x][y] == YET_UNCOMPUTED) {
                    this.isInMandelbrotSet(new Point(x, y));
                }
            }
        }
    }

    private synchronized void computeTheJuliaSetForC(ComplexNumber c) {
        for (int y = 0; y < worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                Point zPoint = new Point(x, y);
                ComplexNumber z = this.getComplexNumberFromLatticeCoordsForJulia(zPoint);
                lattice[x][y] = z.computeJuliaSet(c);
            }
        }
    }

    public synchronized void computeTheJuliaSetFor(Point pointFromMandelbrotSet) {
        ComplexNumber complexNumberForJuliaSetC =
            getComplexNumberFromLatticeCoordsForMandelbrot(pointFromMandelbrotSet);
        computeTheJuliaSetForC(complexNumberForJuliaSetC);
    }
}
