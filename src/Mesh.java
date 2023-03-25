import java.util.ArrayList;

/**
 * A mesh of triangles, forms planes/shapes.
 */
public class Mesh {
    public ArrayList<Triangle> triangles;

    public Mesh() {
        triangles = new ArrayList<>();
    }

    public void add(Triangle tri) {
        triangles.add(tri);
    }
}