package org.woehlke.computer.kurzweil.mandelbrot.zoom.view.panels;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Mandelbrot Set drawn by a Turing Machine.
 * (C) 2006 - 2022 Thomas Woehlke.
 * @author Thomas Woehlke
 *
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/mandelbrot-zoom">Github Repository</a>
 * @see <a href="https://java.woehlke.org/mandelbrot-zoom/">Maven Project Repository</a>
 *
 * @see ApplicationModel
 *
 * @see JPanel
 * @see ActionListener
 *
 * Created by tw on 16.12.2019.
 */
public class PanelButtons extends JPanel implements ActionListener {

    final static long serialVersionUID = 242L;

    private volatile JLabel copyright;
    private volatile JButton zoomOut;
    private final ApplicationFrame tab;

    public PanelButtons(ApplicationFrame tab) {
        this.tab = tab;
        this.copyright = new JLabel(tab.getConfig().getMandelbrotZoom().getView().getCopyright());
        this.zoomOut = new JButton(tab.getConfig().getMandelbrotZoom().getView().getButtonsZoomOut());
        int hgap = 16;
        int vgap = 2;
        this.copyright.setLayout(new FlowLayout( FlowLayout.RIGHT, hgap, vgap));
        this.zoomOut.setLayout(new FlowLayout( FlowLayout.LEFT, hgap, vgap));
        this.setLayout(new FlowLayout( FlowLayout.CENTER, hgap, vgap));
        this.add(this.copyright);
        this.add(this.zoomOut);
        this.zoomOut.addActionListener(this);
    }

    /**
     * TODO write doc.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.zoomOut){
            this.tab.getModel().zoomOut();
            this.tab.getCanvas().repaint();
            this.tab.repaint();
        }
    }
}
