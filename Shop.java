package core;

import items.empty;
import items.healthPotion;
import items.item;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import weapon.*;


public class Shop extends JFrame implements ActionListener
{
    private JLabel shopHolder;
    private JLabel buttonHolder;
    private JLabel background;
    private JLabel goldSupply;
    private JButton shopItemOne;
    private JButton shopItemTwo;
    private JButton shopItemThree;
    private JButton shopItemFour;
    private JButton exit;
    item[] shopArray = new item[4];
    ArrayList<item> itemList = new ArrayList<>();
    int randomItem;
    final int UL;
    final int LL;
    final int range;
    
    character temp;
    
    /**
     * shop constructor
     * @param c
     * @param cha
     */
    public Shop(Container c, character cha)
    {
        temp = cha;

        //items that can be in shop
        itemList.add(new healthPotion());
        itemList.add(new empty());

        //random vars
        UL = itemList.size() - 1;
        LL = 0;
        range = UL - LL + 1;

        //items being added to shop
        for(int x = 0; x < 4; x++)
        {
            randomItem = (int)((Math.random() *  range) + LL);
            shopArray[x] = itemList.get((int)((Math.random() *  range) + LL));
            //System.out.println("\nUL: " + UL + " LL: " + LL + "\nRange: " + range + "\nRandom Item: " + randomItem + "\nItem: " + itemList.get(randomItem).getName());
        }

        //container for the shop
        shopHolder = new JLabel();
        shopHolder.setBounds(100, 65, 400, 300);
        shopHolder.setBorder(BorderFactory.createLineBorder(Color.black));
        c.add(shopHolder);
        shopHolder.setVisible(false);

        goldSupply = new JLabel("Gold: " + temp.getGold());
        goldSupply.setBounds(10, 10, 65, 25);
        goldSupply.setBorder(BorderFactory.createLineBorder(Color.yellow));
        goldSupply.setForeground(Color.yellow);
        shopHolder.add(goldSupply);

        buttonHolder = new JLabel();
        buttonHolder.setBounds(10, 100, 230, 130);
        buttonHolder.setForeground(Color.green);
        shopHolder.add(buttonHolder);
        
        shopItemOne = new JButton(shopArray[0].getName());
        shopItemOne.setBounds(10, 10, 100, 50);
        shopItemOne.addActionListener(this);
        buttonHolder.add(shopItemOne);
        
        shopItemTwo = new JButton(shopArray[1].getName());
        shopItemTwo.setBounds(120, 10, 100, 50);
        shopItemTwo.addActionListener(this);
        buttonHolder.add(shopItemTwo);
        
        shopItemThree = new JButton(shopArray[2].getName());
        shopItemThree.setBounds(10, 70, 100, 50);
        shopItemThree.addActionListener(this);
        buttonHolder.add(shopItemThree);
        
        shopItemFour = new JButton(shopArray[3].getName());
        shopItemFour.setBounds(120, 70, 100, 50);
        shopItemFour.addActionListener(this);
        buttonHolder.add(shopItemFour);
        
        exit = new JButton(new ImageIcon(getClass().getResource("/core/exit.png")));
        exit.setBounds(375, 0, 25, 25);
        exit.addActionListener(this);
        shopHolder.add(exit);
        
        background = new JLabel(new ImageIcon(getClass().getResource("/core/shop.png")));
        background.setBounds(0, 0, 400, 300);
        shopHolder.add(background);
        
        
    }
    
    /**
     * method that makes the shop visible
     */
    public void openShop()
    {
        shopHolder.setVisible(true);
    }
    
    /**
     * method that makes the shop not visible
     */
    public void closeShop()
    {
        shopHolder.setVisible(false);
    }
    
    public void updateGold()
    {
        goldSupply.setText("Gold: " + temp.getGold());
    }
    
    /**
     * method that does all purchasing logic, like deduct money from player and adds it to players(c) inv
     * @param i
     * @param c
     */
    public void purchase(int i, character c)
    {
        int realPlace = i - 1;
        
        if(!"empty".equals(shopArray[realPlace].getName()))
        {
            if (shopArray[realPlace].getGoldValue() <= c.getGold())
            {                
                temp.setGold(c.getGold() - shopArray[realPlace].getGoldValue());
                temp.addToInv(shopArray[realPlace]);
                temp.displayInv();
            }
        }
    }
    
    /**
     * action listener for shop functions
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        JButton clickedButton = (JButton) e.getSource();
        if (e.getSource() instanceof JButton)
        {
            if (clickedButton == exit)
            {
                closeShop();
            }
            else if (clickedButton == shopItemOne)
            {
                purchase(1, temp);
                updateGold();
                
            }
            else if (clickedButton == shopItemTwo)
            {
                purchase(2, temp);
                updateGold();
            }
            else if (clickedButton == shopItemThree)
            {
                purchase(3, temp);
                updateGold();
            }
            else if (clickedButton == shopItemFour)
            {
                purchase(4, temp);
                updateGold();
            }
        }
    }
}