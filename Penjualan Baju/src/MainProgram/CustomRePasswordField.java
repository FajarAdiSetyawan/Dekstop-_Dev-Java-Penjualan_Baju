/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

/**
 *
 * @author Taopik Ridwan
 */
public class CustomRePasswordField extends JPasswordField {

    private String placeholder = "Ulangi Password";

    public CustomRePasswordField() {
        initListener();
    }

    public CustomRePasswordField(String text) {
        super(text);
        initListener();
    }

    public CustomRePasswordField(int columns) {
        super(columns);
        initListener();
    }

    public CustomRePasswordField(String text, int columns) {
        super(text, columns);
        initListener();
    }

    private void initListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                repaint();
            }
        });
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if ((new String(getPassword())).trim().equals("")) {
            Font font = getFont().deriveFont(Font.BOLD);

            Graphics2D g2d = (Graphics2D) g;
            FontMetrics fontMetrics = g2d.getFontMetrics(font);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLACK);
            g2d.setFont(font);

            java.awt.geom.Rectangle2D rect = fontMetrics.getStringBounds(placeholder, g2d);
            int textHeight = (int) rect.getHeight();
            g2d.drawString(placeholder, 5, textHeight + 3);
        }
    }
}
