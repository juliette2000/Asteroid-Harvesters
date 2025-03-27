
import java.util.Random;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chenj
 */
public class Asteroide extends Entite implements Bougeable,Destructible {
    private int grandeur;
    private int v = 5;
    private int direction; // 0 = vertical, 1 = left to right, 2 = right to left
   // private int x;
    //private int y;
//     public Asteroid(int x, int y, SpriteSheet spriteSheet) {
//        super(x, y, spriteSheet, 1, 0);
//    }
    protected boolean detruire = false; // Ne pas détruire si false
    public Asteroide(int x, int y, int width, int height, String imagepath) {
        super(x,y,width,height,"asteroid.png");
    }

    public void setGrandeur(int grandeur) {
        this.grandeur = grandeur;
    }

    public int getGrandeur() {
        return grandeur;
    }
    
    public void reduce() {
        grandeur /= 2;    
    }
    
    public void setDirection(int d)
    {
        direction = d;
    }
    
    public void updatePosition(int backgroundspeed){
        switch (direction) {
        // vertical
            case 0:
                y += backgroundspeed + v;
                break;
        // gauche a droite
            case 1:
                x += v;
                y += backgroundspeed;
                break;
            default:
                x -= v;
                y += backgroundspeed;
                break;
        }
    }

    public int getDirection() {
        return direction;
    }
    
    public boolean getDetruire() { // Si l’objet doit être détruit --> true, false sinon
        return detruire;
    }
}


// implementer le split de l'asteroide
    // d'abord verifier qu'on touche une asteroide
    // si on touche, et que l'asteroide n'est pas trop petit (64), l'asteroide fait juste disparaitre
    // enlever l'asteroide touche, et creer deux nouveaux avec des nouvelles positions ainsi que des nouvelles vitesses

