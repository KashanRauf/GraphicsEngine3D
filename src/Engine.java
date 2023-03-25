import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

// Setting up graphics and GUI
public class Engine extends JPanel {
    // ONLY HERE FOR TEST PURPOSES
    private static Random r = new Random();
    private static int counter = -1;

    private Mesh cube;

    public Engine() {
        setPreferredSize(new Dimension(600, 600));

        // ALL CODE AFTER THIS POINT IS FOR TESTING PURPOSES

        // Creating a basic shape, a cube bounded by (0,0,0) and (1,1,1)
        cube = new Mesh();
        Vector3D a = new Vector3D(0f, 0f, 0f); // At origin
        Vector3D b = new Vector3D(0f, 1f, 0f); // 1 above origin
        Vector3D c = new Vector3D(1f, 0f, 0f); // 1 to right of origin
        Vector3D d = new Vector3D(0f, 0f, 1f); // 1 ahead of origin (in reality high z = further = behind)
        Vector3D e = new Vector3D(0f, 1f, 1f);
        Vector3D f = new Vector3D(1f, 0f, 1f);
        Vector3D g = new Vector3D(1f, 1f, 0f);
        Vector3D h = new Vector3D(1f, 1f, 1f); // Furthest from origin of given points

        // Makes the planes as illustrated in Meshes.png
        cube.add(new Triangle(a, b, g));
        cube.add(new Triangle(a, g, c));

        cube.add(new Triangle(b, e, h));
        cube.add(new Triangle(b, h, g));

        cube.add(new Triangle(c, g, h));
        cube.add(new Triangle(c, h, f));

        // These planes would be invisible according to Meshes.png
        cube.add(new Triangle(a, b, e));
        cube.add(new Triangle(a, e, d));

        cube.add(new Triangle(a, d, f));
        cube.add(new Triangle(a, f, c));

        cube.add(new Triangle(c, g, h));
        cube.add(new Triangle(c, h, f));

        JButton repainter = new JButton();
        repainter.setBounds(260, 500, 80, 40);
        repainter.setText("repaint");
        add(repainter);

        repainter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = r.nextInt(550);
        int y = r.nextInt(550);
        g.setColor(Color.blue);
        g.drawRect(x, y, 50, 50);

        Triangle cur;
        if (counter == -1) {
           cur = cube.triangles.get(0);
        } else {
            cur = cube.triangles.get(counter % 12);
        }
        Vector3D a = cur.vectors[0];
        Vector3D b = cur.vectors[1];
        Vector3D c = cur.vectors[2];
            
        g.setColor(Color.BLACK);
            
        g.drawLine((int) a.getX()*100+20, 600-(int)(a.getY()*100+20), (int) b.getX()*100+20, 600-(int)(b.getY()*100+20));
        g.drawLine((int) b.getX()*100+20, 600-(int)(b.getY()*100+20), (int) c.getX()*100+20, 600-(int)(c.getY()*100+20));
        g.drawLine((int) c.getX()*100+20, 600-(int)(c.getY()*100+20), (int) a.getX()*100+20, 600-(int)(a.getY()*100+20));
        System.out.println("Triangle bounded by: (" + 
        a.getX() + ", " + a.getY() + ", " + a.getZ() + "), (" + 
        b.getX() + ", " + b.getY() + ", " + b.getZ() + "), (" + 
        c.getX() + ", " + c.getY() + ", " + c.getZ() + ")");
        System.out.println(counter%12);
        counter++;
    }
}