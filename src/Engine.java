import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JPanel;

// Setting up graphics and GUI
public class Engine extends JPanel {
    // ONLY HERE FOR TEST PURPOSES
    private static Random r = new Random();
    private static int counter = -1;

    final static int WIDTH = 600;
    final static int HEIGHT = 600;
    final static int DEPTH = 1;
    private Mesh cube;

    private float R = (float)WIDTH/2;
    private float L = -1*R;
    private float B = (float)HEIGHT/2;
    private float T = -1*B;
    private float N = 100.0f;
    private float F = 1000.0f;

    // Perspective projection matrix
    private float[][] mat = new float[][] {
        // {(WIDTH*N)/(R/L), 0, -(R+L)/(R-L), 0},
        // {0, (WIDTH*N)/(B-T), -(B+T)/(B-T), 0},
        // {0, 0, F/(F-N), DEPTH*(-(F*N)/(F-N))},
        // {0, 0, 1, 0}

        // Based on the assumtion that frustum is centered at the origin (0,0,0)
        // L=-R, T=-B -> B+T = 0, B-T = 2B (Same for L+R/L-R)
        {N/R, 0, 0, 0},
        {0, N/B, 0, 0},
        {0, 0, F/(F-N), -(F*N)/(F-N)},
        {0, 0, 1, 0}

        // Calculations for B and R can be used to get another form
        // B=Ntan(theta/2), R=(NW/H)tan(theta/2)
        // {1/(WIDTH/HEIGHT)*Math.tan(theta/2), 0, 0, 0},
        // {0, 1/Math.tan(theta/2), 0, 0},
        // {0, 0 F/(F-N), -(F*N)/(F-N)},
        // {0, 0, 1, 0}
        // Not using since I'm not sure what angle theta should be equal to
    };

    public Engine() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // ALL CODE AFTER THIS POINT IS FOR TESTING PURPOSES

        System.out.println("PROJECTION PERSPECTIVE MATRIX");
        System.out.println(R);
        System.out.println(mat[0][0] + ", " + mat[1][1] + ", " + mat[2][2] + ", " + mat[2][3] +"\n\n");

        // Creating a basic shape, a cube bounded by (0,0,0) and (1,1,1)
        cube = new Mesh();
        Vector3D a = new Vector3D(0f, 0f, 0f); // At origin
        Vector3D b = new Vector3D(0f, 100f, 0f); // 1 above origin
        Vector3D c = new Vector3D(100f, 0f, 0f); // 1 to right of origin
        Vector3D d = new Vector3D(0f, 0f, 100f); // 1 ahead of origin (in reality high z = further = behind)
        Vector3D e = new Vector3D(0f, 100f, 100f);
        Vector3D f = new Vector3D(100f, 0f, 100f);
        Vector3D g = new Vector3D(100f, 100f, 0f);
        Vector3D h = new Vector3D(100f, 100f, 100f); // Furthest from origin of given points

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

        g.setColor(Color.GREEN);
        g.drawLine(300, 0, 300, 600);
        g.drawLine(0, 300, 600, 300);

        // Triangle cur;
        // if (counter == -1) {
        //    cur = cube.triangles.get(0);
        // } else {
        //     cur = cube.triangles.get(counter % 12);
        // }

        // float[] a = calculatePoint(cur.vectors[0].toArray());
        // float[] b = calculatePoint(cur.vectors[1].toArray());
        // float[] c = calculatePoint(cur.vectors[2].toArray());
            
        // g.setColor(Color.BLACK);
            
        // g.drawLine((int) a[0]*100+20, 300-(int)(a[1]*100+20), (int) b[0]*100+20, 600-(int)(b[1]*100+20));
        // g.drawLine((int) b[0]*100+20, 300-(int)(b[1]*100+20), (int) c[0]*100+20, 600-(int)(c[1]*100+20));
        // g.drawLine((int) c[0]*100+20, 300-(int)(c[1]*100+20), (int) a[0]*100+20, 600-(int)(a[1]*100+20));
        // System.out.println("Triangle bounded by: (" + 
        // (a[0]*100+20) + ", " + (300-(int)(a[1]*100+20)) + ", " + a[2] + "), (" + 
        // (b[0]*100+20) + ", " + (300-(int)(b[1]*100+20)) + ", " + b[2] + "), (" + 
        // (c[0]*100+20) + ", " + (300-(int)(c[1]*100+20)) + ", " + c[2] + ")");
        // System.out.println(counter%12);
        // counter++;

        g.setColor(Color.BLACK);
        for (Triangle cur : cube.triangles) {
            float[] a = calculatePoint(cur.vectors[0].toArray());
            float[] b = calculatePoint(cur.vectors[1].toArray());
            float[] c = calculatePoint(cur.vectors[2].toArray());

            // All the math being done on these points, is only to translate to (0,0)
            g.drawLine((WIDTH/2)+(int)(a[0]), (HEIGHT/2)-(int)((a[1])), (WIDTH/2)+(int)(b[0]), (HEIGHT/2)-(int)((b[1])));
            g.drawLine((WIDTH/2)+(int)(b[0]), (HEIGHT/2)-(int)((b[1])), (WIDTH/2)+(int)(c[0]), (HEIGHT/2)-(int)((c[1])));
            g.drawLine((WIDTH/2)+(int)(c[0]), (HEIGHT/2)-(int)((c[1])), (WIDTH/2)+(int)(a[0]), (HEIGHT/2)-(int)((a[1])));
            System.out.println("Triangle: (" + ((a[0]*90)+200) + ", " + (200-(int)((a[1]*90)))
                                   + ") , (" + ((b[0]*90)+200) + ", " + (200-(int)((b[1]*90)))
                                   + ") , (" + ((c[0]*90)+200) + ", " + (200-(int)((c[1]*90))) + ")");
        }
    }

    // Uses matrix multiplication to perspective project a point
    public float[] calculatePoint(float[] p) {
        // Already tested, function works properly
        float[] projectedPoint = new float[] {
            mat[0][0]*p[0] + mat[0][1]*p[1] + mat[0][2]*p[2] + mat[0][3]*p[3],
            mat[1][0]*p[0] + mat[1][1]*p[1] + mat[1][2]*p[2] + mat[1][3]*p[3],
            mat[2][0]*p[0] + mat[2][1]*p[1] + mat[2][2]*p[2] + mat[2][3]*p[3],
            mat[3][0]*p[0] + mat[3][1]*p[1] + mat[3][2]*p[2] + mat[3][3]*p[3]
        };
        return projectedPoint;
    }
}