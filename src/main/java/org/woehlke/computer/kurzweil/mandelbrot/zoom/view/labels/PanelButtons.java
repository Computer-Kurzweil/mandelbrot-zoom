package org.woehlke.computer.kurzweil.mandelbrot.zoom.view.labels;

import org.woehlke.computer.kurzweil.mandelbrot.zoom.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.model.ApplicationModel;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.view.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelButtons extends JPanel implements ActionListener {

    final static long serialVersionUID = 242L;

    private volatile JLabel copyright;
    private volatile JButton zoomOut;
    private final ApplicationFrame tab;
    private final ApplicationModel model;

    public PanelButtons(ApplicationModel model, ApplicationFrame tab, ComputerKurzweilProperties config ) {
        this.tab = tab;
        this.model = model;
        this.copyright = new JLabel(config.getMandelbrotZoom().getView().getCopyright());
        this.zoomOut = new JButton(config.getMandelbrotZoom().getView().getButtonsZoomOut());
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
            this.model.zoomOut();
            this.tab.getCanvas().repaint();
            this.tab.repaint();
        }
    }
}
