

import java.awt.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;




public abstract class Entite {


    protected int x, y, width, height; // position et taille
    protected Image image; // L’image de l’entité
    protected boolean detruire = false; // Ne pas détruire si false


    /**
     * Constructeur d'Entite avec image sur le disque
     *
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param width largeur de l'image 13
     * @param height hauteur de l'image
     * @param imagepath chemin d'accès de l'image sur le disque
     */
    public Entite(int x, int y, int width, int height, String imagepath) {
        this.x = x;
        this.y = y;
   
        try {
            image = new Image(imagepath);
        } catch (SlickException e) {
            System.out.println("Image non trouvée pour " + getClass());
        }
        // modification apporté par thomas 23nov
        this.width = width;
        this.height = height;
    }


    /**
     * Constructeur d'Entite #2 - Avec SpriteSheet
     *
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param spriteSheet SpriteSheet qui contient l'image
     * @param ligne la ligne dans le SpriteSheet de l'image
     * @param colonne la colonne dans le SpriteSheet de l'image
     */
    public Entite(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne) {
        this.x = x;
        this.y = y;
        this.image = spriteSheet.getSubImage(ligne, colonne);
        this.width = image.getWidth();
        this.height = image.getHeight();
      
      
    }


    public int getX() { // Position en X du coin supérieur gauche de l’entite
        return x;
    }


    public int getY() { // Position en Y du coin supérieur gauche de l’entite
        return y;
    }


     void  setX(int x) { // Position en X du coin supérieur gauche de l’entite
        this.x = x;
    }


    void  setY(int y) { // Position en Y du coin supérieur gauche de l’entite
         this.y=y;
    }
    public float getWidth() { // Largeur de l’entite
        return width;
    }


    public float getHeight() { // Hauteur de l’entite
        return height;
    }


    public Image getImage() { // Retourne l’image de l’entité
        return image.getScaledCopy(width, height);
    }


    public Rectangle getRectangle() { // Retourne le rectangle qui englobe l’entité
        return new Rectangle(x, y, width, height);
    }


    public void setLocation(int x, int y) { // Pour déplacer l’élément depuis le Jeu
        this.x = x;
        this.y = y;
    }


    public boolean getDetruire() { // Si l’objet doit être détruit --> true, false sinon
        return detruire;
    }
    




}