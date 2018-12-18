/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import gui.ClassLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author HoangTheSon_Computer
 */
public class GUIContent extends JPanel{
    private static final long serialVersionUID = 1L;
    private List<ClassLayout> classLayouts;
    private boolean reArranged = false;
    MovingAdapter ma = new MovingAdapter();
    public GUIContent(String paths) throws IOException {
        ClassParser h = new ClassParser(paths);
        List<UMLClass> umlClasses = h.getClasses();
        classLayouts = new ArrayList<>();
        for(UMLClass c:umlClasses) {
            classLayouts.add(new ClassLayout(c));
        }
        for(int i=0;i<umlClasses.size();i++) {
            classLayouts.get(i).setConnectors(umlClasses.get(i).getAssociations(), classLayouts);
        }
        addMouseMotionListener(ma);
        addMouseListener(ma);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(new Color(0,0,0));
        for(ClassLayout c:this.classLayouts) {
                c.setG2D(g2d);
                c.draw();
        }
        if(this.reArranged==false)
                this.reArrange(g2d);
    }
    public void reArrange(Graphics2D g2d) {
        double maxWidth = 0, maxHeight = 0;
        for(ClassLayout c:this.classLayouts) {
            if(c.getCircleBoundOfRect().getWidth()>maxWidth)
                    maxWidth = c.getCircleBoundOfRect().getWidth();
            if(c.getCircleBoundOfRect().getHeight()>maxHeight)
                    maxHeight = c.getCircleBoundOfRect().getHeight();
        }
        int maxClassesInARow = (int) Math.floor(Math.sqrt(this.classLayouts.size()));
        int maxClassesInACol = Math.round(this.classLayouts.size()/maxClassesInARow);
        int row = 0;
        double firstX = g2d.getClipBounds().width/2-(double)(maxWidth*maxClassesInARow/2);
        double firstY = g2d.getClipBounds().height/2-(double)(maxHeight*maxClassesInACol/2);

        double x = firstX;
        double y = firstY;

        for(ClassLayout c:this.classLayouts) {
            c.setPosition(x, y);
            x+=maxWidth;
            row++;
            if(row==maxClassesInARow) {
                    row=0;
                    x = firstX;
                    y+=maxHeight;
            }
            repaint();
        }
        System.out.println("maxWidth " + maxWidth + " maxHeight " + maxHeight);
        this.reArranged=true;
        System.out.println("g2d.getClipBounds().width " + g2d.getClipBounds().width + " g2d.getClipBounds().height " + g2d.getClipBounds().height);
        System.out.println("num of class " + this.classLayouts.size());
        System.out.println("num of row " + maxClassesInARow);
    }
    class MovingAdapter extends MouseAdapter {
        private int x;
        private int y;
        public void mousePressed(MouseEvent e) {
            x=e.getX();
            y=e.getY();
            for(ClassLayout c:classLayouts) {
                    if(c.getRect().contains(x, y)) {
                            c.setChose(true);
                    }
            }
        }

        public void mouseDragged(MouseEvent e) {
            int dx = e.getX() - x;
            int dy = e.getY() - y;
            for(ClassLayout c:classLayouts) {
                if(c.isChose())
                {
                    c.moveRect(dx, dy);
                    repaint();
                    break;
                }
            }
            x+=dx;
            y+=dy;
        }

        public void mouseReleased(MouseEvent e) {
            for(ClassLayout c:classLayouts) {
                    c.setChose(false);
            }
        }
    }
}
