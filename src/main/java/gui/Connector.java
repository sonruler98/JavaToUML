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
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import utils.Configs;
import utils.ConnectorType;

public class Connector {
	private ConnectorType type;
	private ClassLayout source, dest;
	public Connector(ConnectorType type, ClassLayout source, ClassLayout dest) {
		super();
		this.type = type;
		this.source = source;
		this.dest = dest;
	}
	
	public ConnectorType getType() {
		return type;
	}
	public void setType(ConnectorType type) {
		this.type = type;
	}
	public void draw(Graphics2D g2d) {
		if(this.source.getRect()!=null&&this.dest.getRect()!=null)
		{
			Line2D line = this.getLine();
			if(this.type==ConnectorType.IS_A) {
				Polygon triangle = this.getTriangle();
				int[] xCross = triangle.xpoints;
				int[] yCross = triangle.ypoints;
				Line2D cross = new Line2D.Double(new Point2D.Double(xCross[1], yCross[1]), new Point2D.Double(xCross[2], yCross[2]));
				if(this.getIntersectionBetweenLines(line, cross)!=null)
					line.setLine(line.getP1(), this.getIntersectionBetweenLines(line, cross));
				g2d.draw(triangle);
			} else  if(this.type==ConnectorType.HAS_A) {
				Polygon square = this.getSquare();
				line.setLine(line.getP2(), new Point2D.Double(square.xpoints[2], square.ypoints[2]));
				g2d.draw(square);
			}
			g2d.draw(line);
		}
	}
	
	public Line2D getLine() {
		Line2D lineBetween = new Line2D.Double(this.source.getCenter(), this.dest.getCenter());
		List<Line2D> myLines = this.source.getRectLines();
		List<Line2D> otherLines = this.dest.getRectLines();
		Point2D p1 = null; //mine
		Point2D p2 = null; //others
		for(Line2D l:myLines) {
			if(lineBetween.intersectsLine(l)) {
				p1 = this.getIntersectionBetweenLines(lineBetween, l);
				break;
			}
		}
		for(Line2D l:otherLines) {
			if(lineBetween.intersectsLine(l)) {
				p2 = this.getIntersectionBetweenLines(lineBetween, l);
				break;
			}
		}
		if(p1!=null&&p2!=null)
			lineBetween.setLine(p1, p2);
		return lineBetween;
	}
	
	private Polygon getTriangle() {
		Polygon triangle = new Polygon();
		Point2D lineEndPoint = this.getLine().getP2();
		Point2D lineStartPoint = this.getLine().getP1();
        double angle = -Math.atan2(lineEndPoint.getY() - lineStartPoint.getY(), lineEndPoint.getX() - lineStartPoint.getX());
		Point2D startPoint1 = new Point2D.Double(
				-Math.cos(-angle)*Configs.ARROW_HEAD_WIDTH+lineEndPoint.getX(),
				-Math.sin(-angle)*Configs.ARROW_HEAD_WIDTH+lineEndPoint.getY()
				);
        int x1 = (int) ((Math.sin(angle) * Configs.ARROW_HEAD_WIDTH) + startPoint1.getX());
        int y1 = (int) ((Math.cos(angle) * Configs.ARROW_HEAD_WIDTH) + startPoint1.getY());
        int x2 = (int) (startPoint1.getX() - (Math.sin(angle) * Configs.ARROW_HEAD_WIDTH));
        int y2 = (int) (startPoint1.getY() - (Math.cos(angle) * Configs.ARROW_HEAD_WIDTH));
		triangle.addPoint((int)lineEndPoint.getX(), (int)lineEndPoint.getY());
		triangle.addPoint(x1, y1);
		triangle.addPoint(x2, y2);
		return triangle;
	}
	
	private Polygon getSquare() {
		Polygon triangle = new Polygon();
		Point2D lineEndPoint = this.getLine().getP1();
		Point2D lineStartPoint = this.getLine().getP2();
        double angle = -Math.atan2(lineEndPoint.getY() - lineStartPoint.getY(), lineEndPoint.getX() - lineStartPoint.getX());
		Point2D startPoint = new Point2D.Double(
				-Math.cos(-angle)*Configs.ARROW_HEAD_WIDTH+lineEndPoint.getX(),
				-Math.sin(-angle)*Configs.ARROW_HEAD_WIDTH+lineEndPoint.getY()
				);
		Point2D startPoint2 = new Point2D.Double(
				-Math.cos(-angle)*Configs.ARROW_HEAD_WIDTH*2+lineEndPoint.getX(),
				-Math.sin(-angle)*Configs.ARROW_HEAD_WIDTH*2+lineEndPoint.getY()
				);
        int x1 = (int) ((Math.sin(angle) * Configs.ARROW_HEAD_WIDTH) + startPoint.getX());
        int y1 = (int) ((Math.cos(angle) * Configs.ARROW_HEAD_WIDTH) + startPoint.getY());
        int x2 = (int) (startPoint.getX() - (Math.sin(angle) * Configs.ARROW_HEAD_WIDTH));
        int y2 = (int) (startPoint.getY() - (Math.cos(angle) * Configs.ARROW_HEAD_WIDTH));
		triangle.addPoint((int)lineEndPoint.getX(), (int)lineEndPoint.getY());
		triangle.addPoint(x1, y1);
		triangle.addPoint((int)startPoint2.getX(), (int)startPoint2.getY());
		triangle.addPoint(x2, y2);
		return triangle;
	}

	
	public Point2D getIntersectionBetweenLines(Line2D line1, Line2D line2) {
		Point2D point = null;
		
		double x1 = line1.getX1();
		double y1 = line1.getY1();
		double x2 = line1.getX2();
		double y2 = line1.getY2();
		
		double x3 = line2.getX1();
		double y3 = line2.getY1();
		double x4 = line2.getX2();
		double y4 = line2.getY2();
		
		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d != 0) {
            double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
            point = new Point2D.Double(xi, yi);
        }
		
		return point;
	}
	
	
}