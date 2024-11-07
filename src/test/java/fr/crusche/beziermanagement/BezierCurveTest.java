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
}