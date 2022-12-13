package org.woehlke.computer.kurzweil.mandelbrot.zoom.model.turing;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 *
 * Created by tw on 18.08.15.
 */
public enum TuringPhase {
    SEARCH_THE_SET,
    WALK_AROUND_THE_SET,
    FILL_THE_OUTSIDE_WITH_COLOR,
    FINISHED
}
