import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PiApproximationFrame extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int SIZE = 400;
    private final int RADIUS = SIZE/2;
    private final int DELAY = 300;


    private Timer timer;
    private JPanel panel;
    private JPanel smallPanel;
    private JButton button;
    private JLabel totalNumberOfPoints;
    private JLabel numberOfPointsInsideCircle;
    private JLabel approximation;
    private int total;
    private int insideCircle;
    private double piApproximation;

    private BufferedImage image;


    Random random = new Random();

    private boolean isStarted = false;

    public PiApproximationFrame() {
        super("Pi Approximation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image,0,0,null);
                totalNumberOfPoints.setText("Total number of points: " + total);
                numberOfPointsInsideCircle.setText("Points inside circle: " + insideCircle);
                approximation.setText("Approximation: " + piApproximation);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400,400);
            }
        };

        panel.setBackground(Color.WHITE);
        image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,WIDTH, HEIGHT);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        int offsetX = 50;
        int offsetY = 5;
        g2.drawRect(offsetX,offsetY,SIZE,SIZE);
        g2.drawOval(offsetX,offsetY,SIZE,SIZE);
        panel.repaint();

        smallPanel = new JPanel();
        smallPanel.setLayout(new GridLayout(2,2));
        button = new JButton("Start approximation");
        totalNumberOfPoints = new JLabel("Total number of points: " + total);
        numberOfPointsInsideCircle = new JLabel("Points inside circle: " + insideCircle);
        approximation = new JLabel("Approximation: " + piApproximation);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStarted) {
                    button.setText("Stop approximation");
                    isStarted = true;
                    startApproximation();
                } else {
                    button.setText("Start approximation");
                    isStarted = false;
                    stopApproximation();
                }
            }

            public void startApproximation() {
                timer = new Timer(DELAY, null);
                timer.addActionListener(e -> {
                    int offsetX1 = 50;
                    int offsetY1 = 5;
                    int n = 1000;
                    int circleSize = 2;
                    Graphics2D g21 = (Graphics2D) image.getGraphics();
                    for (int i = 0; i < n; i++) {
                        double x = 0 + (SIZE - 0) * random.nextDouble();
                        double y = 0 + (SIZE - 0) * random.nextDouble();
                        total++;

                        // Если истинно, то точка внутри круга
                        if (Math.pow(x-RADIUS,2) + Math.pow(y-RADIUS,2) < Math.pow(RADIUS, 2)) {
                            g21.setColor(Color.RED);
                            Shape circle = new Ellipse2D.Double(x+ offsetX1, y+ offsetY1,circleSize,circleSize);
                            g21.fill(circle);
                            insideCircle++;
                            piApproximation = 4 * (double)insideCircle / (double)total;
                        } else {
                            g21.setColor(Color.BLUE);
                            Shape circle = new Ellipse2D.Double(x+ offsetX1, y+ offsetY1,circleSize,circleSize);
                            g21.fill(circle);
                        }


                    }
                    panel.repaint();
                });
                timer.start();
            }

            public void stopApproximation() {
                timer.stop();
            }
        });

        smallPanel.add(totalNumberOfPoints);
        smallPanel.add(numberOfPointsInsideCircle);
        smallPanel.add(button);
        smallPanel.add(approximation);


        this.add(panel, BorderLayout.CENTER);
        this.add(smallPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}
