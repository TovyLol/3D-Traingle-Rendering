import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Vertex {
    double x, y, z;

    Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class Triangle {
    Vertex v1, v2, v3;
    Color color;

    Triangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }
}

public class DemoViewer {

    private static ArrayList<Triangle> triangles = new ArrayList<>();
    private static double heading = 0;
    private static double pitch = 0;
    private static Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        // window (its not a virus)
        JPanel renderPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // rendering (simple function lmao)
                render(g2, getWidth(), getHeight());
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // lore
        triangles.add(new Triangle(new Vertex(0, 1, 0), new Vertex(-1, -1, 1), new Vertex(1, -1, 1), Color.RED));
        triangles.add(new Triangle(new Vertex(0, 1, 0), new Vertex(-1, -1, -1), new Vertex(-1, -1, 1), Color.GREEN));
        triangles.add(new Triangle(new Vertex(0, 1, 0), new Vertex(1, -1, -1), new Vertex(-1, -1, -1), Color.BLUE));
        triangles.add(new Triangle(new Vertex(0, 1, 0), new Vertex(1, -1, 1), new Vertex(1, -1, -1), Color.YELLOW));

        // rondam
        Timer timer = new Timer(100, e -> {
            heading += Math.toRadians(random.nextInt(5) - 10);
            pitch += Math.toRadians(random.nextInt(5) - 10);
            frame.repaint();
        });
        timer.start();
    }

    private static void render(Graphics2D g, int width, int height) {
        for (Triangle triangle : triangles) {
            Polygon p = new Polygon();

            Vertex[] vertices = {triangle.v1, triangle.v2, triangle.v3};

            for (Vertex v : vertices) {
                double xRot = v.x * Math.cos(heading) - v.z * Math.sin(heading);
                double zRot = v.z * Math.cos(heading) + v.x * Math.sin(heading);

                double yRot = v.y * Math.cos(pitch) - zRot * Math.sin(pitch);
                double zRot2 = zRot * Math.cos(pitch) + v.y * Math.sin(pitch);

                int xScreen = (int) (width / 2 + xRot * 100);
                int yScreen = (int) (height / 2 - yRot * 100);

                p.addPoint(xScreen, yScreen);
            }

            g.setColor(triangle.color);
            g.fillPolygon(p);
        }
    }
}
