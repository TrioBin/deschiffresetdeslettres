package fr.crusche.beziermanagement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BezierCurveTest {
    @Test
    public void testConstructor() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(10, 20));
        points.add(new Point(30, 40));
        points.add(new Point(50, 60));
        points.add(new Point(70, 80));

        int scale = 10;
        BezierCurve bezierCurve = new BezierCurve(points, scale);

        ArrayList<Point> expectedPoints = new ArrayList<>();
        expectedPoints.add(new Point(1, 2));
        expectedPoints.add(new Point(3, 4));
        expectedPoints.add(new Point(5, 6));
        expectedPoints.add(new Point(7, 8));

        assertEquals(scale, bezierCurve.getScale());
        assertEquals(expectedPoints.size(), bezierCurve.getPoints().size());

        for (int i = 0; i < expectedPoints.size(); i++) {
            assertEquals(expectedPoints.get(i).getX(), bezierCurve.getPoints().get(i).getX());
            assertEquals(expectedPoints.get(i).getY(), bezierCurve.getPoints().get(i).getY());
        }
    }

    @Test
    public void testCalculatePoints() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 2));
        points.add(new Point(3, 3));
        points.add(new Point(4, 0));

        int scale = 1;
        BezierCurve bezierCurve = new BezierCurve(points, scale);

        ArrayList<Point> calculatedPoints = bezierCurve.calculatePoints(4);

        assertEquals(4, calculatedPoints.size());

        assertEquals(0, calculatedPoints.get(0).getX(), 0.3);
        assertEquals(0, calculatedPoints.get(0).getY(), 0.3);

        assertEquals(1, calculatedPoints.get(1).getX(), 0.1);
        assertEquals(1.5, calculatedPoints.get(1).getY(), 0.3);

        assertEquals(2, calculatedPoints.get(2).getX(), 0.3);
        assertEquals(2, calculatedPoints.get(2).getY(), 0.3);

        assertEquals(3, calculatedPoints.get(3).getX(), 0.3);
        assertEquals(1.5, calculatedPoints.get(3).getY(), 0.3);
    }

    @Test
    public void testGetLastCalculatedCache() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 2));
        points.add(new Point(3, 3));
        points.add(new Point(4, 0));

        int scale = 1;
        BezierCurve bezierCurve = new BezierCurve(points, scale);

        // Initially, the cache should be null
        assertNull(bezierCurve.getLastCalculatedCache());

        // Calculate points and check the cache
        bezierCurve.calculatePoints(4);
        ArrayList<Point> calculatedPoints = bezierCurve.getLastCalculatedCache();

        assertNotNull(calculatedPoints);
        assertEquals(4, calculatedPoints.size());

        assertEquals(0, calculatedPoints.get(0).getX(), 0.001);
        assertEquals(0, calculatedPoints.get(0).getY(), 0.001);

        assertEquals(1, calculatedPoints.get(1).getX(), 0.3);
        assertEquals(1.5, calculatedPoints.get(1).getY(), 0.3);

        assertEquals(2, calculatedPoints.get(2).getX(), 0.3);
        assertEquals(2, calculatedPoints.get(2).getY(), 0.3);

        assertEquals(3, calculatedPoints.get(3).getX(), 0.3);
        assertEquals(1.5, calculatedPoints.get(3).getY(), 0.3);
    }

    @Test
    public void testGetPointWithX() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 2));
        points.add(new Point(3, 3));
        points.add(new Point(4, 0));

        int scale = 1;
        BezierCurve bezierCurve = new BezierCurve(points, scale);
        bezierCurve.calculatePoints(4);

        assertEquals(0, bezierCurve.getPointWithX(0), 0.001);
        assertEquals(1.5, bezierCurve.getPointWithX(1), 0.3);
        assertEquals(2, bezierCurve.getPointWithX(2), 0.3);
        assertEquals(1.5, bezierCurve.getPointWithX(3), 0.3);
    }
}