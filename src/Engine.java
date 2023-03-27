import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

// Setting up graphics and GUI
public class Engine extends JPanel {
    // ONLY HERE FOR TEST PURPOSES

    final static int WIDTH = 600;
    final static int HEIGHT = 600;
    final static int DEPTH = 1;
    private Mesh cube;

    private float R = (float)WIDTH/2;
    @SuppressWarnings("unused")
    private float L = -1*R;
    private float B = (float)HEIGHT/2;
    @SuppressWarnings("unused")
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

        // MOST CODE AFTER THIS POINT IS FOR TESTING PURPOSES

        System.out.println("PROJECTION PERSPECTIVE MATRIX");
        System.out.println("| " + mat[0][0] + " " + mat[0][1] + " " + mat[0][2] + " " + mat[0][3] + " |\n"
        + "| " + mat[1][0]  + " " + mat[1][1]  + " " + mat[1][2]  + " " + mat[1][3] + " |\n"
        + "| " + mat[2][0]  + " " + mat[2][1]  + " " + mat[2][2]  + " " + mat[2][3] + " |\n"
        + "| " + mat[3][0]  + " " + mat[3][1]  + " " + mat[3][2]  + " " + mat[3][3] + " |\n");

        // Creating a basic shape, a cube bounded by (0,0,0) and (1,1,1)
        cube = new Mesh();
        Vector3D a = new Vector3D(0f, 0f, 0); // At origin
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

        // These planes would be invisible according to Meshes.png since they'd be behind the rest of the cube
        cube.add(new Triangle(a, b, e));
        cube.add(new Triangle(a, e, d));

        cube.add(new Triangle(a, d, f));
        cube.add(new Triangle(a, f, c));

        cube.add(new Triangle(f, d, e));
        cube.add(new Triangle(f, e, h));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        // Axis lines
        g2D.setColor(Color.GREEN);
        g2D.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
        g2D.drawLine(0, HEIGHT/2, WIDTH, HEIGHT/2);

        g2D.translate(WIDTH/2, HEIGHT/2);
        g2D.setColor(Color.BLACK);
        for (Triangle cur : cube.triangles) {
            float[] a = calculatePoint(cur.vectors[0].toArray());
            float[] b = calculatePoint(cur.vectors[1].toArray());
            float[] c = calculatePoint(cur.vectors[2].toArray());

            // Draws the triangle
            Path2D line = new Path2D.Float();
            line.moveTo(a[0], -a[1]);
            line.lineTo(b[0], -b[1]);
            line.lineTo(c[0], -c[1]);
            line.closePath();
            g2D.draw(line);
        }
    }

    // Uses matrix multiplication to perspective project a point
    public float[] calculatePoint(float[] p) {
        float[] projectedPoint = new float[] {
            mat[0][0]*p[0] + mat[0][1]*p[1] + mat[0][2]*p[2] + mat[0][3]*p[3],
            mat[1][0]*p[0] + mat[1][1]*p[1] + mat[1][2]*p[2] + mat[1][3]*p[3],
            mat[2][0]*p[0] + mat[2][1]*p[1] + mat[2][2]*p[2] + mat[2][3]*p[3],
            mat[3][0]*p[0] + mat[3][1]*p[1] + mat[3][2]*p[2] + mat[3][3]*p[3]
        };
        return projectedPoint;
    }
}