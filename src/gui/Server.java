/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

/**
 *
 * @author Lean
 */
public class Server extends JButton
{
    private final RoundedImagePanel IMAGE;
    private final List<Client> LIST_QUEUE;
    private MarvinImage imageIn;
    private MarvinImage imageOut;
    protected final JLabel TITLE;
    protected final JLabel SERVING;
    public final int ID;
    
    private void setupGUI()
    {
        IMAGE.setImage(imageOut);
        TITLE.setHorizontalAlignment(JLabel.CENTER);
        TITLE.setFont(new Font("Calibri", Font.BOLD, 15));
        SERVING.setHorizontalAlignment(JLabel.CENTER);
        
        SpringLayout layout = new SpringLayout();
        String n = SpringLayout.NORTH;
        String e = SpringLayout.EAST;
        String s = SpringLayout.SOUTH;
        String w = SpringLayout.WEST;
        String hc = SpringLayout.HORIZONTAL_CENTER;
        String vc = SpringLayout.VERTICAL_CENTER;
        Dimension imageSize = IMAGE.getSize();
        Dimension labelSize = TITLE.getSize();
        
        layout.putConstraint(n, IMAGE, 0, n, this);
        layout.putConstraint(e, IMAGE, 0, e, this);
        layout.putConstraint(s, IMAGE, -10, n, TITLE);
        layout.putConstraint(w, IMAGE, 0, w, this);
        
        layout.putConstraint(s, TITLE, 0, n, SERVING);
        layout.putConstraint(hc, TITLE, 0, hc, this);
        
        layout.putConstraint(s, SERVING, 0, s, this);
        layout.putConstraint(hc, SERVING, 0, hc, this);
        
        setContentAreaFilled(false);
        setBorder(BorderFactory.createLineBorder(new Color(122, 94, 72), 10));
        setLayout(layout);
        add(TITLE);
        add(SERVING);
        add(IMAGE);
    }
    
    private void configureGUI()
    {
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent ce)
            {
                Dimension d = IMAGE.getSize();
                
                if(d.width != 0 && d.height != 0)
                {
                    imageOut = imageIn.clone();
                    int s = Math.min(d.width, d.height);
                    
                    imageOut.resize(s, s);
                    imageOut.update();
                    IMAGE.setImage(imageOut);
                }
            }
        });
    }
    
    public Server(int id)
    {
        LIST_QUEUE = new ArrayList();
        TITLE = new JLabel("SERVER");
        SERVING = new JLabel("SERVING");
        IMAGE = new RoundedImagePanel();
        ID = id;
        imageIn = MarvinImageIO.loadImage("./img/img_default_photo.png");
        imageOut = imageIn.clone();
        
        setupGUI();
        configureGUI();
    }
    
    public MarvinImage getClientImage()
    {
        return(imageIn);
    }
    
    public void setClientImage(MarvinImage img)
    {
        imageIn = img;
        imageOut = imageIn.clone();
        Dimension d = IMAGE.getSize();
        
        imageOut.resize(d.width, d.height);
        imageOut.update();
        IMAGE.setImage(imageOut);
    }
    
    public void reload()
    {
        revalidate();
        repaint();
    }
}