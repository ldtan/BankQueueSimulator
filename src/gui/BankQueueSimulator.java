/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import obj.Time;

/**
 *
 * @author LIKE_MIANN
 */
public class BankQueueSimulator implements Runnable
{
    private MainFrame mainFrame;
    
    @Override
    public void run()
    {
        mainFrame = new MainFrame(3, 20, new Time(), new Time());
        mainFrame.loadGUI();
    }
    
    public static void main(String[] args)
    {
        Thread bankQueueSimulator = new Thread(new BankQueueSimulator());
        bankQueueSimulator.start();
        Time t = new Time();
        
        t.adjustSecond(2160);
        System.out.println(Time.random(new Time(0, 0, 0), new Time(0, 2, 5)));
        System.out.println(Math.pow(60, 3));
        System.out.println(60 % 60);
    }
}
