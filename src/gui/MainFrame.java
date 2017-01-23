/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.GridLayout;
import javax.swing.JFrame;
import obj.Time;

public class MainFrame extends JFrame{
    
    private Server manager;
    private Server[] array_teller;
    private Client[] array_client;
    private Bank bank;
    private Thread simulator;
    
    private void setupGUI(){
        
        bank.JTEXTFIELD_CLOCK.setText(bank.startTime.toString());
        manager.TITLE.setText("Manager");
        
        for(int i = 0; i < array_teller.length; i++){
            array_teller[i] = new Server(i + 1);
            
            array_teller[i].TITLE.setText("Teller#" + (i + 1));
            bank.addToServer(array_teller[i]);
        }
        
        bank.addToServer(manager);
        
        for(int i = 0; i < array_client.length; i++)
        {
            array_client[i] = new Client(i + 1);
            array_client[i].arrivalTime = Time.random(new Time(0, 0, 100), new Time(0, 0, 106));
            
            array_client[i].TITLE.setText("CLIENT#" + (i + 1));
            array_client[i].TIME.setText(array_client[i].arrivalTime.toString());
            
            if(bank.startTime.lessThan(array_client[i].arrivalTime))
            {
                bank.addToWaiting(array_client[i]);
            }
        }
        
        setLayout(new GridLayout(1, 1));
        add(bank);
    }
    
    public MainFrame(int tellerCount, int clientCount, Time timeStart, Time timeEnd)
    {
        manager = new Server(0);
        array_teller = new Server[tellerCount];
        array_client = new Client[clientCount];
        bank = new Bank();
        bank.startTime = timeStart;
        bank.endTime = timeEnd;
        
        setupGUI();
    }
    
    public void loadGUI()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setVisible(true);
    }
}