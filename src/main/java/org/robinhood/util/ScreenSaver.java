package org.robinhood.util;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ScreenSaver do print screen.
 */
@Data
public class ScreenSaver {
    /**
     * Template of filename.
     */
    private final String NAME_TEMPLATE = "SCREEN_#";
    /**
     * Current amount of screens in target dir.
     */
    private int fileNumber = 0;
    /**
     * Path to target dir.
     */
    private String baseDir;
    /**
     * Maximum files for save in base dir.
     */
    private int maxAmountFiles;

    public ScreenSaver(String baseDir, int maxAmountFiles) {
        this.maxAmountFiles = maxAmountFiles;
        this.baseDir = baseDir;
    }

    /**
     * Do print screen.
     */
    public boolean doScreen() throws IOException, AWTException {

        final BufferedImage buffImage = grabScreen();

        if (buffImage == null) throw new IllegalArgumentException();

        if (fileNumber >= maxAmountFiles) deleteOldScreens();


        final File file = new File(
                baseDir,
                NAME_TEMPLATE.replace("#", Integer.toString(fileNumber++)));

        return ImageIO.write(buffImage, "png", file);
    }

    /**
     * Delete all old screens files.
     */
    private void deleteOldScreens() {

        final File baseDirectory = new File(baseDir);

        final File[] garbageImg = baseDirectory.listFiles();

        if (!baseDirectory.isDirectory() || garbageImg == null) {
            throw new IllegalArgumentException();
        }

        if (garbageImg.length == 0) return;

        fileNumber = 0;

        for (File file : garbageImg) {
            file.delete();
        }
    }

    /**
     * Print Screen.
     *
     * @return byte representation of screen image.
     */
    private BufferedImage grabScreen() throws AWTException {

        final Robot robot = new Robot();

        final Dimension screenSze = Toolkit.getDefaultToolkit().getScreenSize();

        final Rectangle rectangle = new Rectangle(screenSze);

        return robot.createScreenCapture(rectangle);
    }
}