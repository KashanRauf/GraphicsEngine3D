/**
 * Represents a point/vector in 3D space
 */
public class Vector3D {
    float x;
    float y;
    float z;

    // Creates a vector for a specified point
    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Creates a blank vector at 0,0,0
    public Vector3D() {
        this(0f, 0f, 0f);
    }
}
