/**
 * Represents a group of three Vector3Ds.
 */
public class Triangle {
    Vector3D[] vectors;

    public Triangle(Vector3D first, Vector3D second, Vector3D third) {
        // Currently unsure if I need to make deep copies or not, leaving it for now for simplicity
        vectors = new Vector3D[] {first, second, third};
    }
}
