/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author HoangTheSon_Computer
 */
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import parser.UMLAssociation;
import parser.UMLAttribute;
import parser.UMLClass;
import parser.UMLMethod;
import utils.Configs;

public class ClassLayout {
	private Rectangle2D rect;
	private String className;
	private List<String> attributeNames;
	private List<String> methodNames;
	private List<Connector> connectors;
	private int classNameBlockHeight;
	private int attributesBlockHeight;
	private int methodsBlockHeight;
	Graphics2D g2d;
	UMLClass umlClass;
	private boolean chose;
	
	public ClassLayout(UMLClass umlClass) {
		this.umlClass = umlClass;
		this.className = umlClass.getName();
		this.attributeNames = new ArrayList<String>();
		this.methodNames = new ArrayList<String>();
		for(UMLAttribute a:umlClass.getAttributes())
			this.attributeNames.add(a.toString());
		for(UMLMethod m:umlClass.getMethods())
			this.methodNames.add(m.toString());
		this.connectors = new ArrayList<Connector>();
	}
	
	public void setG2D(Graphics2D g2d) {
		this.g2d = g2d;
		this.g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, Configs.FONT_SIZE));
		int textWidth = 0;
		if(this.g2d.getFontMetrics().stringWidth(umlClass.getName())>textWidth)
			textWidth = this.g2d.getFontMetrics().stringWidth(umlClass.getName());
		for(String str:this.attributeNames)
		{
			if(this.g2d.getFontMetrics().stringWidth(str)>textWidth)
				textWidth = this.g2d.getFontMetrics().stringWidth(str);
		}
		for(String str:this.methodNames)
		{
			if(this.g2d.getFontMetrics().stringWidth(str)>textWidth)
				textWidth = this.g2d.getFontMetrics().stringWidth(str);
		}
		
		int width = textWidth+2*Configs.MARGIN;
		this.classNameBlockHeight = Configs.MARGIN*2+Configs.FONT_SIZE;
		this.attributesBlockHeight = Configs.MARGIN*2+Configs.FONT_SIZE*this.attributeNames.size();
		this.methodsBlockHeight = Configs.MARGIN*2+Configs.FONT_SIZE*this.methodNames.size();

		int height = this.classNameBlockHeight+this.attributesBlockHeight+this.methodsBlockHeight;
		if(this.rect==null) {
//			Rectangle bound = this.g2d.getClipBounds();
//			int x = bound.width/2-width/2;
//			int y = bound.height/2-height/2;
			this.rect = new Rectangle2D.Double(width,height,width,height);
		}
	}
	
	public void draw() {
		this.drawName();
		this.drawAttributes(this.classNameBlockHeight);
		this.drawMethods(this.classNameBlockHeight+this.attributesBlockHeight);
		this.g2d.draw(this.rect);
//		this.g2d.draw(this.getCircleBoundOfRect());
		this.drawConnectors();
	}
	
	public void drawName() {
		this.g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Configs.FONT_SIZE));
		int x,y,nameWidth;
		nameWidth = this.g2d.getFontMetrics().stringWidth(className);
		x = (int) Math.round(this.rect.getX()+(this.rect.getWidth()-nameWidth)/2);
		y = (int) Math.round(this.rect.getY()+Configs.MARGIN+Configs.FONT_SIZE);
		this.g2d.drawString(this.className, x, y);
		int x1,x2,y1,y2;
		x1 = (int)Math.round(this.rect.getX());
		x2 = (int)Math.round(this.rect.getX()+this.rect.getWidth());
		y1 = (int)Math.round(this.rect.getY()+Configs.MARGIN*2+Configs.FONT_SIZE);
		y2 = (int)Math.round(this.rect.getY()+Configs.MARGIN*2+Configs.FONT_SIZE);
		this.g2d.drawLine(x1,y1,x2,y2);
	}

	public void drawAttributes(int preHeight) {
		this.g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, Configs.FONT_SIZE));
		int x,y;
		x = (int) Math.round(this.rect.getX()+Configs.MARGIN);
		y = (int) this.rect.getY()+preHeight+Configs.MARGIN+Configs.FONT_SIZE;
		for(String str:this.attributeNames) {
			this.g2d.drawString(str, x, y);
			y+=Configs.FONT_SIZE;
		}
		int x1,x2,y1,y2;
		x1 = (int)Math.round(this.rect.getX());
		x2 = (int)Math.round(this.rect.getX()+this.rect.getWidth());
		y1 = (int)Math.round(preHeight+this.rect.getY()+Configs.MARGIN*2+Configs.FONT_SIZE*this.attributeNames.size());
		y2 = (int)Math.round(preHeight+this.rect.getY()+Configs.MARGIN*2+Configs.FONT_SIZE*this.attributeNames.size());
		this.g2d.drawLine(x1,y1,x2,y2);
	}

	public void drawMethods(int preHeight) {
		this.g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, Configs.FONT_SIZE));
		int x,y;
		x = (int) Math.round(this.rect.getX()+Configs.MARGIN);
		y = (int) this.rect.getY()+preHeight+Configs.MARGIN+Configs.FONT_SIZE;
		for(String str:this.methodNames) {
			this.g2d.drawString(str, x, y);
			y+=Configs.FONT_SIZE;
		}
		int x1,x2,y1,y2;
		x1 = (int)Math.round(this.rect.getX());
		x2 = (int)Math.round(this.rect.getX()+this.rect.getWidth());
		y1 = (int)Math.round(preHeight+this.rect.getY()+Configs.MARGIN*2+Configs.FONT_SIZE*this.methodNames.size());
		y2 = (int)Math.round(preHeight+this.rect.getY()+Configs.MARGIN*2+Configs.FONT_SIZE*this.methodNames.size());
		this.g2d.drawLine(x1,y1,x2,y2);
	}

	
	public boolean isChose() {
		return chose;
	}

	
	public void setChose(boolean chose) {
		this.chose = chose;
	}

	
	public Point2D getCenter() {
		return new Point2D.Double(this.rect.getCenterX(), this.rect.getCenterY());
	}

	
	public Rectangle2D getRect() {
		return rect;
	}
	
	public Ellipse2D getCircleBoundOfRect() {
		if(this.rect!=null) {
			Point2D center = this.getCenter();
			double diameter = Math.sqrt(this.rect.getWidth()*this.rect.getWidth()+this.rect.getHeight()*this.rect.getHeight());
			return new Ellipse2D.Double(center.getX()-diameter/2, center.getY()-diameter/2, diameter, diameter);
		}
		return null;
	}

	public List<Connector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<UMLAssociation> associations, List<ClassLayout> classLayouts) {
		this.connectors = new ArrayList<Connector>();
		for(UMLAssociation a:associations) {
			for(ClassLayout classLayout:classLayouts) {
				if(classLayout.getClassName().equals(a.getWith())) {
					this.connectors.add(new Connector(a.getType(), this, classLayout));
					break;
				}
			}
		}
	}

	public String getClassName() {
		return className;
	}

	public void moveRect(double dx, double dy) {
		this.rect.setRect(this.rect.getX()+dx,this.rect.getY()+dy, this.rect.getWidth(), this.rect.getHeight());
	}
	
	public void setPosition(double x, double y) {
		this.rect.setRect(x, y, this.rect.getWidth(), this.rect.getHeight());
	}
	
	public List<Line2D> getRectLines() {
		List<Line2D> lines = new ArrayList<Line2D>();
		Point2D p1 = new Point2D.Double(this.rect.getX(), this.rect.getY());
		Point2D p2 = new Point2D.Double(this.rect.getX()+this.rect.getWidth(), this.rect.getY());
		Point2D p3 = new Point2D.Double(this.rect.getX(), this.rect.getY()+this.rect.getHeight());
		Point2D p4 = new Point2D.Double(this.rect.getX()+this.rect.getWidth(), this.rect.getY()+this.rect.getHeight());
		lines.add(new Line2D.Double(p1,p2));//top
		lines.add(new Line2D.Double(p1,p3));//left
		lines.add(new Line2D.Double(p2,p4));//right
		lines.add(new Line2D.Double(p3,p4));//bot
		return lines;
	}

	public void drawConnectors() {
		for(Connector c : this.connectors) {
			c.draw(this.g2d);
		}
	}
}