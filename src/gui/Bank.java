/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import obj.Time;

/**
 *
 * @author Lean
 */
public class Bank extends MarvinImagePanel
{
    private final JButton JPANEL_WAITING;
    private final JButton JPANEL_SERVER;
    private final JButton JPANEL_SERVING;
    private static final MarvinImage IMG_FLOOR = MarvinImageIO.loadImage("./img/img_floor.png");
    private final List<Component> LIST_WAITING;
    private final Map<Component, Component> MAP_SERVING;
    private final List<Component> LIST_SERVER;
    protected JTextField JTEXTFIELD_CLOCK;
    protected Time startTime;
    protected Time endTime;
    
    private void setupGUI()
    {
        SpringLayout layout = new SpringLayout();
        String n = SpringLayout.NORTH;
        String e = SpringLayout.EAST;
        String s = SpringLayout.SOUTH;
        String w = SpringLayout.WEST;
        String hc = SpringLayout.HORIZONTAL_CENTER;
        String vc = SpringLayout.VERTICAL_CENTER;
        
        JTEXTFIELD_CLOCK.setFont(new Font("Calibri", Font.BOLD, 25));
        JTEXTFIELD_CLOCK.setEditable(false);
        JTEXTFIELD_CLOCK.setHorizontalAlignment(JTextField.CENTER);
        JTEXTFIELD_CLOCK.setColumns(6);
        JTEXTFIELD_CLOCK.setBorder(BorderFactory.createLineBorder(new Color(122, 94, 72), 5));
        
        JPANEL_SERVER.setContentAreaFilled(false);
        JPANEL_SERVER.setBorderPainted(false);
        
        JPANEL_WAITING.setContentAreaFilled(false);
        JPANEL_WAITING.setBorderPainted(false);
        
        JPANEL_SERVING.setContentAreaFilled(false);
        JPANEL_SERVING.setBorderPainted(false);
        
        layout.putConstraint(n, JTEXTFIELD_CLOCK, 20, n, this);
        layout.putConstraint(hc, JTEXTFIELD_CLOCK, 0, hc, this);
        
        layout.putConstraint(n, JPANEL_SERVER, 20, s, JTEXTFIELD_CLOCK);
        layout.putConstraint(e, JPANEL_SERVER, 0, e, this);
        layout.putConstraint(s, JPANEL_SERVER, 200, n, JPANEL_SERVER);
        layout.putConstraint(w, JPANEL_SERVER, 0, w, this);
        
        layout.putConstraint(n, JPANEL_SERVING, 0, s, JPANEL_SERVER);
        layout.putConstraint(e, JPANEL_SERVING, 0, e, JPANEL_SERVER);
        layout.putConstraint(s, JPANEL_SERVING, 100, n, JPANEL_SERVING);
        layout.putConstraint(w, JPANEL_SERVING, 0, w, JPANEL_SERVER);
        
        layout.putConstraint(n, JPANEL_WAITING, 50, s, JPANEL_SERVING);
        layout.putConstraint(e, JPANEL_WAITING, 0, e, this);
        layout.putConstraint(s, JPANEL_WAITING, 0, s, this);
        layout.putConstraint(w, JPANEL_WAITING, 0, w, this);
        
        setImage(IMG_FLOOR);
        setLayout(layout);
        add(JTEXTFIELD_CLOCK);
        add(JPANEL_SERVER);
        add(JPANEL_SERVING);
        add(JPANEL_WAITING);
    }
    
    private void configureGUI()
    {
        /*addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent ce)
            {
                Dimension d = getSize();
                MarvinImage imageOut = IMG_FLOOR.clone();
                
                imageOut.resize(d.width, d.height);
                imageOut.update();
                setImage(imageOut);
            }
            
        });*/
    }
    
    public Bank()
    {
        JPANEL_WAITING = new JButton();
        JPANEL_SERVER = new JButton();
        JPANEL_SERVING = new JButton();
        LIST_WAITING = new ArrayList();
        MAP_SERVING = new HashMap();
        LIST_SERVER = new ArrayList();
        JTEXTFIELD_CLOCK = new JTextField("00:00:00");
        
        setupGUI();
        configureGUI();
    }
    
    public int serverCount()
    {
        return(LIST_SERVER.size());
    }
    
    public boolean addToServer(Component c)
    {
        if(LIST_SERVER.contains(c))
        {
            return(false);
        }
        
        LIST_SERVER.add(c);
        JPANEL_SERVER.setLayout(new GridLayout(1, JPANEL_SERVER.getComponentCount() + 1));
        JPANEL_SERVER.add(c);
        
        return(true);
    }
    
    public boolean removeToServer(Component c)
    {
        if(!LIST_SERVER.contains(c))
        {
            return(false);
        }
        
        LIST_SERVER.remove(c);
        JPANEL_SERVER.remove(c);
        JPANEL_SERVER.setLayout(new GridLayout(1, JPANEL_SERVER.getComponentCount()));
        
        return(true);
    }
    
    public int servingCount()
    {
        return(MAP_SERVING.size());
    }
    
    public boolean addToServing(Component c1, Component c2)
    {
        if(MAP_SERVING.containsValue(c2) || !LIST_SERVER.contains(c1))
        {
            return(false);
        }
        
        MAP_SERVING.put(c1, c2);
        JPANEL_SERVING.removeAll();
        JPANEL_SERVING.setLayout(new GridLayout(1, MAP_SERVING.size()));
        
        for(Component com1 : LIST_SERVER)
        {
            Component com2 = MAP_SERVING.get(com1);
            JPANEL_SERVING.add(com2 != null ? com2 : new JLabel());
        }
        
        return(true);
    }
    
    public boolean removeToServing(Component c)
    {
        if(!MAP_SERVING.containsKey(c))
        {
            return(false);
        }
        
        MAP_SERVING.remove(c);
        JPANEL_SERVING.remove(c);
        JPANEL_SERVING.setLayout(new GridLayout(1, MAP_SERVING.size()));
        
        return(true);
    }
    
    public int waitingCount()
    {
        return(LIST_WAITING.size());
    }
    
    public boolean addToWaiting(Component c)
    {
        if(LIST_WAITING.contains(c))
        {
            return(false);
        }
        
        LIST_WAITING.add(c);
        JPANEL_WAITING.setLayout(new GridLayout((JPANEL_WAITING.getComponentCount() / 10) + 1, 10));
        JPANEL_WAITING.add(c);
        
        return(true);
    }
    
    public boolean removeToWaiting(Component c)
    {
        if(!LIST_WAITING.contains(c))
        {
            return(false);
        }
        
        LIST_WAITING.remove(c);
        JPANEL_WAITING.remove(c);
        JPANEL_WAITING.setLayout(new GridLayout((JPANEL_WAITING.getComponentCount() / 10) + 1, 10));
        
        return(true);
    }
}