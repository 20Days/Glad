package core;

import items.item;
import enemys.enemy;
import items.empty;
import items.healthPotion;
import weapon.fists;
import weapon.weapon;

/**
 *
 * @author 20 Days
 */
@SuppressWarnings("unused")
public class character 
{
    private final String name;
    protected final item[] inv;
    final weapon[] weapons;
    private int armor;
    private int hp;
    private int gold;
    final int ul = 10;
    final int ll = 1;
    final int range = ul - ll + 1;
    
    /**
     * character constructor
     * @param n
     */
    public character(String n)
    {
        name = n;
        inv = new item[5];
        weapons = new weapon[1];
        hp = 100;
        armor = 0;
        gold = 100;
        
        inv[0] = new empty();
        inv[1] = new healthPotion();
        inv[2] = new empty();
        inv[3] = new empty();
        inv[4] = new empty();
        
        weapons[0] = new fists();
    }
    
    public void replaceWeapon(weapon w) {weapons[0] = w;}
    
    public void setGold(int g){gold = g;}
    
    public int getGold()  {return gold;}
    
    public int getArmor() {return armor;}
    
    public void setArmor(int a) {armor = a;}
 
    /**
     * returns name of character
     * @return 
     */
    public String getName() {return name;}

    /**
     * returns HP value of character
     * @return 
     */
    public int getHP()  {return hp;}

    /**
     * sets HP value to specified amount 
     * @param c
     */
    public void setHP(int c) {hp = c;}
    
    /**
     * use method for character, uses item specified using 'pos' on character e. possible to add friendlies to heal later
     * @param e
     * @param pos
     */
    public void use(character e, int pos)
    {
        if(pos < inv.length) //makes sure given spot is not out of bounds
            if (inv[pos] != null)
                this.getItem(pos).use(e, pos);
            else
                System.out.println("spot is emtpy");
        else
            System.out.println("spot does not exist!");
    }
    
    /**
     * characters method to attack specified enemy
     * @param e
     * @return 
     */
    public String attack(enemy e)
    {
        int crit = (int)(Math.random() * range) + ll;
 
        if(e.getArmor() <= 0)
        {
            if(e.getHP() > 0)
            {
                if (crit <= 9)
                {
                    e.setHP((e.getHP() - weapons[0].getDamage()));
                    return "You did " + weapons[0].getDamage() + " damage to the " + e.getName();
                }
                else
                {
                    e.setHP((e.getHP() - (weapons[0].getDamage() + 2)));
                    return "You did " + (weapons[0].getDamage() + 1) + " damage to the " + e.getName();
                }
            }
            else
                return "You Have killed the " + e.getName() + "!";
        }
        else
        {
            if (crit <= 9)
            {
                e.setArmor((e.getArmor() - weapons[0].getDamage()));
                if(e.getArmor() < 0)
                    e.setArmor(0);
                return "You did " + weapons[0].getDamage() + " damage to the " + e.getName() + "'s armor!";
            }
            else
            {
                e.setArmor((e.getArmor() - (weapons[0].getDamage() + 2)));
                if(e.getArmor() < 0)
                    e.setArmor(0);
                return "You did " + (weapons[0].getDamage() + 1) + " damage to the " + e.getName() + "'s armor!";
            }
        }
    }  
    
    /**
     * returns item in specified spot
     * @param pos
     * @return 
     */
    public item getItem(int pos)
    {
        if(pos < inv.length)
            return inv[pos];
        else 
            return null;
    }
    
    /**
     * for loop to return everything in inv
     */
    public void displayInv()
    {
        for (int x = 0; x < inv.length; x++)
            if(getItem(x) != null)
                System.out.println(x + ": " + getItem(x));
            else
                System.out.println(x + ": !Empty!");
    }
    
    /**
     * returns toString for item in specified spot(s)
     * @param s
     * @return 
     */
    public String getInvString(int s)
    {
        return this.inv[s].toString();
    }

    /**
     * adds specified item(e) to a specific spot(pos)
     * @param e
     * @param pos
     */
    public void addToInv(item e, int pos)
    {
        if(pos < inv.length)
            if(getItem(pos) == null || getItem(pos).getName().equals("empty"))
                inv[pos] = e;
            else
                System.out.println("spot is taken!");
        else
            System.out.println("spot does not exist!");
    }
    
    /**
     * adds specified item(e) to next available spot
     * @param e
     * @return 
     */
    public boolean addToInv(item e)
    {
        for(int i = 0; i < inv.length; i++)
        {            
            if(getItem(i) == null || getItem(i).getName().equals("empty"))
            {
                inv[i] = e;
                System.out.println("adding " + e + " to " + i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * removes item in specified location
     * @param pos
     */
    public void removeItem(int pos)
    {
        if(pos < inv.length)
            if (inv[pos] != null)
                inv[pos] = null;
            else
                System.out.println("Spot is already empty!");
        else
            System.out.println("Spot does not exist!");
    }
    
    /**
     * toString for character, returns name and then HP value
     * @return
     */
    @Override
    public String toString()
    {
        return "[" + getName() + ", " + getHP() + "]";
    }
}