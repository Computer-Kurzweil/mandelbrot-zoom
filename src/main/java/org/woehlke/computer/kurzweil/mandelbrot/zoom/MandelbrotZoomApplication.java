package org.woehlke.computer.kurzweil.mandelbrot.zoom;

import lombok.extern.log4j.Log4j2;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.application.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.mandelbrot.zoom.tabs.mandelbrot.zoom.MandelbrotZoomTab;

/**
 * Class with main Method for Starting the Desktop Application.
 *
 * @see MandelbrotZoomTab
 *
 * &copy; 2006 - 2008 Thomas Woehlke.
 * http://java.woehlke.org/simulated-evolution/
 * @author Thomas Woehlke
 */
@Log4j2
public class MandelbrotZoomApplication {

    private MandelbrotZoomApplication(String configFileName, String jarFilePath) {
        ComputerKurzweilProperties properties = ComputerKurzweilProperties.propertiesFactory(configFileName, jarFilePath);
        MandelbrotZoomTab simulatedEvolutionTab = new MandelbrotZoomTab(properties);
    }

    /**
     * Starting the Desktop Application
     * @param args CLI Parameter
     */
    public static void main(String[] args) {
        String configFileName = "application.yml";
        String jarFilePath = "target/mandelbrot-zoom.jar";
        MandelbrotZoomApplication application = new MandelbrotZoomApplication(configFileName,jarFilePath);
    }
}
