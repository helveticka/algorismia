package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class IntegerCellDrawable implements CellDrawable {
    private final int value;

    public IntegerCellDrawable(int value) {
        this.value = value;
    }
    
    @Override
    public Image FiguraFBlanc() {
        return this.asImage(Color.WHITE);
    }

    @Override
    public Image FiguraFNegre() {
        return this.asImage(Color.BLACK);
    }
    /**
     * Mètode que dibuixa el cuadrats del tauler d'escacs
     * amb el nombre de la iteració corresponent
     * @param backgroundColor color de fons
     * @return la imatge del nombre indicat en font 8-bit RGBA
     */
    private Image asImage(Color backgroundColor) {
        String text = Integer.toString(this.value);
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);// Represents an image with 8-bit RGBA color components packed into integer pixels.
        Graphics2D graphics2d = image.createGraphics();
        Font font = new Font("TimesNewRoman", Font.BOLD, 24);
        graphics2d.setFont(font);
        FontMetrics fontmetrics = graphics2d.getFontMetrics();
        int width = fontmetrics.stringWidth(text);
        int height = fontmetrics.getHeight();
        graphics2d.dispose();
 
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2d = image.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2d.setFont(font);
        fontmetrics = graphics2d.getFontMetrics();
        graphics2d.setColor(backgroundColor);
        graphics2d.fillRect(0, 0, width, height);
        graphics2d.setColor(Color.GREEN);
        graphics2d.drawString(text, 0, fontmetrics.getAscent());
        graphics2d.dispose();
        
        return image;
    }
    
}
