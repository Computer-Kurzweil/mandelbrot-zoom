package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.complexnumer;

import java.util.Objects;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see ComplexNumberPlane
 *
 * Created by tw on 18.08.15.
 */
//@Getter
//@Setter
public class ComplexNumber {

    private volatile double realX;
    private volatile double imgY;

    private volatile int iterations;
    private volatile boolean inMandelbrotSet;
    private volatile boolean inJuliaSet;

    public final static int MAX_ITERATIONS = 128;
    private final static double DIVERGENCE_THRESHOLD = 4.0d;

    public double getRealX() {
        return realX;
    }

    public double getImgY() {
        return imgY;
    }

    public ComplexNumber() {
        this.realX = 0.0d;
        this.imgY = 0.0d;
        this.iterations=0;
        this.inMandelbrotSet=false;
        this.inJuliaSet=false;
    }

    public ComplexNumber(ComplexNumber complexNumber) {
        this.realX = complexNumber.realX;
        this.imgY = complexNumber.imgY;
        this.iterations=complexNumber.iterations;
        this.inMandelbrotSet=complexNumber.inMandelbrotSet;
        this.inJuliaSet=complexNumber.inJuliaSet;
    }

    public ComplexNumber(double realX, double imgY) {
        this.realX = realX;
        this.imgY = imgY;
        this.iterations=0;
        this.inMandelbrotSet=false;
        this.inJuliaSet=false;
    }

    public ComplexNumber plus(ComplexNumber complexNumber){
        double newRealZ = this.realX + complexNumber.realX;
        double newImgZ = this.imgY + complexNumber.imgY;
        return new ComplexNumber(newRealZ,newImgZ);
    }

    public ComplexNumber square(){
        double realZ= realX;
        double imgZ= imgY;
        double newRealZ=realZ*realZ-imgZ*imgZ;
        double newImgZ=2*realZ*imgZ;
        return new ComplexNumber(newRealZ,newImgZ);
    }

    public synchronized int computeMandelbrotSet() {
        int iterationsTmp = 0;
        ComplexNumber z = new ComplexNumber();
        do {
            iterationsTmp++;
            z = z.square().plus(this);
        } while (z.isNotDivergent() && (iterationsTmp < MAX_ITERATIONS));
        this.inMandelbrotSet = z.isNotDivergent();
        this.iterations = this.inMandelbrotSet?0:iterationsTmp;
        return this.iterations;
    }

    public synchronized int computeJuliaSet(ComplexNumber c) {
        int iterationsTmp = 0;
        ComplexNumber z = new ComplexNumber(this);
        do {
            iterationsTmp++;
            z = z.square().plus(c);
        } while (z.isNotDivergent() && (iterationsTmp < MAX_ITERATIONS));
        this.inJuliaSet = z.isNotDivergent();
        this.iterations = this.inJuliaSet?0:iterationsTmp;
        return this.iterations;
    }

    public synchronized boolean isInMandelbrotSet() {
        return inMandelbrotSet;
    }

    public synchronized boolean isInJuliaSet() {
        return inJuliaSet;
    }

    public synchronized boolean isNotDivergent(){
        return (( realX * realX + imgY * imgY) < DIVERGENCE_THRESHOLD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexNumber)) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.getRealX(), getRealX()) == 0 &&
            Double.compare(that.getImgY(), getImgY()) == 0 &&
            iterations == that.iterations &&
            isInMandelbrotSet() == that.isInMandelbrotSet() &&
            isInJuliaSet() == that.isInJuliaSet();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRealX(), getImgY(), iterations, isInMandelbrotSet(), isInJuliaSet());
    }

    @Override
    public String toString() {
        return "ComplexNumber{" +
            "real=" + realX +
            ", img=" + imgY +
            ", iterations=" + iterations +
            ", inMandelbrotSet=" + inMandelbrotSet +
            ", inJuliaSet=" + inJuliaSet +
            '}';
    }
}
