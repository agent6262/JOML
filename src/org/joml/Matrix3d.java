/*
 * (C) Copyright 2015-2017 Richard Greenlees

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

import org.joml.api.AxisAngle4dc;
import org.joml.api.AxisAngle4fc;
import org.joml.api.matrix.*;
import org.joml.api.quaternion.IQuaterniond;
import org.joml.api.quaternion.IQuaternionf;
import org.joml.api.quaternion.Quaterniondc;
import org.joml.api.quaternion.Quaternionfc;
import org.joml.api.vector.IVector3d;
import org.joml.api.vector.IVector3f;
import org.joml.api.vector.Vector3dc;
import org.joml.api.vector.Vector3fc;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Contains the definition of a 3x3 matrix of doubles, and associated functions to transform it. The matrix is
 * column-major to match OpenGL's interpretation, and it looks like this:
 * <p>
 * m00  m10  m20<br> m01  m11  m21<br> m02  m12  m22<br>
 *
 * @author Richard Greenlees
 * @author Kai Burjack
 */
public class Matrix3d extends Matrix3dc implements Externalizable {

    private static final long serialVersionUID = 1L;

    public double m00, m01, m02;
    public double m10, m11, m12;
    public double m20, m21, m22;

    /**
     * Create a new {@link Matrix3d} and initialize it to {@link #identity() identity}.
     */
    public Matrix3d() {
        m00 = 1.0;
        m11 = 1.0;
        m22 = 1.0;
    }

    /**
     * Create a new {@link Matrix3d} and initialize it with the values from the given matrix.
     *
     * @param mat the matrix to initialize this matrix with
     */
    public Matrix3d(IMatrix3d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    /**
     * Create a new {@link Matrix3d} and initialize it with the values from the given matrix.
     *
     * @param mat the matrix to initialize this matrix with
     */
    public Matrix3d(IMatrix3f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    /**
     * Create a new {@link Matrix3d} and make it a copy of the upper left 3x3 of the given {@link IMatrix4f}.
     *
     * @param mat the {@link IMatrix4f} to copy the values from
     */
    public Matrix3d(IMatrix4f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    /**
     * Create a new {@link Matrix3d} and make it a copy of the upper left 3x3 of the given {@link IMatrix4d}.
     *
     * @param mat the {@link IMatrix4d} to copy the values from
     */
    public Matrix3d(IMatrix4d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
    }

    /**
     * Create a new {@link Matrix3d} and initialize its elements with the given values.
     *
     * @param m00 the value of m00
     * @param m01 the value of m01
     * @param m02 the value of m02
     * @param m10 the value of m10
     * @param m11 the value of m11
     * @param m12 the value of m12
     * @param m20 the value of m20
     * @param m21 the value of m21
     * @param m22 the value of m22
     */
    public Matrix3d(double m00, double m01, double m02,
                    double m10, double m11, double m12,
                    double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    //#ifdef __HAS_NIO__

    /**
     * Create a new {@link Matrix3d} by reading its 9 double components from the given {@link DoubleBuffer} at the
     * buffer's current position.
     * <p>
     * That DoubleBuffer is expected to hold the values in column-major order.
     * <p>
     * The buffer's position will not be changed by this method.
     *
     * @param buffer the {@link DoubleBuffer} to read the matrix values from
     */
    public Matrix3d(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
    }
    //#endif

    /**
     * Create a new {@link Matrix3d} and initialize its three columns using the supplied vectors.
     *
     * @param col0 the first column
     * @param col1 the second column
     * @param col2 the third column
     */
    public Matrix3d(IVector3d col0, IVector3d col1, IVector3d col2) {
        this.m00 = col0.x();
        this.m01 = col0.y();
        this.m02 = col0.z();
        this.m10 = col1.x();
        this.m11 = col1.y();
        this.m12 = col1.z();
        this.m20 = col2.x();
        this.m21 = col2.y();
        this.m22 = col2.z();
    }

    public double m00() {
        return m00;
    }

    public double m01() {
        return m01;
    }

    public double m02() {
        return m02;
    }

    public double m10() {
        return m10;
    }

    public double m11() {
        return m11;
    }

    public double m12() {
        return m12;
    }

    public double m20() {
        return m20;
    }

    public double m21() {
        return m21;
    }

    public double m22() {
        return m22;
    }

    public Matrix3dc m00(double m00) {
        this.m00 = m00;
        return this;
    }

    public Matrix3dc m01(double m01) {
        this.m01 = m01;
        return this;
    }

    public Matrix3dc m02(double m02) {
        this.m02 = m02;
        return this;
    }

    public Matrix3dc m10(double m10) {
        this.m10 = m10;
        return this;
    }

    public Matrix3dc m11(double m11) {
        this.m11 = m11;
        return this;
    }

    public Matrix3dc m12(double m12) {
        this.m12 = m12;
        return this;
    }

    public Matrix3dc m20(double m20) {
        this.m20 = m20;
        return this;
    }

    public Matrix3dc m21(double m21) {
        this.m21 = m21;
        return this;
    }

    public Matrix3dc m22(double m22) {
        this.m22 = m22;
        return this;
    }

    public Matrix3dc set(IMatrix3d m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        return this;
    }

    public Matrix3dc set(IMatrix3f m) {
        m00 = m.m00();
        m01 = m.m01();
        m02 = m.m02();
        m10 = m.m10();
        m11 = m.m11();
        m12 = m.m12();
        m20 = m.m20();
        m21 = m.m21();
        m22 = m.m22();
        return this;
    }

    public Matrix3dc set(IMatrix4f mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        return this;
    }

    public Matrix3dc set(IMatrix4d mat) {
        m00 = mat.m00();
        m01 = mat.m01();
        m02 = mat.m02();
        m10 = mat.m10();
        m11 = mat.m11();
        m12 = mat.m12();
        m20 = mat.m20();
        m21 = mat.m21();
        m22 = mat.m22();
        return this;
    }

    public Matrix3dc set(AxisAngle4fc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        m00 = c + x * x * omc;
        m11 = c + y * y * omc;
        m22 = c + z * z * omc;
        double tmp1 = x * y * omc;
        double tmp2 = z * s;
        m10 = tmp1 - tmp2;
        m01 = tmp1 + tmp2;
        tmp1 = x * z * omc;
        tmp2 = y * s;
        m20 = tmp1 + tmp2;
        m02 = tmp1 - tmp2;
        tmp1 = y * z * omc;
        tmp2 = x * s;
        m21 = tmp1 - tmp2;
        m12 = tmp1 + tmp2;
        return this;
    }

    public Matrix3dc set(AxisAngle4dc axisAngle) {
        double x = axisAngle.x();
        double y = axisAngle.y();
        double z = axisAngle.z();
        double angle = axisAngle.angle();
        double invLength = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= invLength;
        y *= invLength;
        z *= invLength;
        double s = Math.sin(angle);
        double c = Math.cosFromSin(s, angle);
        double omc = 1.0 - c;
        m00 = c + x * x * omc;
        m11 = c + y * y * omc;
        m22 = c + z * z * omc;
        double tmp1 = x * y * omc;
        double tmp2 = z * s;
        m10 = tmp1 - tmp2;
        m01 = tmp1 + tmp2;
        tmp1 = x * z * omc;
        tmp2 = y * s;
        m20 = tmp1 + tmp2;
        m02 = tmp1 - tmp2;
        tmp1 = y * z * omc;
        tmp2 = x * s;
        m21 = tmp1 - tmp2;
        m12 = tmp1 + tmp2;
        return this;
    }

    public Matrix3dc set(IQuaternionf q) {
        return rotation(q);
    }

    public Matrix3dc set(IQuaterniond q) {
        return rotation(q);
    }

    public Matrix3dc mul(IMatrix3d right) {
        return mul(right, this);
    }

    public Matrix3dc mul(IMatrix3d right, Matrix3dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc mulLocal(IMatrix3d left) {
        return mulLocal(left, this);
    }

    public Matrix3dc mulLocal(IMatrix3d left, Matrix3dc dest) {
        double nm00 = left.m00() * m00 + left.m10() * m01 + left.m20() * m02;
        double nm01 = left.m01() * m00 + left.m11() * m01 + left.m20() * m02;
        double nm10 = left.m00() * m10 + left.m10() * m11 + left.m20() * m12;
        double nm11 = left.m01() * m10 + left.m11() * m11 + left.m20() * m12;
        double nm20 = left.m00() * m20 + left.m10() * m21 + left.m20() * m22;
        double nm21 = left.m01() * m20 + left.m11() * m21 + left.m21() * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m20(nm20);
        dest.m21(nm21);
        return dest;
    }

    public Matrix3dc mul(IMatrix3f right) {
        return mul(right, this);
    }

    public Matrix3dc mul(IMatrix3f right, Matrix3dc dest) {
        double nm00 = m00 * right.m00() + m10 * right.m01() + m20 * right.m02();
        double nm01 = m01 * right.m00() + m11 * right.m01() + m21 * right.m02();
        double nm02 = m02 * right.m00() + m12 * right.m01() + m22 * right.m02();
        double nm10 = m00 * right.m10() + m10 * right.m11() + m20 * right.m12();
        double nm11 = m01 * right.m10() + m11 * right.m11() + m21 * right.m12();
        double nm12 = m02 * right.m10() + m12 * right.m11() + m22 * right.m12();
        double nm20 = m00 * right.m20() + m10 * right.m21() + m20 * right.m22();
        double nm21 = m01 * right.m20() + m11 * right.m21() + m21 * right.m22();
        double nm22 = m02 * right.m20() + m12 * right.m21() + m22 * right.m22();
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc set(double m00, double m01, double m02,
                         double m10, double m11, double m12,
                         double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        return this;
    }

    public Matrix3dc set(double m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
        return this;
    }

    public Matrix3dc set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
        return this;
    }

    public double determinant() {
        return (m00 * m11 - m01 * m10) * m22
                + (m02 * m10 - m00 * m12) * m21
                + (m01 * m12 - m02 * m11) * m20;
    }

    public Matrix3dc invert() {
        return invert(this);
    }

    public Matrix3dc invert(Matrix3dc dest) {
        double s = determinant();
        // client must make sure that matrix is invertible
        s = 1.0 / s;
        double nm00 = (m11 * m22 - m21 * m12) * s;
        double nm01 = (m21 * m02 - m01 * m22) * s;
        double nm02 = (m01 * m12 - m11 * m02) * s;
        double nm10 = (m20 * m12 - m10 * m22) * s;
        double nm11 = (m00 * m22 - m20 * m02) * s;
        double nm12 = (m10 * m02 - m00 * m12) * s;
        double nm20 = (m10 * m21 - m20 * m11) * s;
        double nm21 = (m20 * m01 - m00 * m21) * s;
        double nm22 = (m00 * m11 - m10 * m01) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc transpose() {
        return transpose(this);
    }

    public Matrix3dc transpose(Matrix3dc dest) {
        dest.set(m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22);
        return dest;
    }

    public String toString() {
        DecimalFormat formatter = new DecimalFormat(" 0.000E0;-");
        String str = toString(formatter);
        StringBuffer res = new StringBuffer();
        int eIndex = Integer.MIN_VALUE;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'E') {
                eIndex = i;
            } else if (c == ' ' && eIndex == i - 1) {
                // workaround Java 1.4 DecimalFormat bug
                res.append('+');
                continue;
            } else if (Character.isDigit(c) && eIndex == i - 1) {
                res.append('+');
            }
            res.append(c);
        }
        return res.toString();
    }

    public String toString(NumberFormat formatter) {
        return formatter.format(m00) + " " + formatter.format(m10) + " " + formatter.format(m20) + "\n"
                + formatter.format(m01) + " " + formatter.format(m11) + " " + formatter.format(m21) + "\n"
                + formatter.format(m02) + " " + formatter.format(m12) + " " + formatter.format(m22) + "\n";
    }

    public Matrix3dc get(Matrix3dc dest) {
        return dest.set(this);
    }

    public AxisAngle4fc getRotation(AxisAngle4fc dest) {
        return dest.set(this);
    }

    public Quaternionfc getUnnormalizedRotation(Quaternionfc dest) {
        return dest.setFromUnnormalized(this);
    }

    public Quaternionfc getNormalizedRotation(Quaternionfc dest) {
        return dest.setFromNormalized(this);
    }

    public Quaterniondc getUnnormalizedRotation(Quaterniondc dest) {
        return dest.setFromUnnormalized(this);
    }

    public Quaterniondc getNormalizedRotation(Quaterniondc dest) {
        return dest.setFromNormalized(this);
    }

    //#ifdef __HAS_NIO__

    public DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public DoubleBuffer get(int index, DoubleBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public FloatBuffer get(int index, FloatBuffer buffer) {
        MemUtil.INSTANCE.putf(this, index, buffer);
        return buffer;
    }

    public ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }

    public ByteBuffer get(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.put(this, index, buffer);
        return buffer;
    }

    public ByteBuffer getFloats(ByteBuffer buffer) {
        return getFloats(buffer.position(), buffer);
    }

    public ByteBuffer getFloats(int index, ByteBuffer buffer) {
        MemUtil.INSTANCE.putf(this, index, buffer);
        return buffer;
    }
    //#endif

    public double[] get(double[] arr, int offset) {
        arr[offset + 0] = m00;
        arr[offset + 1] = m01;
        arr[offset + 2] = m02;
        arr[offset + 3] = m10;
        arr[offset + 4] = m11;
        arr[offset + 5] = m12;
        arr[offset + 6] = m20;
        arr[offset + 7] = m21;
        arr[offset + 8] = m22;
        return arr;
    }

    public double[] get(double[] arr) {
        return get(arr, 0);
    }

    public float[] get(float[] arr, int offset) {
        arr[offset + 0] = (float) m00;
        arr[offset + 1] = (float) m01;
        arr[offset + 2] = (float) m02;
        arr[offset + 3] = (float) m10;
        arr[offset + 4] = (float) m11;
        arr[offset + 5] = (float) m12;
        arr[offset + 6] = (float) m20;
        arr[offset + 7] = (float) m21;
        arr[offset + 8] = (float) m22;
        return arr;
    }

    public float[] get(float[] arr) {
        return get(arr, 0);
    }

    //#ifdef __HAS_NIO__

    public Matrix3dc set(DoubleBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }

    public Matrix3dc set(FloatBuffer buffer) {
        MemUtil.INSTANCE.getf(this, buffer.position(), buffer);
        return this;
    }

    public Matrix3dc set(ByteBuffer buffer) {
        MemUtil.INSTANCE.get(this, buffer.position(), buffer);
        return this;
    }

    public Matrix3dc setFloats(ByteBuffer buffer) {
        MemUtil.INSTANCE.getf(this, buffer.position(), buffer);
        return this;
    }
    //#endif

    public Matrix3dc set(IVector3d col0,
                         IVector3d col1,
                         IVector3d col2) {
        this.m00 = col0.x();
        this.m01 = col0.y();
        this.m02 = col0.z();
        this.m10 = col1.x();
        this.m11 = col1.y();
        this.m12 = col1.z();
        this.m20 = col2.x();
        this.m21 = col2.y();
        this.m22 = col2.z();
        return this;
    }

    public Matrix3dc zero() {
        m00 = 0.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 0.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 0.0;
        return this;
    }

    public Matrix3dc identity() {
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        return this;
    }

    public Matrix3dc scaling(double factor) {
        m00 = factor;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = factor;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = factor;
        return this;
    }

    public Matrix3dc scaling(double x, double y, double z) {
        m00 = x;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = y;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = z;
        return this;
    }

    public Matrix3dc scaling(IVector3d xyz) {
        m00 = xyz.x();
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = xyz.y();
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = xyz.z();
        return this;
    }

    public Matrix3dc scale(IVector3d xyz, Matrix3dc dest) {
        return scale(xyz.x(), xyz.y(), xyz.z(), dest);
    }

    public Matrix3dc scale(IVector3d xyz) {
        return scale(xyz.x(), xyz.y(), xyz.z(), this);
    }

    public Matrix3dc scale(double x, double y, double z, Matrix3dc dest) {
        // scale matrix elements:
        // m00 = x, m11 = y, m22 = z
        // all others = 0
        dest.m00(m00 * x);
        dest.m01(m01 * x);
        dest.m02(m02 * x);
        dest.m10(m10 * y);
        dest.m11(m11 * y);
        dest.m12(m12 * y);
        dest.m20(m20 * z);
        dest.m21(m21 * z);
        dest.m22(m22 * z);
        return dest;
    }

    public Matrix3dc scale(double x, double y, double z) {
        return scale(x, y, z, this);
    }

    public Matrix3dc scale(double xyz, Matrix3dc dest) {
        return scale(xyz, xyz, xyz, dest);
    }

    public Matrix3dc scale(double xyz) {
        return scale(xyz, xyz, xyz);
    }

    public Matrix3dc scaleLocal(double x, double y, double z, Matrix3dc dest) {
        double nm00 = x * m00;
        double nm01 = y * m01;
        double nm02 = z * m02;
        double nm10 = x * m10;
        double nm11 = y * m11;
        double nm12 = z * m12;
        double nm20 = x * m20;
        double nm21 = y * m21;
        double nm22 = z * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc scaleLocal(double x, double y, double z) {
        return scaleLocal(x, y, z, this);
    }

    public Matrix3dc rotation(double angle, IVector3d axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix3dc rotation(double angle, IVector3f axis) {
        return rotation(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix3dc rotation(AxisAngle4fc axisAngle) {
        return rotation(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix3dc rotation(AxisAngle4dc axisAngle) {
        return rotation(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix3dc rotation(double angle, double x, double y, double z) {
        double sin = Math.sin(angle);
        double cos = Math.cosFromSin(sin, angle);
        double C = 1.0 - cos;
        double xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
        return this;
    }

    public Matrix3dc rotationX(double ang) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        m00 = 1.0;
        m01 = 0.0;
        m02 = 0.0;
        m10 = 0.0;
        m11 = cos;
        m12 = sin;
        m20 = 0.0;
        m21 = -sin;
        m22 = cos;
        return this;
    }

    public Matrix3dc rotationY(double ang) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        m00 = cos;
        m01 = 0.0;
        m02 = -sin;
        m10 = 0.0;
        m11 = 1.0;
        m12 = 0.0;
        m20 = sin;
        m21 = 0.0;
        m22 = cos;
        return this;
    }

    public Matrix3dc rotationZ(double ang) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        m00 = cos;
        m01 = sin;
        m02 = 0.0;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0;
        m20 = 0.0;
        m21 = 0.0;
        m22 = 1.0;
        return this;
    }

    public Matrix3dc rotationXYZ(double angleX, double angleY, double angleZ) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm11 = cosX;
        double nm12 = sinX;
        double nm21 = m_sinX;
        double nm22 = cosX;
        // rotateY
        double nm00 = cosY;
        double nm01 = nm21 * m_sinY;
        double nm02 = nm22 * m_sinY;
        m20 = sinY;
        m21 = nm21 * cosY;
        m22 = nm22 * cosY;
        // rotateZ
        m00 = nm00 * cosZ;
        m01 = nm01 * cosZ + nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ;
        m11 = nm01 * m_sinZ + nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        return this;
    }

    public Matrix3dc rotationZYX(double angleZ, double angleY, double angleX) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = cosZ;
        double nm01 = sinZ;
        double nm10 = m_sinZ;
        double nm11 = cosZ;
        // rotateY
        double nm20 = nm00 * sinY;
        double nm21 = nm01 * sinY;
        double nm22 = cosY;
        m00 = nm00 * cosY;
        m01 = nm01 * cosY;
        m02 = m_sinY;
        // rotateX
        m10 = nm10 * cosX + nm20 * sinX;
        m11 = nm11 * cosX + nm21 * sinX;
        m12 = nm22 * sinX;
        m20 = nm10 * m_sinX + nm20 * cosX;
        m21 = nm11 * m_sinX + nm21 * cosX;
        m22 = nm22 * cosX;
        return this;
    }

    public Matrix3dc rotationYXZ(double angleY, double angleX, double angleZ) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm00 = cosY;
        double nm02 = m_sinY;
        double nm20 = sinY;
        double nm22 = cosY;
        // rotateX
        double nm10 = nm20 * sinX;
        double nm11 = cosX;
        double nm12 = nm22 * sinX;
        m20 = nm20 * cosX;
        m21 = m_sinX;
        m22 = nm22 * cosX;
        // rotateZ
        m00 = nm00 * cosZ + nm10 * sinZ;
        m01 = nm11 * sinZ;
        m02 = nm02 * cosZ + nm12 * sinZ;
        m10 = nm00 * m_sinZ + nm10 * cosZ;
        m11 = nm11 * cosZ;
        m12 = nm02 * m_sinZ + nm12 * cosZ;
        return this;
    }

    public Matrix3dc rotation(IQuaterniond quat) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        m00 = w2 + x2 - z2 - y2;
        m01 = xy + zw + zw + xy;
        m02 = xz - yw + xz - yw;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        return this;
    }

    public Matrix3dc rotation(IQuaternionf quat) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        m00 = w2 + x2 - z2 - y2;
        m01 = xy + zw + zw + xy;
        m02 = xz - yw + xz - yw;
        m10 = -zw + xy - zw + xy;
        m11 = y2 - z2 + w2 - x2;
        m12 = yz + yz + xw + xw;
        m20 = yw + xz + xz + yw;
        m21 = yz + yz - xw - xw;
        m22 = z2 - y2 - x2 + w2;
        return this;
    }

    public Vector3dc transform(Vector3dc v) {
        return v.mul(this);
    }

    public Vector3dc transform(IVector3d v, Vector3dc dest) {
        v.mul(this, dest);
        return dest;
    }

    public Vector3fc transform(Vector3fc v) {
        return v.mul(this);
    }

    public Vector3fc transform(IVector3f v, Vector3fc dest) {
        v.mul(this, dest);
        return dest;
    }

    public Vector3dc transform(double x, double y, double z, Vector3dc dest) {
        dest.set(m00 * x + m10 * y + m20 * z,
                m01 * x + m11 * y + m21 * z,
                m02 * x + m12 * y + m22 * z);
        return dest;
    }

    public Vector3dc transformTranspose(Vector3dc v) {
        return v.mulTranspose(this);
    }

    public Vector3dc transformTranspose(IVector3d v, Vector3dc dest) {
        v.mulTranspose(this, dest);
        return dest;
    }

    public Vector3dc transformTranspose(double x, double y, double z, Vector3dc dest) {
        dest.set(m00 * x + m01 * y + m02 * z,
                m10 * x + m11 * y + m12 * z,
                m20 * x + m21 * y + m22 * z);
        return dest;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m02);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m12);
        out.writeDouble(m20);
        out.writeDouble(m21);
        out.writeDouble(m22);
    }

    public void readExternal(ObjectInput in) throws IOException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m02 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m12 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
        m22 = in.readDouble();
    }

    public Matrix3dc rotateX(double ang, Matrix3dc dest) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        double rm11 = cos;
        double rm21 = -sin;
        double rm12 = sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm10 = m10 * rm11 + m20 * rm12;
        double nm11 = m11 * rm11 + m21 * rm12;
        double nm12 = m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20(m10 * rm21 + m20 * rm22);
        dest.m21(m11 * rm21 + m21 * rm22);
        dest.m22(m12 * rm21 + m22 * rm22);
        // set other values
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m00(m00);
        dest.m01(m01);
        dest.m02(m02);

        return dest;
    }

    public Matrix3dc rotateX(double ang) {
        return rotateX(ang, this);
    }

    public Matrix3dc rotateY(double ang, Matrix3dc dest) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        double rm00 = cos;
        double rm20 = sin;
        double rm02 = -sin;
        double rm22 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m20 * rm02;
        double nm01 = m01 * rm00 + m21 * rm02;
        double nm02 = m02 * rm00 + m22 * rm02;
        // set non-dependent values directly
        dest.m20(m00 * rm20 + m20 * rm22);
        dest.m21(m01 * rm20 + m21 * rm22);
        dest.m22(m02 * rm20 + m22 * rm22);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(m10);
        dest.m11(m11);
        dest.m12(m12);

        return dest;
    }

    public Matrix3dc rotateY(double ang) {
        return rotateY(ang, this);
    }

    public Matrix3dc rotateZ(double ang, Matrix3dc dest) {
        double sin, cos;
        sin = Math.sin(ang);
        cos = Math.cosFromSin(sin, ang);
        double rm00 = cos;
        double rm10 = -sin;
        double rm01 = sin;
        double rm11 = cos;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01;
        double nm01 = m01 * rm00 + m11 * rm01;
        double nm02 = m02 * rm00 + m12 * rm01;
        // set non-dependent values directly
        dest.m10(m00 * rm10 + m10 * rm11);
        dest.m11(m01 * rm10 + m11 * rm11);
        dest.m12(m02 * rm10 + m12 * rm11);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m20(m20);
        dest.m21(m21);
        dest.m22(m22);

        return dest;
    }

    public Matrix3dc rotateZ(double ang) {
        return rotateZ(ang, this);
    }

    public Matrix3dc rotateXYZ(double angleX, double angleY, double angleZ) {
        return rotateXYZ(angleX, angleY, angleZ, this);
    }

    public Matrix3dc rotateXYZ(double angleX, double angleY, double angleZ, Matrix3dc dest) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinX = -sinX;
        double m_sinY = -sinY;
        double m_sinZ = -sinZ;

        // rotateX
        double nm10 = m10 * cosX + m20 * sinX;
        double nm11 = m11 * cosX + m21 * sinX;
        double nm12 = m12 * cosX + m22 * sinX;
        double nm20 = m10 * m_sinX + m20 * cosX;
        double nm21 = m11 * m_sinX + m21 * cosX;
        double nm22 = m12 * m_sinX + m22 * cosX;
        // rotateY
        double nm00 = m00 * cosY + nm20 * m_sinY;
        double nm01 = m01 * cosY + nm21 * m_sinY;
        double nm02 = m02 * cosY + nm22 * m_sinY;
        dest.m20(m00 * sinY + nm20 * cosY);
        dest.m21(m01 * sinY + nm21 * cosY);
        dest.m22(m02 * sinY + nm22 * cosY);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        return dest;
    }

    public Matrix3dc rotateZYX(double angleZ, double angleY, double angleX) {
        return rotateZYX(angleZ, angleY, angleX, this);
    }

    public Matrix3dc rotateZYX(double angleZ, double angleY, double angleX, Matrix3dc dest) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinZ = -sinZ;
        double m_sinY = -sinY;
        double m_sinX = -sinX;

        // rotateZ
        double nm00 = m00 * cosZ + m10 * sinZ;
        double nm01 = m01 * cosZ + m11 * sinZ;
        double nm02 = m02 * cosZ + m12 * sinZ;
        double nm10 = m00 * m_sinZ + m10 * cosZ;
        double nm11 = m01 * m_sinZ + m11 * cosZ;
        double nm12 = m02 * m_sinZ + m12 * cosZ;
        // rotateY
        double nm20 = nm00 * sinY + m20 * cosY;
        double nm21 = nm01 * sinY + m21 * cosY;
        double nm22 = nm02 * sinY + m22 * cosY;
        dest.m00(nm00 * cosY + m20 * m_sinY);
        dest.m01(nm01 * cosY + m21 * m_sinY);
        dest.m02(nm02 * cosY + m22 * m_sinY);
        // rotateX
        dest.m10(nm10 * cosX + nm20 * sinX);
        dest.m11(nm11 * cosX + nm21 * sinX);
        dest.m12(nm12 * cosX + nm22 * sinX);
        dest.m20(nm10 * m_sinX + nm20 * cosX);
        dest.m21(nm11 * m_sinX + nm21 * cosX);
        dest.m22(nm12 * m_sinX + nm22 * cosX);
        return dest;
    }

    public Matrix3dc rotateYXZ(Vector3dc angles) {
        return rotateYXZ(angles.y(), angles.x(), angles.z());
    }

    public Matrix3dc rotateYXZ(double angleY, double angleX, double angleZ) {
        return rotateYXZ(angleY, angleX, angleZ, this);
    }

    public Matrix3dc rotateYXZ(double angleY, double angleX, double angleZ, Matrix3dc dest) {
        double sinX = Math.sin(angleX);
        double cosX = Math.cosFromSin(sinX, angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cosFromSin(sinY, angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cosFromSin(sinZ, angleZ);
        double m_sinY = -sinY;
        double m_sinX = -sinX;
        double m_sinZ = -sinZ;

        // rotateY
        double nm20 = m00 * sinY + m20 * cosY;
        double nm21 = m01 * sinY + m21 * cosY;
        double nm22 = m02 * sinY + m22 * cosY;
        double nm00 = m00 * cosY + m20 * m_sinY;
        double nm01 = m01 * cosY + m21 * m_sinY;
        double nm02 = m02 * cosY + m22 * m_sinY;
        // rotateX
        double nm10 = m10 * cosX + nm20 * sinX;
        double nm11 = m11 * cosX + nm21 * sinX;
        double nm12 = m12 * cosX + nm22 * sinX;
        dest.m20(m10 * m_sinX + nm20 * cosX);
        dest.m21(m11 * m_sinX + nm21 * cosX);
        dest.m22(m12 * m_sinX + nm22 * cosX);
        // rotateZ
        dest.m00(nm00 * cosZ + nm10 * sinZ);
        dest.m01(nm01 * cosZ + nm11 * sinZ);
        dest.m02(nm02 * cosZ + nm12 * sinZ);
        dest.m10(nm00 * m_sinZ + nm10 * cosZ);
        dest.m11(nm01 * m_sinZ + nm11 * cosZ);
        dest.m12(nm02 * m_sinZ + nm12 * cosZ);
        return dest;
    }

    public Matrix3dc rotate(double ang, double x, double y, double z) {
        return rotate(ang, x, y, z, this);
    }

    public Matrix3dc rotate(double ang, double x, double y, double z, Matrix3dc dest) {
        double s = Math.sin(ang);
        double c = Math.cosFromSin(s, ang);
        double C = 1.0 - c;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double rm00 = xx * C + c;
        double rm01 = xy * C + z * s;
        double rm02 = xz * C - y * s;
        double rm10 = xy * C - z * s;
        double rm11 = yy * C + c;
        double rm12 = yz * C + x * s;
        double rm20 = xz * C + y * s;
        double rm21 = yz * C - x * s;
        double rm22 = zz * C + c;

        // add temporaries for dependent values
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        // set other values
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);

        return dest;
    }

    public Matrix3dc rotateLocal(double ang, double x, double y, double z, Matrix3dc dest) {
        double s = Math.sin(ang);
        double c = Math.cosFromSin(s, ang);
        double C = 1.0 - c;
        double xx = x * x, xy = x * y, xz = x * z;
        double yy = y * y, yz = y * z;
        double zz = z * z;
        double lm00 = xx * C + c;
        double lm01 = xy * C + z * s;
        double lm02 = xz * C - y * s;
        double lm10 = xy * C - z * s;
        double lm11 = yy * C + c;
        double lm12 = yz * C + x * s;
        double lm20 = xz * C + y * s;
        double lm21 = yz * C - x * s;
        double lm22 = zz * C + c;
        double nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        double nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        double nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc rotateLocal(double ang, double x, double y, double z) {
        return rotateLocal(ang, x, y, z, this);
    }

    public Matrix3dc rotateLocalX(double ang, Matrix3dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm01 = cos * m01 - sin * m02;
        double nm02 = sin * m01 + cos * m02;
        double nm11 = cos * m11 - sin * m12;
        double nm12 = sin * m11 + cos * m12;
        double nm21 = cos * m21 - sin * m22;
        double nm22 = sin * m21 + cos * m22;
        dest.m00(m00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(m10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(m20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc rotateLocalX(double ang) {
        return rotateLocalX(ang, this);
    }

    public Matrix3dc rotateLocalY(double ang, Matrix3dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm00 = cos * m00 + sin * m02;
        double nm02 = -sin * m00 + cos * m02;
        double nm10 = cos * m10 + sin * m12;
        double nm12 = -sin * m10 + cos * m12;
        double nm20 = cos * m20 + sin * m22;
        double nm22 = -sin * m20 + cos * m22;
        dest.m00(nm00);
        dest.m01(m01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(m11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(m21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc rotateLocalY(double ang) {
        return rotateLocalY(ang, this);
    }

    public Matrix3dc rotateLocalZ(double ang, Matrix3dc dest) {
        double sin = Math.sin(ang);
        double cos = Math.cosFromSin(sin, ang);
        double nm00 = cos * m00 - sin * m01;
        double nm01 = sin * m00 + cos * m01;
        double nm10 = cos * m10 - sin * m11;
        double nm11 = sin * m10 + cos * m11;
        double nm20 = cos * m20 - sin * m21;
        double nm21 = sin * m20 + cos * m21;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(m02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(m12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(m22);
        return dest;
    }

    public Matrix3dc rotateLocalZ(double ang) {
        return rotateLocalZ(ang, this);
    }

    public Matrix3dc rotateLocal(IQuaterniond quat, Matrix3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double lm00 = w2 + x2 - z2 - y2;
        double lm01 = xy + zw + zw + xy;
        double lm02 = xz - yw + xz - yw;
        double lm10 = -zw + xy - zw + xy;
        double lm11 = y2 - z2 + w2 - x2;
        double lm12 = yz + yz + xw + xw;
        double lm20 = yw + xz + xz + yw;
        double lm21 = yz + yz - xw - xw;
        double lm22 = z2 - y2 - x2 + w2;
        double nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        double nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        double nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc rotateLocal(IQuaterniond quat) {
        return rotateLocal(quat, this);
    }

    public Matrix3dc rotateLocal(IQuaternionf quat, Matrix3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double lm00 = w2 + x2 - z2 - y2;
        double lm01 = xy + zw + zw + xy;
        double lm02 = xz - yw + xz - yw;
        double lm10 = -zw + xy - zw + xy;
        double lm11 = y2 - z2 + w2 - x2;
        double lm12 = yz + yz + xw + xw;
        double lm20 = yw + xz + xz + yw;
        double lm21 = yz + yz - xw - xw;
        double lm22 = z2 - y2 - x2 + w2;
        double nm00 = lm00 * m00 + lm10 * m01 + lm20 * m02;
        double nm01 = lm01 * m00 + lm11 * m01 + lm21 * m02;
        double nm02 = lm02 * m00 + lm12 * m01 + lm22 * m02;
        double nm10 = lm00 * m10 + lm10 * m11 + lm20 * m12;
        double nm11 = lm01 * m10 + lm11 * m11 + lm21 * m12;
        double nm12 = lm02 * m10 + lm12 * m11 + lm22 * m12;
        double nm20 = lm00 * m20 + lm10 * m21 + lm20 * m22;
        double nm21 = lm01 * m20 + lm11 * m21 + lm21 * m22;
        double nm22 = lm02 * m20 + lm12 * m21 + lm22 * m22;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc rotateLocal(IQuaternionf quat) {
        return rotateLocal(quat, this);
    }

    public Matrix3dc rotate(IQuaterniond quat) {
        return rotate(quat, this);
    }

    public Matrix3dc rotate(IQuaterniond quat, Matrix3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double rm00 = w2 + x2 - z2 - y2;
        double rm01 = xy + zw + zw + xy;
        double rm02 = xz - yw + xz - yw;
        double rm10 = -zw + xy - zw + xy;
        double rm11 = y2 - z2 + w2 - x2;
        double rm12 = yz + yz + xw + xw;
        double rm20 = yw + xz + xz + yw;
        double rm21 = yz + yz - xw - xw;
        double rm22 = z2 - y2 - x2 + w2;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        return dest;
    }

    public Matrix3dc rotate(IQuaternionf quat) {
        return rotate(quat, this);
    }

    public Matrix3dc rotate(IQuaternionf quat, Matrix3dc dest) {
        double w2 = quat.w() * quat.w();
        double x2 = quat.x() * quat.x();
        double y2 = quat.y() * quat.y();
        double z2 = quat.z() * quat.z();
        double zw = quat.z() * quat.w();
        double xy = quat.x() * quat.y();
        double xz = quat.x() * quat.z();
        double yw = quat.y() * quat.w();
        double yz = quat.y() * quat.z();
        double xw = quat.x() * quat.w();
        double rm00 = w2 + x2 - z2 - y2;
        double rm01 = xy + zw + zw + xy;
        double rm02 = xz - yw + xz - yw;
        double rm10 = -zw + xy - zw + xy;
        double rm11 = y2 - z2 + w2 - x2;
        double rm12 = yz + yz + xw + xw;
        double rm20 = yw + xz + xz + yw;
        double rm21 = yz + yz - xw - xw;
        double rm22 = z2 - y2 - x2 + w2;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        return dest;
    }

    public Matrix3dc rotate(AxisAngle4fc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix3dc rotate(AxisAngle4fc axisAngle, Matrix3dc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix3dc rotate(AxisAngle4dc axisAngle) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z());
    }

    public Matrix3dc rotate(AxisAngle4dc axisAngle, Matrix3dc dest) {
        return rotate(axisAngle.angle(), axisAngle.x(), axisAngle.y(), axisAngle.z(), dest);
    }

    public Matrix3dc rotate(double angle, IVector3d axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix3dc rotate(double angle, IVector3d axis, Matrix3dc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Matrix3dc rotate(double angle, IVector3f axis) {
        return rotate(angle, axis.x(), axis.y(), axis.z());
    }

    public Matrix3dc rotate(double angle, IVector3f axis, Matrix3dc dest) {
        return rotate(angle, axis.x(), axis.y(), axis.z(), dest);
    }

    public Vector3dc getRow(int row, Vector3dc dest) throws IndexOutOfBoundsException {
        switch (row) {
            case 0:
                dest.set(m00, m10, m20);
                break;
            case 1:
                dest.set(m01, m11, m21);
                break;
            case 2:
                dest.set(m02, m12, m22);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    public Matrix3dc setRow(int row, IVector3d src) throws IndexOutOfBoundsException {
        return setRow(row, src.x(), src.y(), src.z());
    }

    public Matrix3dc setRow(int row, double x, double y, double z) throws IndexOutOfBoundsException {
        switch (row) {
            case 0:
                this.m00 = x;
                this.m01 = y;
                this.m02 = z;
                break;
            case 1:
                this.m10 = x;
                this.m11 = y;
                this.m12 = z;
                break;
            case 2:
                this.m20 = x;
                this.m21 = y;
                this.m22 = z;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public Vector3dc getColumn(int column, Vector3dc dest) throws IndexOutOfBoundsException {
        switch (column) {
            case 0:
                dest.set(m00, m01, m02);
                break;
            case 1:
                dest.set(m10, m11, m12);
                break;
            case 2:
                dest.set(m20, m21, m22);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return dest;
    }

    public Matrix3dc setColumn(int column, IVector3d src) throws IndexOutOfBoundsException {
        return setColumn(column, src.x(), src.y(), src.z());
    }

    public Matrix3dc setColumn(int column, double x, double y, double z) throws IndexOutOfBoundsException {
        switch (column) {
            case 0:
                this.m00 = x;
                this.m01 = y;
                this.m02 = z;
                break;
            case 1:
                this.m10 = x;
                this.m11 = y;
                this.m12 = z;
                break;
            case 2:
                this.m20 = x;
                this.m21 = y;
                this.m22 = z;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public double get(int column, int row) {
        switch (column) {
            case 0:
                switch (row) {
                    case 0:
                        return m00;
                    case 1:
                        return m01;
                    case 2:
                        return m02;
                    default:
                        break;
                }
                break;
            case 1:
                switch (row) {
                    case 0:
                        return m10;
                    case 1:
                        return m11;
                    case 2:
                        return m12;
                    default:
                        break;
                }
                break;
            case 2:
                switch (row) {
                    case 0:
                        return m20;
                    case 1:
                        return m21;
                    case 2:
                        return m22;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        throw new IllegalArgumentException();
    }

    public Matrix3dc set(int column, int row, double value) {
        switch (column) {
            case 0:
                switch (row) {
                    case 0:
                        this.m00 = value;
                        return this;
                    case 1:
                        this.m01 = value;
                        return this;
                    case 2:
                        this.m02 = value;
                        return this;
                    default:
                        break;
                }
                break;
            case 1:
                switch (row) {
                    case 0:
                        this.m10 = value;
                        return this;
                    case 1:
                        this.m11 = value;
                        return this;
                    case 2:
                        this.m12 = value;
                        return this;
                    default:
                        break;
                }
                break;
            case 2:
                switch (row) {
                    case 0:
                        this.m20 = value;
                        return this;
                    case 1:
                        this.m21 = value;
                        return this;
                    case 2:
                        this.m22 = value;
                        return this;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        throw new IllegalArgumentException();
    }

    public Matrix3dc normal() {
        return normal(this);
    }

    public Matrix3dc normal(Matrix3dc dest) {
        double m00m11 = m00 * m11;
        double m01m10 = m01 * m10;
        double m02m10 = m02 * m10;
        double m00m12 = m00 * m12;
        double m01m12 = m01 * m12;
        double m02m11 = m02 * m11;
        double det = (m00m11 - m01m10) * m22 + (m02m10 - m00m12) * m21 + (m01m12 - m02m11) * m20;
        double s = 1.0 / det;
        /* Invert and transpose in one go */
        double nm00 = (m11 * m22 - m21 * m12) * s;
        double nm01 = (m20 * m12 - m10 * m22) * s;
        double nm02 = (m10 * m21 - m20 * m11) * s;
        double nm10 = (m21 * m02 - m01 * m22) * s;
        double nm11 = (m00 * m22 - m20 * m02) * s;
        double nm12 = (m20 * m01 - m00 * m21) * s;
        double nm20 = (m01m12 - m02m11) * s;
        double nm21 = (m02m10 - m00m12) * s;
        double nm22 = (m00m11 - m01m10) * s;
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        dest.m20(nm20);
        dest.m21(nm21);
        dest.m22(nm22);
        return dest;
    }

    public Matrix3dc lookAlong(IVector3d dir, IVector3d up) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix3dc lookAlong(IVector3d dir, IVector3d up, Matrix3dc dest) {
        return lookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix3dc lookAlong(double dirX, double dirY, double dirZ,
                               double upX, double upY, double upZ, Matrix3dc dest) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = -dirX * invDirLength;
        double dirnY = -dirY * invDirLength;
        double dirnZ = -dirZ * invDirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double invRightLength = 1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        double upnX = rightY * dirnZ - rightZ * dirnY;
        double upnY = rightZ * dirnX - rightX * dirnZ;
        double upnZ = rightX * dirnY - rightY * dirnX;

        // calculate right matrix elements
        double rm00 = rightX;
        double rm01 = upnX;
        double rm02 = -dirnX;
        double rm10 = rightY;
        double rm11 = upnY;
        double rm12 = -dirnY;
        double rm20 = rightZ;
        double rm21 = upnZ;
        double rm22 = -dirnZ;

        // perform optimized matrix multiplication
        // introduce temporaries for dependent results
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        // set the rest of the matrix elements
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);

        return dest;
    }

    public Matrix3dc lookAlong(double dirX, double dirY, double dirZ,
                               double upX, double upY, double upZ) {
        return lookAlong(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix3dc setLookAlong(IVector3d dir, IVector3d up) {
        return setLookAlong(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix3dc setLookAlong(double dirX, double dirY, double dirZ,
                                  double upX, double upY, double upZ) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double dirnX = -dirX * invDirLength;
        double dirnY = -dirY * invDirLength;
        double dirnZ = -dirZ * invDirLength;
        // right = direction x up
        double rightX, rightY, rightZ;
        rightX = dirnY * upZ - dirnZ * upY;
        rightY = dirnZ * upX - dirnX * upZ;
        rightZ = dirnX * upY - dirnY * upX;
        // normalize right
        double invRightLength = 1.0 / Math.sqrt(rightX * rightX + rightY * rightY + rightZ * rightZ);
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;
        // up = right x direction
        double upnX = rightY * dirnZ - rightZ * dirnY;
        double upnY = rightZ * dirnX - rightX * dirnZ;
        double upnZ = rightX * dirnY - rightY * dirnX;

        m00 = rightX;
        m01 = upnX;
        m02 = -dirnX;
        m10 = rightY;
        m11 = upnY;
        m12 = -dirnY;
        m20 = rightZ;
        m21 = upnZ;
        m22 = -dirnZ;

        return this;
    }

    public Vector3dc getScale(Vector3dc dest) {
        dest.set(Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02), Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12), Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22));
        return dest;
    }

    public Vector3dc positiveZ(Vector3dc dir) {
        dir.set(m10 * m21 - m11 * m20, m20 * m01 - m21 * m00, m00 * m11 - m01 * m10);
        dir.normalize();
        return dir;
    }

    public Vector3dc normalizedPositiveZ(Vector3dc dir) {
        dir.set(m02, m12, m22);
        return dir;
    }

    public Vector3dc positiveX(Vector3dc dir) {
        dir.set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11);
        dir.normalize();
        return dir;
    }

    public Vector3dc normalizedPositiveX(Vector3dc dir) {
        dir.set(m00, m10, m20);
        return dir;
    }

    public Vector3dc positiveY(Vector3dc dir) {
        dir.set(m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12);
        dir.normalize();
        return dir;
    }

    public Vector3dc normalizedPositiveY(Vector3dc dir) {
        dir.set(m01, m11, m21);
        return dir;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(m00);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m01);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m02);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m10);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m11);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m20);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix3dc))
            return false;
        Matrix3dc other = (Matrix3dc) obj;
        if (Double.doubleToLongBits(m00) != Double.doubleToLongBits(other.m00()))
            return false;
        if (Double.doubleToLongBits(m01) != Double.doubleToLongBits(other.m01()))
            return false;
        if (Double.doubleToLongBits(m02) != Double.doubleToLongBits(other.m02()))
            return false;
        if (Double.doubleToLongBits(m10) != Double.doubleToLongBits(other.m10()))
            return false;
        if (Double.doubleToLongBits(m11) != Double.doubleToLongBits(other.m11()))
            return false;
        if (Double.doubleToLongBits(m12) != Double.doubleToLongBits(other.m12()))
            return false;
        if (Double.doubleToLongBits(m20) != Double.doubleToLongBits(other.m20()))
            return false;
        if (Double.doubleToLongBits(m21) != Double.doubleToLongBits(other.m21()))
            return false;
        if (Double.doubleToLongBits(m22) != Double.doubleToLongBits(other.m22()))
            return false;
        return true;
    }

    public Matrix3dc swap(Matrix3dc other) {
        double tmp;
        tmp = m00;
        m00 = other.m00();
        other.m00(tmp);
        tmp = m01;
        m01 = other.m01();
        other.m01(tmp);
        tmp = m02;
        m02 = other.m02();
        other.m02(tmp);
        tmp = m10;
        m10 = other.m10();
        other.m10(tmp);
        tmp = m11;
        m11 = other.m11();
        other.m11(tmp);
        tmp = m12;
        m12 = other.m12();
        other.m12(tmp);
        tmp = m20;
        m20 = other.m20();
        other.m20(tmp);
        tmp = m21;
        m21 = other.m21();
        other.m21(tmp);
        tmp = m22;
        m22 = other.m22();
        other.m22(tmp);
        return this;
    }

    public Matrix3dc add(IMatrix3d other) {
        return add(other, this);
    }

    public Matrix3dc add(IMatrix3d other, Matrix3dc dest) {
        dest.m00(m00 + other.m00());
        dest.m01(m01 + other.m01());
        dest.m02(m02 + other.m02());
        dest.m10(m10 + other.m10());
        dest.m11(m11 + other.m11());
        dest.m12(m12 + other.m12());
        dest.m20(m20 + other.m20());
        dest.m21(m21 + other.m21());
        dest.m22(m22 + other.m22());
        return dest;
    }

    public Matrix3dc sub(IMatrix3d subtrahend) {
        return sub(subtrahend, this);
    }

    public Matrix3dc sub(IMatrix3d subtrahend, Matrix3dc dest) {
        dest.m00(m00 - subtrahend.m00());
        dest.m01(m01 - subtrahend.m01());
        dest.m02(m02 - subtrahend.m02());
        dest.m10(m10 - subtrahend.m10());
        dest.m11(m11 - subtrahend.m11());
        dest.m12(m12 - subtrahend.m12());
        dest.m20(m20 - subtrahend.m20());
        dest.m21(m21 - subtrahend.m21());
        dest.m22(m22 - subtrahend.m22());
        return dest;
    }

    public Matrix3dc mulComponentWise(IMatrix3d other) {
        return mulComponentWise(other, this);
    }

    public Matrix3dc mulComponentWise(IMatrix3d other, Matrix3dc dest) {
        dest.m00(m00 * other.m00());
        dest.m01(m01 * other.m01());
        dest.m02(m02 * other.m02());
        dest.m10(m10 * other.m10());
        dest.m11(m11 * other.m11());
        dest.m12(m12 * other.m12());
        dest.m20(m20 * other.m20());
        dest.m21(m21 * other.m21());
        dest.m22(m22 * other.m22());
        return dest;
    }

    public Matrix3dc setSkewSymmetric(double a, double b, double c) {
        m00 = m11 = m22 = 0;
        m01 = -a;
        m02 = b;
        m10 = a;
        m12 = -c;
        m20 = -b;
        m21 = c;
        return this;
    }

    public Matrix3dc lerp(IMatrix3d other, double t) {
        return lerp(other, t, this);
    }

    public Matrix3dc lerp(IMatrix3d other, double t, Matrix3dc dest) {
        dest.m00(m00 + (other.m00() - m00) * t);
        dest.m01(m01 + (other.m01() - m01) * t);
        dest.m02(m02 + (other.m02() - m02) * t);
        dest.m10(m10 + (other.m10() - m10) * t);
        dest.m11(m11 + (other.m11() - m11) * t);
        dest.m12(m12 + (other.m12() - m12) * t);
        dest.m20(m20 + (other.m20() - m20) * t);
        dest.m21(m21 + (other.m21() - m21) * t);
        dest.m22(m22 + (other.m22() - m22) * t);
        return dest;
    }

    public Matrix3dc rotateTowards(IVector3d direction, IVector3d up, Matrix3dc dest) {
        return rotateTowards(direction.x(), direction.y(), direction.z(), up.x(), up.y(), up.z(), dest);
    }

    public Matrix3dc rotateTowards(IVector3d direction, IVector3d up) {
        return rotateTowards(direction.x(), direction.y(), direction.z(), up.x(), up.y(), up.z(), this);
    }

    public Matrix3dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        return rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, this);
    }

    public Matrix3dc rotateTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ, Matrix3dc dest) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double ndirX = dirX * invDirLength;
        double ndirY = dirY * invDirLength;
        double ndirZ = dirZ * invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = ndirY * leftZ - ndirZ * leftY;
        double upnY = ndirZ * leftX - ndirX * leftZ;
        double upnZ = ndirX * leftY - ndirY * leftX;
        double rm00 = leftX;
        double rm01 = leftY;
        double rm02 = leftZ;
        double rm10 = upnX;
        double rm11 = upnY;
        double rm12 = upnZ;
        double rm20 = ndirX;
        double rm21 = ndirY;
        double rm22 = ndirZ;
        double nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        double nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        double nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        double nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        double nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        double nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        dest.m20(m00 * rm20 + m10 * rm21 + m20 * rm22);
        dest.m21(m01 * rm20 + m11 * rm21 + m21 * rm22);
        dest.m22(m02 * rm20 + m12 * rm21 + m22 * rm22);
        dest.m00(nm00);
        dest.m01(nm01);
        dest.m02(nm02);
        dest.m10(nm10);
        dest.m11(nm11);
        dest.m12(nm12);
        return dest;
    }

    public Matrix3dc rotationTowards(IVector3d dir, IVector3d up) {
        return rotationTowards(dir.x(), dir.y(), dir.z(), up.x(), up.y(), up.z());
    }

    public Matrix3dc rotationTowards(double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
        // Normalize direction
        double invDirLength = 1.0 / Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        double ndirX = dirX * invDirLength;
        double ndirY = dirY * invDirLength;
        double ndirZ = dirZ * invDirLength;
        // left = up x direction
        double leftX, leftY, leftZ;
        leftX = upY * ndirZ - upZ * ndirY;
        leftY = upZ * ndirX - upX * ndirZ;
        leftZ = upX * ndirY - upY * ndirX;
        // normalize left
        double invLeftLength = 1.0 / Math.sqrt(leftX * leftX + leftY * leftY + leftZ * leftZ);
        leftX *= invLeftLength;
        leftY *= invLeftLength;
        leftZ *= invLeftLength;
        // up = direction x left
        double upnX = ndirY * leftZ - ndirZ * leftY;
        double upnY = ndirZ * leftX - ndirX * leftZ;
        double upnZ = ndirX * leftY - ndirY * leftX;
        this.m00 = leftX;
        this.m01 = leftY;
        this.m02 = leftZ;
        this.m10 = upnX;
        this.m11 = upnY;
        this.m12 = upnZ;
        this.m20 = ndirX;
        this.m21 = ndirY;
        this.m22 = ndirZ;
        return this;
    }

    public Vector3dc getEulerAnglesZYX(Vector3dc dest) {
        dest.set((float) Math.atan2(m12, m22), (float) Math.atan2(-m02, Math.sqrt(m12 * m12 + m22 * m22)), (float) Math.atan2(m01, m00));
        return dest;
    }
}
