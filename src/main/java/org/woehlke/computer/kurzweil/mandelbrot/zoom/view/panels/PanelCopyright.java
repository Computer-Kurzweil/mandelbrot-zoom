package org.woehlke.computer.kurzweil.mandelbrot.zoom.view.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 *
 * Created by tw on 16.12.2019.
 */
public class PanelCopyright extends JPanel {

    private final static long serialVersionUID = 242L;

    public PanelCopyright(String subtitle) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel(subtitle));
    }

}
