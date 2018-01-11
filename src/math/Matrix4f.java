package math;

// Row major
// [  0  1  2  3 ]
// [  4  5  6  7 ]
// [  8  9 10 11 ]
// [ 12 13 14 15 ]

public class Matrix4f {

    public float[] matrix = new float[16];

    public Matrix4f() {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                matrix[row * 4 + column] = 0.0f;
            }
        }
    }

    // row, column: [0,3]
    public void setVal(int row, int column, float value) {
        matrix[row * 4 + column] = value;
    }

    public float getVal(int row, int column) {
        return matrix[row * 4 + column];
    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            result.setVal(i, i, 1.0f);
        }

        return result;
    }

    public static Matrix4f orthographic(float left, float right, float top, float bottom, float near, float far) {
        Matrix4f result = Matrix4f.identity();

        result.setVal(0, 0, 2.0f / (right - left));
        result.setVal(1, 1, 2.0f / (top - bottom));
        result.setVal(2, 2, 2.0f / (near - far));

        result.setVal(0, 3, (left + right) / (left - right));
        result.setVal(1, 3, (bottom + top) / (bottom - top));
        result.setVal(2, 3, (far + near) / (far - near));


        return result;
    }

    public static Matrix4f translate(Vector3f vector) {
        Matrix4f result = Matrix4f.identity();

        result.setVal(0, 3, vector.x);
        result.setVal(1, 3, vector.y);
        result.setVal(2, 3, vector.z);

        return result;
    }

    public static Matrix4f scale(float factorX, float factorY, float factorZ) {
        Matrix4f result = Matrix4f.identity();

        result.setVal(0, 0, factorX);
        result.setVal(1, 1, factorY);
        result.setVal(2, 2, factorZ);

        return result;
    }

    // Rotates about the Z-axis
    public static Matrix4f rotateAboutZ(float degrees){
        Matrix4f result = Matrix4f.identity();

        double radians = Math.toRadians(degrees);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        result.setVal(0, 0, cos);
        result.setVal(0, 1, -1 * sin);
        result.setVal(1, 0, sin);
        result.setVal(1, 1, cos);

        return result;
    }

    public Matrix4f multiply(Matrix4f otherMatrix) {
        float total;
        Matrix4f result = new Matrix4f();

        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                total = 0.0f;
                for (int element = 0; element < 4; element++) {
                    total += matrix[row * 4 + element] * otherMatrix.getVal(element, column);
                }
                result.setVal(row, column, total);
            }
        }

        return result;
    }

    public String toString() {
        String retVal = "";

        for (int i = 0; i < 16; i ++) {
            if (i % 4 == 0) {
                retVal += "\n";
            }
            retVal += " " + matrix[i];
        }

        return retVal;
    }
}
