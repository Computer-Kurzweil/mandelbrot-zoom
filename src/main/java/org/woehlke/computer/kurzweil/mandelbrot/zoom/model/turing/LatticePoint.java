package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing;

import lombok.Getter;
import lombok.Setter;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.complexnumer.ComplexNumberPlane;

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
 * Date: 04.02.2006
 * Time: 23:47:05
 */
@Getter
@Setter
public class LatticePoint {

    private volatile int x = 0;
    private volatile int y = 0;

    public LatticePoint() {
    }

    public LatticePoint(LatticePoint p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public LatticePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public int getWidth(){
        return x;
    }

    public int getHeight() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatticePoint)) return false;
        LatticePoint latticePoint = (LatticePoint) o;
        return getX() == latticePoint.getX() &&
            getY() == latticePoint.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "Point{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
