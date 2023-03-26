/**
 * Represents a point/vector in 3D space
 * Currently does nothing other than hold the data
 */
@SuppressWarnings("unused")
public class Vector3D {
    private float x;
    private float y;
    private float z;

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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float[] toArray() {
        return new float[] {x, y, z, 1};
    }
}
