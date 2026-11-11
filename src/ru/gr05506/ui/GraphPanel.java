package ru.gr05506.ui;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import ru.gr05506.domain.Calculation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class GraphPanel extends JPanel {

    private final Expression expression;
    private double scale = 40.0;
    private final double upper;
    private final double lower;

    public GraphPanel(Calculation calculation) {
        this.expression = new ExpressionBuilder(calculation.getFormula()).variable("x").build();
        setPreferredSize(new Dimension(500,400));
        setBackground(Color.WHITE);
        this.upper = calculation.getUpper();
        this.lower = calculation.getLower();
    }

    public void setScale(double scale) {
        this.scale = scale;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Сглаживание
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width/2;
        int centerY = height/2;

        //Сетка
        drawGridAndAxes(g2, width, height, centerX, centerY);

        //Пределы интегрирования
        drawBorders(g2,height,centerX);

        //График функции
        if (expression != null) {
            drawFunction(g2, width, height, centerX, centerY);
        }

    }

    private void drawGridAndAxes(Graphics2D g2, int width, int height, int centerX, int centerY) {
        // Светло-серая сетка
        g2.setColor(new Color(230, 230, 230));
        for (int x = centerX % (int) scale; x < width; x += scale) {
            g2.drawLine(x, 0, x, height);
        }
        for (int y = centerY % (int) scale; y < height; y += scale) {
            g2.drawLine(0, y, width, y);
        }

        // Оси X и Y
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(0, centerY, width, centerY); // Ось X
        g2.drawLine(centerX, 0, centerX, height); // Ось Y
    }

    private void drawBorders(Graphics2D g2, int height, int centerX) {
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2.0f));

        int screenUpper = (int)(upper*scale) + centerX;
        int screenLower = (int)(lower*scale) + centerX;

        g2.drawLine(screenUpper,0, screenUpper, height);
        g2.drawLine(screenLower,0, screenLower, height);
    }

    private void drawFunction(Graphics2D g2, int width, int height, int centerX, int centerY) {
        g2.setColor(new Color(0, 102, 204));
        g2.setStroke(new BasicStroke(2.0f));

        Path2D path = new Path2D.Double();
        boolean firstPoint = true;

        // Проходим по каждому пикселю ширины экрана
        for (int screenX = 0; screenX < width; screenX++) {
            // Переводим экранный X в математический X
            double mathX = (screenX - centerX) / scale;

            try {
                // Вычисляем Y через exp4j
                double mathY = expression.setVariable("x", mathX).evaluate();

                // Пропускаем разрывы (например, деление на ноль или tan(pi/2))
                if (Double.isNaN(mathY) || Double.isInfinite(mathY)) {
                    firstPoint = true;
                    continue;
                }

                // Переводим математический Y в экранный Y (ось Y в Swing направлена вниз)
                double screenY = centerY - (mathY * scale);

                // Защита от резких скачков при асимптотах (как у 1/x)
                if (screenY < -height || screenY > height * 2) {
                    firstPoint = true;
                    continue;
                }

                if (firstPoint) {
                    path.moveTo(screenX, screenY);
                    firstPoint = false;
                } else {
                    path.lineTo(screenX, screenY);
                }
            } catch (ArithmeticException e) {
                // Игнорируем точки, где функция не определена (например, sqrt(-1))
                firstPoint = true;
            }
        }

        g2.draw(path);
    }
}
