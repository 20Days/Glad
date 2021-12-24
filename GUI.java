/*
 * to add:
 * drop system for enemies
 * armor system
 * add more enemies
 * image icons
 * classes
 *
 */
package core;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JFrame;
import enemys.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.Timer;
import turnbase.*;

public class GUI extends JFrame implements ActionListener
{
    private JLabel output;
    private JLabel HP;  
    private JLabel enemyCount;
    private JLabel armor;
    private JLabel enemyHP;
    private JLabel enemyArmor;
    private JLabel weaponDisplay;
    private JButton blockB;
    private JButton attack;
    private JButton use;
    private JList inventoryDisplay;
    private JLabel ply1;
    private JLabel pleeb;
    private JLabel knight;
    private JLabel background;
    ArrayList<enemy> allEnemies = new ArrayList<>();
    private int UL;
    private int LL;
    private int range;
    private int randomEnemy;
    
    int enemiesKilled = 0;

    private boolean dead = false;
    private boolean block = false;
    
    Container contentPane = getContentPane();
    
    //people
    character me = new character("Dr. Free G. Man");
    enemy knightC;
    enemy plebC;
    
    //shop
    Shop shop = new Shop(contentPane, me);

    //funtions
    Queue<enemy> enemyList;
    turns turn = new turns();
    ArrayList<JLabel> labelList = new ArrayList<>();

    /**
     * GUI constructor, makes all the buttons labels and gui stuff
     */
    public GUI()
    {
        UL = allEnemies.size() - 1;
        LL = 0;
        range = UL - LL + 1;
        
        enemyList = new LinkedList<>();

        //all enemies in the game that can be spawned
        allEnemies.add(plebC = new pleb());
        allEnemies.add(knightC = new knight());
        
        //start of the enemies
        enemyList.add(allEnemies.get(0));
        enemyList.add(allEnemies.get(1));

        //GUI STUFF
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        setTitle("Glad");
        setSize(600, 500);
        setLocation(150, 150);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /*
         * BUTTONS are use, attack, and blockB
         */
        use = new JButton("Use");
        use.setBounds(230, 400, 100, 50);
        use.addActionListener(this);
        contentPane.add(use);

        attack = new JButton("Attack");
        attack.setBounds(120, 400, 100, 50);
        attack.addActionListener(this);
        contentPane.add(attack);

        blockB = new JButton("Shop");
        blockB.setBounds(10, 400, 100, 50);
        blockB.addActionListener(this);
        contentPane.add(blockB);
        
        weaponDisplay = new JLabel(new ImageIcon(getClass().getResource("/core/weapon_fist.png")));
        weaponDisplay.setBounds(355, 400, 50, 50);
        weaponDisplay.setBorder(BorderFactory.createLineBorder(Color.red));
        contentPane.add(weaponDisplay);

        /*
         * LABELS are HP, slotLabel, output, enemyISDead
         */
        HP = new JLabel(String.valueOf("HP: " + me.getHP()));
        HP.setBounds(540, 10, 50, 25);
        HP.setBorder(BorderFactory.createLineBorder(Color.red));
        HP.setForeground(Color.RED);
        contentPane.add(HP);
        
        enemyCount = new JLabel(String.valueOf("Enemies killed: " + enemiesKilled));
        enemyCount.setBounds(250, 10, 110, 25);
        enemyCount.setBorder(BorderFactory.createLineBorder(Color.red));
        enemyCount.setForeground(Color.red);
        contentPane.add(enemyCount);

        armor = new JLabel(String.valueOf("Armor: " + me.getArmor()));
        armor.setBounds(530, 40, 60, 25);
        armor.setBorder(BorderFactory.createLineBorder(new Color(21, 118, 187)));
        armor.setForeground(new Color(21, 118, 187));
        contentPane.add(armor);

        enemyHP = new JLabel(String.valueOf("Enemy's HP: " + enemyList.peek().getHP()));
        enemyHP.setBounds(10, 10, 100, 25);
        enemyHP.setBorder(BorderFactory.createLineBorder(Color.red));
        enemyHP.setForeground(Color.RED);
        contentPane.add(enemyHP);

        enemyArmor = new JLabel(String.valueOf("Enemy's Armor: " + enemyList.peek().getArmor()));
        enemyArmor.setBounds(10, 40, 110, 25);
        enemyArmor.setBorder(BorderFactory.createLineBorder(new Color(21, 118,187)));
        enemyArmor.setForeground(new Color(21, 118, 187));
        contentPane.add(enemyArmor);

        output = new JLabel(String.valueOf(""));
        output.setBounds(30, 350, 400, 40);
        output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPane.add(output);

        /*
         * LISTS are inventoryDisplay
         */
        inventoryDisplay = new JList(me.inv);
        inventoryDisplay.setBounds(450, 355, 120, 100);
        inventoryDisplay.setBorder(BorderFactory.createLineBorder(Color.blue));
        inventoryDisplay.setSelectionMode(SINGLE_SELECTION);
        contentPane.add(inventoryDisplay);

        /*
         * IMAGES are ply1, pleeb, knight
         */
        ply1 = new JLabel(new ImageIcon(getClass().getResource("/core/ply1.png")));
        ply1.setBounds(275, 75, 200, 200);
        contentPane.add(ply1);

        pleeb = new JLabel(new ImageIcon(getClass().getResource("/core/pleeb.png")));
        pleeb.setBounds(100, 75, 200, 200);
        contentPane.add(pleeb);
        pleeb.setVisible(false);
        labelList.add(pleeb);

        knight = new JLabel(new ImageIcon(getClass().getResource("/core/knight.png")));
        knight.setBounds(100, 75, 200, 200);
        contentPane.add(knight);
        knight.setVisible(false);
        labelList.add(knight);

        //background image
        background = new JLabel(new ImageIcon(getClass().getResource("/core/background.png")));
        background.setBounds(0, 0, 600, 500);
        contentPane.add(background);

        checkEnemy();
    }
    
    /**
     * pops current enemy from stack
     */
    public void nextEnemy()
    {
        if (!enemyList.isEmpty())
        {
            enemyList.remove();
            checkEnemy();
            turn.nextTurn();
        }
        else
            System.out.println("list is empty! you won!");
    }

    /**
     * checks to see if enemy has updated
     */
    private void checkEnemy()
    {
        switch (enemyList.peek().getName())
        {
            case "pleb":
                for (int i = 0; i < labelList.size(); i++)
                    labelList.get(i).setVisible(false);
                pleeb.setVisible(true);
                break;
            case "Knight":
                for (int k = 0; k < labelList.size(); k++)
                    labelList.get(k).setVisible(false);
                knight.setVisible(true);
                break;
            default:
                output.setText("!ERROR 202 TELL PROGRAMMER!");
                break;
        }
    }

    /**
     * method to display outputs when enemy is killed and cycles next 
     * enemy with outputs
     */
    public void killedEnemy()
    {
        randomEnemy = (int)((Math.random() *  range) + LL);
        output.setText(enemyList.peek().dead());
        me.setGold(me.getGold() + enemyList.peek().goldDrop());
        nextEnemy();
        delMsg("You are now fighting a " + enemyList.peek().getName() + "!", 1);
        enemiesKilled++;
        enemyList.add(allEnemies.get(randomEnemy));
        updateStatus();
    }

    /**
     * updates all display outputs to current numbers and inv display
     */
    public void updateStatus()
    {
        enemyHP.setText("Enemy's HP: " + enemyList.peek().getHP());
        HP.setText("HP: " + String.valueOf(me.getHP()));
        inventoryDisplay.setListData(me.inv); 
        enemyArmor.setText("Enemy's Armor: " + enemyList.peek().getArmor());
        armor.setText("Armor: " + me.getArmor());
        enemyCount.setText("Enemies killed: " + enemiesKilled);
    }

    /**
     * logic for finding what the enemy will do on their turn
     * @param e
     * @param c
     * @return 
     */
    public String enemysTurn(enemy e, character c)
    {
        int ul = 15;
        int ll = 1;
        int range = ul - ll + 1;

        int whatdo = (int) (Math.random() * range + ll);

        if (whatdo <= 13)
        {
            if (!block)
            {
                e.attack(c);
                updateStatus();
                turn.nextTurn();
                return "Enemy attacked, but was blocked!";
            }
            else
            {
                turn.nextTurn();
                block = false;
                return "You blocked the enemys attack!";
            }
        }
        else
        {
            e.heal();
            updateStatus();
            turn.nextTurn();
            block = false;
            return "Enemy healed for " + enemyList.peek().getHP() + " HP!";
        }
    }
    
    /**
     * uses timer to output a delayed message
     * @param s
     * @param i
     */
    public void delMsg(String s, int i)
    {
        Timer timer = new Timer(i * 1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                output.setText(s);
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    /**
     * uses timer to make an enemies attack delayed
     * @param m
     * @param i
     */
    public void delAtck(character m, int i)
    {
        Timer timer = new Timer(i * 1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                output.setText(enemysTurn(enemyList.peek(), m));
                if (m.getHP() <= 0)
                {
                    dead = true;
                    output.setText("You have died, please try again!");
                }
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    /**
     * actions for buttons
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {        
        try
        {            
            if (!dead && turn.whosTurn())
            {
                int invSlot = inventoryDisplay.getSelectedIndex();

                JButton clickedButton = (JButton) e.getSource();
                if (e.getSource() instanceof JButton)
                {
                    if (clickedButton == attack)
                    {
                        if (enemyList.peek().getHP() > 0)
                        {
                            turn.nextTurn();
                            output.setText(me.attack(enemyList.peek()));
                            updateStatus();
                            if (enemyList.peek().getHP() > 0)
                                delAtck(me, 1);
                            else
                                killedEnemy();
                        }
                        else
                        {
                            killedEnemy();
                            output.setText("you killed the " + enemyList.peek().getName());
                        }
                    }
                    else if (clickedButton == use)
                    {
                        turn.nextTurn();
                        me.use(me, invSlot);
                        updateStatus();
                        if (enemyList.peek().getHP() > 0)
                            delAtck(me, 1);
                        else
                            killedEnemy();
                    }
                    else if (clickedButton == blockB)
                    {
                        //turn.nextTurn();
                        shop.openShop();
                        //block = true;
                        //if (enemyList.peek().getHP() > 0)
                        //    delAtck(me, 1);
                        //else
                        //    killedEnemy();
                    }
                }
            }
        }
        catch (NumberFormatException a)
        {
            System.out.println(a.getMessage());
            output.setText("how did you mess up so badly!");
        }
    }
}