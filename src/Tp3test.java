/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author kevin
 */
public class Tp3test {
public static final int Hauteur=722, Largeur=722;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Jeu("Tp3"));
            app.setDisplayMode(Hauteur, Largeur, false);
            app.setVSync(true);
            app.start();
        } catch (SlickException ex) {
            Logger.getLogger(Tp3test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
