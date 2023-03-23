public class App {
    public static void main(String[] args) throws Exception {
        // Creating a basic shape, a cube starting at 0,0,0
        Mesh cube = new Mesh();
        Vector3D a = new Vector3D(0f,0f,0f); // At origin
        Vector3D b = new Vector3D( 0f, 0f, 1f); // 1 above origin
        Vector3D c = new Vector3D( 0f, 1f, 0f); // 1 to the right of origin
        Vector3D d = new Vector3D( 1f, 0f, 0f); // 1 Ahead of origin
        // Every vector after is a combination of these (vector addition)
        Vector3D e = new Vector3D( 0f, 1f, 1f);
        Vector3D f = new Vector3D( 1f, 0f, 1f);
        Vector3D g = new Vector3D( 1f, 1f, 0f);
        Vector3D h = new Vector3D( 1f, 1f, 1f);

        // Makes the first plane from Meshes.png
        cube.triangles.add(new Triangle(a, b, e));
        cube.triangles.add(new Triangle(a, e, c));


    }
}
