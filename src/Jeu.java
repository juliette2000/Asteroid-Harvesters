/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

//import res.Entite;
public class Jeu extends BasicGame {

    boolean bouge = false;
    private SpriteSheet spriteSheet;
    private ArrayList<Entite> listeEntite = new ArrayList<>();
    private GameContainer container;
    public Image plane;
    public Image asteroid;
    public Image asteroid256;

    public Image asteroid128;
    public Image bullet;
    public int x = 310;
    public int y = 580;
    public int dy = 0; // shift du background
    public boolean moving = false;
//    public Image image2;
    public int direction = 2;
    int directionOrigineX = 1;
    int directionOrigineY = 3;
    public double x1 = x;
    public double y1 = y;
//    boolean collision = false;
    public int Hauteur = Tp3test.Hauteur;
    public int Largeur = Tp3test.Largeur;
    public Timer timer;

    public List<Asteroide> listAsteroide = new ArrayList<Asteroide>();
    public List<Bullet> listBullets = new ArrayList<Bullet>();
    public int vie = 3;
    public double nbrcollect = 0;
    private ArrayList<Bougeable> listeBougeable = new ArrayList<>();
    public Avion avion;
    public int nbrenvoie = 0;
    public boolean dead = false;
    
    public Jeu(String title) {
        super(title);
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        drawBackground();
        this.avion = new Avion(x, y, "plane.png");
        this.asteroid = new Image("asteroid.png");
        this.bullet = new Image("bullet.png");

//        listAsteroide.add(new Asteroide(0, 0, 128, 128, "ast.png"));
//        for (int i = 0; i < listAsteroide.size(); i++) {
//            listeBougeable.add(listAsteroide.get(i));
//        }
        listeBougeable.add(avion);

        timer = new Timer(100, new GameCycle());
        timer.start();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < listeBougeable.size(); i++) {
                if (listeBougeable.get(i) instanceof Asteroide) {
                    ((Asteroide) listeBougeable.get(i)).updatePosition(10);
                } else if (listeBougeable.get(i) instanceof Bullet) {
                    ((Bullet) listeBougeable.get(i)).updatePosition(10);
                }

            }

            dy += 10;
            dy %= 722;
        }
    }

    public void drawBackground() throws SlickException {
        listeEntite.clear();
        spriteSheet = new SpriteSheet("bg.JPG", 722, 722);
        Floor bg = new Floor(0, dy, spriteSheet);
        Floor bg2 = new Floor(0, dy - 722, spriteSheet);
        listeEntite.add(bg);
        listeEntite.add(bg2);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (this.moving) {
            switch (this.direction) {
                case 0: // up
                    if (y <= 0) {
                        y = 0;
                    } else {
                        this.y -= 0.4 * delta;
                    }
                    break;
                case 1: // left
                    if (x <= 0) {
                        x = 0;
                    } else {
                        this.x -= 0.4 * delta;
                    }
                    break;
                case 2: // down
                    if (y >= Hauteur - 128) {
                        y = Hauteur - 128;
                    } else {
                        this.y += 0.4 * delta;
                    }
                    break;
                case 3: //right
                    if (x > Largeur - 128) {
                        x = Largeur - 128;
                    } else {
                        this.x += 0.4 * delta;
                    }
                    break;
                case 4:
                    if (y1 < Tp3test.Hauteur) {
                        directionOrigineY = 2;
                    }
                    if (y1 < 0) {
                        directionOrigineY = 1;
                    }
                    if (directionOrigineY == 2) {
                        this.y1 -= 0.2 * delta;
                    } else if (directionOrigineY == 1) {
                        this.y1 += 0.2 * delta;
                    }

                    break;

            }
        }

        avion.setLocation(x, y);

//        bullet.setLocation((int) x1 + 56, (int) y1 + 8);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        drawBackground(); // background
        for (Entite entite : listeEntite) {
            g.drawImage(entite.getImage(), entite.getX(), entite.getY());
        }

        if(dead)
        {
             g.drawString("Gameover " , 722/2-50, 722/2);
             g.drawString("Press enter to restart " , 722/2-50, 722/2 + 15);
        }
        else{
        // asteroide
        // est-ce que'on generere un nouveau asteroide?
        if (listAsteroide.size() < 10) {
            Random r = new Random();
            if (r.nextInt(250) == 66) {
                int grandeur = 256 / (int) ((Math.pow(2, r.nextInt(3)))); // 64, 128, 256
                //grandeur = 64;
                int direction = r.nextInt(3);
                int xtemp, ytemp;
                switch (direction) {
                    // vertical
                    case 0:
                        ytemp = 0 - grandeur;
                        xtemp = r.nextInt(722);
                        break;
                    // gauche a droite
                    case 1:
                        ytemp = r.nextInt(300);
                        xtemp = 0 - grandeur;
                        break;
                    default: // droite a gauche
                        ytemp = r.nextInt(300);
                        xtemp = 722;
                        break;
                }
                Asteroide ast_temp = new Asteroide(xtemp, ytemp, grandeur, grandeur, "asteroid.png");
                ast_temp.setDirection(direction);
                ast_temp.setGrandeur(grandeur);
                listAsteroide.add(ast_temp);
                listeBougeable.add(ast_temp);
            }
        }

        gererCollisions();
        gererAsteroides();
        gererBullets();
        gererSplit();
        verifieVie(vie);
        }
        
    
        for (Bougeable b : listeBougeable) {
            g.drawImage(((Entite) b).getImage(), ((Entite) b).getX(), ((Entite) b).getY());
        }

        g.drawString("vie: " + (vie) + "/3", 20, 25);
        g.drawString("collect: " + (nbrcollect) + "/100%", 20, 40);
        g.drawString("Envoie: " + (nbrenvoie), 20, 55);

    }

    public void collect(Asteroide ast) {

        if (ast.getGrandeur() == 32) {
            nbrcollect += 6.25;
        } else if (ast.getGrandeur() == 64) {
            nbrcollect += 25;

        }
        verifieCollect();
    }

    public void verifieCollect() {

        if (nbrcollect > 101) {
            vie -= 1;
            nbrcollect = 0;
        }
    }

    public void verifieVie(int vie) {
        if (vie <= 0) {
            dead = true;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        this.moving = false;
        if (Input.KEY_ESCAPE == key) {
            this.container.exit();
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        this.moving = false;
        if(dead)
        {
            switch (key) {
            case Input.KEY_ENTER:
                dead = false;
                vie = 3;
                nbrenvoie=0;
                nbrcollect=0;
                
                break;
        }
        }
        else
        {
        switch (key) {
            case Input.KEY_W:
                this.direction = 0;
                this.moving = true;
                break;
            case Input.KEY_A:
                this.direction = 1;
                this.moving = true;
                break;
            case Input.KEY_S:
                this.direction = 2;
                this.moving = true;
                break;
            case Input.KEY_D:
                this.direction = 3;
                this.moving = true;
                break;
            case Input.KEY_SPACE:
                Bullet temp = new Bullet(x + 58, y, 13, 12);
                listeBougeable.add(temp);
                listBullets.add(temp);
                break;
            case Input.KEY_E:
                if (nbrcollect >= 100) {
                    nbrcollect = 0;
                    nbrenvoie += 1;
                }

                break;
        }
        }
    }

    private void gererCollisions() {
//        System.out.println(listeBougeable.size());
        ArrayList<Entite> listeTemp = new ArrayList<>();
        Bougeable b1, b2;
        for (int i = 0; i < listeBougeable.size(); i++) {
            for (int j = i + 1; j < listeBougeable.size(); j++) {
                b1 = listeBougeable.get(i);
                b2 = listeBougeable.get(j);
                if (b1 != b2) { // 2 entités différentes
                    if (((Entite) b1).getRectangle().intersects(((Entite) b2).getRectangle())) {
                        if (b1 instanceof Bullet) {
                        } else if (b2 instanceof Bullet) {
                        } else {
                            if ((b1 instanceof Destructible) && (b2 instanceof Destructible)) // deux asteroides et on fait rien
                            {

                            } else { // le cas entre avion et asteroide
                                if (b1 instanceof Destructible) { // b1 est asteroide
                                    listeTemp.add((Entite) b1);
//                           verifier si on l'avale ou perd une vie
                                    System.out.println("b1 has size" + ((Asteroide) b1).getGrandeur());
                                    if (((Asteroide) b1).getGrandeur() >= 128) {
                                        vie -= 1;
                                        nbrcollect = 0;
                                    } else {
                                        collect((Asteroide) b1);
                                    }
                                } else {
                                    listeTemp.add((Entite) b2); // C’est aussi une Entite  
                                    if (((Asteroide) b2).getGrandeur() >= 128) {
                                        vie -= 1;
                                        nbrcollect = 0;
                                    } else {
                                        collect((Asteroide) b2);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
        listAsteroide.removeAll(listeTemp);
        listeTemp.clear(); //Supprimer la liste temporaire
    }

    private void gererSplit() {
        ArrayList<Entite> listeTemp = new ArrayList<>();

        Bullet b;
        Asteroide a;
        
        for (int i = 0; i < listBullets.size(); i++) {
            for (int j = 0; j < listAsteroide.size(); j++) {
                b = listBullets.get(i);
                a = listAsteroide.get(j);
                if (((Entite) a).getRectangle().intersects(((Entite) b).getRectangle())) {
                    listeTemp.add(b);
                    listeTemp.add(a);

                    if (a.getGrandeur() > 32) {
                        Asteroide ast_temp = new Asteroide(a.getX() - a.getGrandeur()/2, a.getY(),  a.getGrandeur()/2,  a.getGrandeur()/2, "asteroid.png");
                        ast_temp.setDirection(a.getDirection());
                        ast_temp.setGrandeur(a.getGrandeur()/2);
                        listAsteroide.add(ast_temp);
                        listeBougeable.add(ast_temp);
                        
                        ast_temp = new Asteroide(a.getX() + a.getGrandeur()/2, a.getY(),  a.getGrandeur()/2,  a.getGrandeur()/2, "asteroid.png");
                        ast_temp.setDirection(a.getDirection());
                        ast_temp.setGrandeur(a.getGrandeur()/2);
                        listAsteroide.add(ast_temp);
                        listeBougeable.add(ast_temp);
                    }
                }
            }
        }
        listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
        listAsteroide.removeAll(listeTemp);
        listBullets.removeAll(listeTemp);
        listeTemp.clear(); //Supprimer la liste temporaire
    }

    private void gererAsteroides() {
        ArrayList<Entite> listeTemp = new ArrayList<>();
        for (Asteroide a : listAsteroide) {
            if (a.getY() > 722 || a.getX() > 722 || a.getX() < -a.getGrandeur()) {
                listeTemp.add((Entite) a);
            }
        }

        listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
        listAsteroide.removeAll(listeTemp);
        listeTemp.clear(); //Supprimer la liste temporaire
    }

    private void gererBullets() {
        ArrayList<Entite> listeTemp = new ArrayList<>();
        for (Bullet a : listBullets) {
            if (a.getY() < 0) {
                listeTemp.add((Entite) a);
            }
        }

        listeBougeable.removeAll(listeTemp); //Supprimer les éléments marqués
        listBullets.removeAll(listeTemp);
        listeTemp.clear(); //Supprimer la liste temporaire
    }
}
