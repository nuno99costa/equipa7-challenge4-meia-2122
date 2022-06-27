package com.isep.meia.grupo7.otimization;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FPViewer extends JPanel {
    ArrayList<Drone> drones;
    String[] droneNames;
    final int border;
    final int width;
    final int height;
    public FPViewer(ArrayList<Drone> drones, String[] droneNames, int width, int height, int border)
    {
        this.drones = drones;
        this.droneNames = droneNames;
        this.border = border;
        this.width = width;
        this.height = height;
        setBackground(Color.BLACK);
    }

    private void drawCircle(Graphics2D g2, Drone d, String label){
        int r = d.getR();
        int x = d.getX();
        int y = d.getY();

        // drawing circles
        g2.drawOval(x+border-r, y+border-r, r*2, r*2);

        if(!label.isEmpty()) {
            g2.drawString(label, x - 5 + border, y + 5 + border);
        }
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawRect(border, border, width, height);
        for(int i=0; i < drones.size(); i++)
        {
            drawCircle(g2, drones.get(i), droneNames[i]);
        }
    }
}
