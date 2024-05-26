package tart.app.components;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class LoadedImage extends Canvas {
    Image img;
    
    public LoadedImage(){
        this(null);
    }
    
    public LoadedImage(Image img){
        this.img = img;
    }
    
    public void set(Image img){
        this.img = img;
        repaint();
    }
    
    @Override
    public void paint(Graphics g){
        if(img == null){
            g.drawString("Select directory with images!", 10, 30);
        } else {
            // TODO refactor magic number 4
            g.drawImage(img, 4, 0, this);
        }
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(img.getWidth(this), img.getHeight(this));
    }
    
    @Override
    public Dimension getMinimumSize(){
        return getPreferredSize();
    } 
}
