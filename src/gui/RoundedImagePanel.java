/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JLabel;
import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;

/**
 *
 * @author Lean
 */
public class RoundedImagePanel extends JButton
{
    private MarvinImage imageIn;
    private MarvinImage imageOut;
    
    private void applyQualityRenderingHints(Graphics2D g2d)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
    
    public RoundedImagePanel()
    {
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.setAlignmentX(JButton.CENTER_ALIGNMENT);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        Rectangle r1 = getBounds();
        Rectangle r2 = new Rectangle(imageOut.getWidth(), imageOut.getHeight());
        
        g2.drawImage(imageOut.getBufferedImage(), (r1.width / 2) - (r2.width / 2), 0, null);
    }
    
    public void setImage(MarvinImage img)
    {
        imageIn = img;
        BufferedImage master = img.getBufferedImage();

        int diameter = Math.min(master.getWidth(), master.getHeight());
        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mask.createGraphics();

        applyQualityRenderingHints(g2d);
        g2d.fillOval(1, 1, diameter, diameter);
        g2d.dispose();

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();

        applyQualityRenderingHints(g2d);

        int x = (diameter - master.getWidth()) / 2;
        int y = (diameter - master.getHeight()) / 2;

        g2d.drawImage(master, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();
        
        imageOut = new MarvinImage(masked);
        reload();
    }
    
    public void reload()
    {
        revalidate();
        repaint();
    }
}