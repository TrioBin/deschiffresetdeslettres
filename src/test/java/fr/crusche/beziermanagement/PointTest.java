package fr.crusche.beziermanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



public class PointTest {

    @Test
    public void testConstructor() {
        Point point = new Point(3.0, 4.0);
        assertEquals(3.0, point.getX());
        assertEquals(4.0, point.getY());
    }

    @Test
    public void testSetX() {
        Point point = new Point(0.0, 0.0);
        point.setX(5.0);
        assertEquals(5.0, point.getX());
    }

    @Test
    public void testSetY() {
        Point point = new Point(0.0, 0.0);
        point.setY(6.0);
        assertEquals(6.0, point.getY());
    }

    @Test
    public void testGetX() {
        Point point = new Point(7.0, 8.0);
        assertEquals(7.0, point.getX());
    }

    @Test
    public void testGetY() {
        Point point = new Point(7.0, 8.0);
        assertEquals(8.0, point.getY());
    }
}