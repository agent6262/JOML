package org.joml.test;

import org.joml.Matrix3x2f;
import org.joml.Vector2f;

import junit.framework.TestCase;
import org.joml.api.matrix.Matrix3x2fc;

/**
 * Tests for the {@link Matrix3x2f} class.
 * 
 * @author Kai Burjack
 */
public class Matrix3x2fTest extends TestCase {

    public static void testInvert() {
        Matrix3x2f m = new Matrix3x2f(1, 2, 4, 5, -0.5f, -2f);
        Vector2f v = new Vector2f(4, 0.5f);
        m.transformPosition(v);
        Vector2f v2 = new Vector2f(v);
        m.invert();
        m.transformPosition(v2);
        TestUtil.assertVector2fEquals(new Vector2f(4, 0.5f), v2, 1E-6f);
    }

    public static void testView() {
        Matrix3x2fc m = new Matrix3x2f().view(-4, 0.5f, -2, 3);
        Vector2f v = new Vector2f(-4, -2);
        m.transformPosition(v);
        TestUtil.assertVector2fEquals(new Vector2f(-1, -1), v, 0f);
        v.set(0.5f, 3);
        m.transformPosition(v);
        TestUtil.assertVector2fEquals(new Vector2f(1, 1), v, 0f);
    }

    public static void testUnproject() {
        Matrix3x2fc m = new Matrix3x2f().view(-3, 2, -4, 1);
        TestUtil.assertVector2fEquals(new Vector2f(-3, -4), m.unproject(0, 0, new int[] {0, 0, 800, 600}, new Vector2f()), 1E-6f);
        TestUtil.assertVector2fEquals(new Vector2f(2, 1), m.unproject(800, 600, new int[] {0, 0, 800, 600}, new Vector2f()), 1E-6f);
    }

    public static void testTestPoint() {
        Matrix3x2fc m = new Matrix3x2f().view(-4, 2, -3, 10);
        assertTrue(m.testPoint(0, 0));
        assertTrue(m.testPoint(-4, -2.9f));
        assertFalse(m.testPoint(-4.01f, -2.9f));
        assertFalse(m.testPoint(-3.9f, -3.01f));
        assertTrue(m.testPoint(0, 9.99f));
        assertFalse(m.testPoint(0, 10.01f));

        // rotated
        m.setView(-2, 2, -2, 2).rotate((float)Math.toRadians(45));
        float[] area = m.viewArea(new float[4]);
        assertTrue(m.testPoint(area[0], 0));
        assertFalse(m.testPoint(area[0]-0.01f, 0));
        assertTrue(m.testPoint(area[2]-0.1f, 0));
        assertFalse(m.testPoint(area[2]+0.01f, 0));
    }

}
