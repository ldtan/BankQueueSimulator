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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import obj.Time;

/**
 *
 * @author Lean
 */
public class Client extends JButton
{
    private final RoundedImagePanel IMAGE;
    private Server serverHandler;
    private MarvinImage imageIn;
    private MarvinImage imageOut;
    private String transactionType;
    protected final JLabel TITLE;
    protected final JLabel TIME;
    protected Time arrivalTime;
    protected Time serviceStart;
    protected Time serviceEnd;
    public static final String SINGLE = "single transaction";
    public static final String MULTIPLE = "multiple transaction";
    public final int ID;
    
    private void setupGUI()
    {
        IMAGE.setImage(imageOut);
        TITLE.setHorizontalAlignment(JLabel.CENTER);
        TITLE.setFont(new Font("Calibri", Font.BOLD, 15));
        TIME.setHorizontalAlignment(JLabel.CENTER);
        
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
        
        layout.putConstraint(s, TITLE, 0, n, TIME);
        layout.putConstraint(hc, TITLE, 0, hc, this);
        
        layout.putConstraint(s, TIME, 0, s, this);
        layout.putConstraint(hc, TIME, 0, hc, this);
        
        super.setBorderPainted(false);
        super.setContentAreaFilled(false);
        setLayout(layout);
        add(IMAGE);
        add(TITLE);
        add(TIME);
    }
    
    private void configureGUI()
    {
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent ce)
            {
                Dimension d = IMAGE.getSize();
                
                if(d.width > 0 && d.height > 0)
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
    
    public Client(int id)
    {
        TITLE = new JLabel("CLIENT");
        TIME = new JLabel("00:00:00");
        IMAGE = new RoundedImagePanel();
        ID = id;
        imageIn = MarvinImageIO.loadImage("./img/img_default_photo.png");
        imageOut = imageIn.clone();
        transactionType = SINGLE;
        
        setupGUI();
        configureGUI();
    }
    
    public String getTransactionType()
    {
        return(transactionType);
    }
    
    public void setTransactionType(String t)
    {
        transactionType = t.equals(SINGLE) || t.equals(MULTIPLE) ? t : transactionType;
    }
    
    public Server getServerHandler()
    {
        return(serverHandler);
    }
    
    public void setServerHandler(Server s)
    {
        serverHandler = s;
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