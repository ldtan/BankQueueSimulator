/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import obj.Time;

/**
 *
 * @author Lean
 */
public class Test
{
    public static void main(String[] args)
    {
        MainFrame mainFrame = new MainFrame(3, 20, new Time(), new Time());
        
        mainFrame.loadGUI();
        /*JFrame frame = new JFrame("Test");
        Bank bank = new Bank();
        Server[] array_server = new Server[3];
        Client[] array_client = new Client[20];
        
        for(int i = 0; i < array_server.length; i++)
        {
            array_server[i] = new Server(i + 1);
            
            bank.addToServer(array_server[i]);
        }
        
        for(int i = 0; i < array_client.length; i++)
        {
            array_client[i] = new Client(i);
            
            if(i < 3)
            {
                bank.addToServing(array_server[i], array_client[i]);
            }
            
            else
            {
                bank.addToWaiting(array_client[i]);
            }
        }
        
        //frame.setLayout(new GridLayout(1, 3));
        //frame.add(client);
        //frame.add(teller);
        frame.add(bank);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setVisible(true);*/
    }
}