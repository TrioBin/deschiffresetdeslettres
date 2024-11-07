package fr.crusche.beziermanagement;

import java.util.ArrayList;

public class BezierCurve {
    private ArrayList<Point> points;
    private int scale;
    private ArrayList<Point> calculatePointsCache;

    public BezierCurve(ArrayList<Point> points, int scale) {
        this.scale = scale;

        ArrayList<Point> tmpPoints = new ArrayList<Point>();
        for (Point point : points) {
            tmpPoints.add(new Point(point.getX() / scale, point.getY() / scale));
        }

        this.points = tmpPoints;
    }

    public int getScale() {
        return this.scale;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public ArrayList<Point> getLastCalculatedCache() {
        return this.calculatePointsCache;
    }

    public ArrayList<Point> calculatePoints(int steps) {
        calculatePointsCache = new ArrayList<Point>();
        for (int i = 0; i < steps; i++) {
            double t = (double) i / steps;
            double x = points.get(0).getX() * Math.pow(1 - t, 3) + 3 * points.get(1).getX() * t * Math.pow(1 - t, 2)
                    + 3 * points.get(2).getX() * Math.pow(t, 2) * (1 - t) + points.get(3).getX() * Math.pow(t, 3);
            double y = points.get(0).getY() * Math.pow(1 - t, 3) + 3 * points.get(1).getY() * t * Math.pow(1 - t, 2)
                    + 3 * points.get(2).getY() * Math.pow(t, 2) * (1 - t) + points.get(3).getY() * Math.pow(t, 3);
            calculatePointsCache.add(new Point(x, y));
        }
        return calculatePointsCache;
    }

    public double getPointWithX(float x) {
        // encadrer x dans calculatePointsCache
        int i = 0;
        while (i < calculatePointsCache.size() && calculatePointsCache.get(i).getX() < x) {
            i++;
        }

        if (i == 0) {
            return calculatePointsCache.get(0).getY();
        }

        double y = calculatePointsCache.get(i - 1).getY()
                + (calculatePointsCache.get(i).getY() - calculatePointsCache.get(i - 1).getY())
                        * (x - calculatePointsCache.get(i - 1).getX())
                        / (calculatePointsCache.get(i).getX() - calculatePointsCache.get(i - 1).getX());

        return y;
    }
}
