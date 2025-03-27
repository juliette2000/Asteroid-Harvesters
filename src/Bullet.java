
import java.awt.Rectangle;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chenj
 */
public class Bullet extends Entite implements Bougeable,Destructible{
    private int v = 10;
protected boolean detruire = false; // Ne pas détruire si false
   public Bullet(int x, int y, int width, int height) {
        super(x,y,width,height,"bullet.png");
    }
    
    

   
    
    public void updatePosition(int backgroundspeed){

                y += backgroundspeed - v*3;

        }
    
    public boolean getDetruire() { // Si l’objet doit être détruit --> true, false sinon
        return detruire;
    }
}

