package aoc;

import java.util.*;

public class Day9RedSquareCalculator {
    public long areaOfLargestRedRectangle(String redTilesList) {
        return areaOfLargestRedRectangle(redTilesList.lines().map(Vec2::parse).toList(), false);
    }

    public long areaOfLargestInteriorRectangle(String redTilesList) {
        return areaOfLargestRedRectangle(redTilesList.lines().map(Vec2::parse).toList(), true);
    }

    long areaOfLargestRedRectangle(List<Vec2> reds, boolean rectInPip) {
        long area = 0;
        // int[] areaIJ = new int[]{0, 0};

        Map<Integer, Boolean> cache = rectInPip ? new HashMap<>() : null;

        for (int i = 0; i < reds.size(); i++) {
            var iElem = reds.get(i);
            for (int j = 0; j < reds.size(); j++) {
                if (i == j)
                    continue;
                var jElem = reds.get(j);

                if (rectInPip && !rectAllInPip(cache, reds, iElem, jElem))
                    continue;

                var ijArea = iElem.rectArea(jElem);
                if (ijArea > area) {
                    area = ijArea;
                    // areaIJ[0] = i;
                    // areaIJ[1] = j;
                }
            }
        }

        return area;
    }

    boolean rectAllInPip(Map<Integer, Boolean> cache, List<Vec2> polygon, Vec2 iElem, Vec2 jElem) {
        int xMin = Math.min(iElem.x(), jElem.x());
        int xMax = Math.max(iElem.x(), jElem.x());
        int yMin = Math.min(iElem.y(), jElem.y());
        int yMax = Math.max(iElem.y(), jElem.y());

        // cache here
        var key = Objects.hash(xMin, xMax, yMin, yMax);
        var cached = cache.get(key);
        if (cached != null)
            return cached;

        Vec2 bl = new Vec2(xMin, yMin), br = new Vec2(xMax, yMin), tl = new Vec2(xMin, yMax), tr = new Vec2(xMax, yMax);

        boolean ok =
                pip(polygon, bl) &&
                        pip(polygon, tl) &&
                        pip(polygon, br) &&
                        pip(polygon, tr);

        if (!ok) {
            cache.put(key, false);
            return false;
        }

        if (Geometry.rectangleEdgeCrossesPolygon(polygon, bl, br, tr, tl)) {
            cache.put(key, false);
            return false;
        }

        cache.put(key, true);
        return true;
    }

    private boolean pip(List<Vec2> polygon, Vec2 p) {
        return Geometry.insideOrOnEdge(polygon, p);
    }

    record Vec2(int x, int y) {
        Vec2(int[] xy) {
            this(xy[0], xy[1]);
        }

        static Vec2 parse(String vec2) {
            return new Vec2(Arrays.stream(vec2.split(",")).mapToInt(Integer::parseInt).toArray());
        }

        long rectArea(Vec2 jElem) {
            long i = (Math.abs(x - jElem.x) + 1) * (long) (Math.abs(y - jElem.y) + 1);
            // System.out.println("area is " + i + " for this " + this + " and other " + jElem);
            return i;
        }
    }

    // ai code
    static class Geometry {
        static boolean insideOrOnEdge(List<Vec2> polygon, Vec2 p) {
            int n = polygon.size();
            boolean inside = false;

            for (int i = 0, j = n - 1; i < n; j = i++) {
                Vec2 a = polygon.get(j);
                Vec2 b = polygon.get(i);

                // 1) Explicit edge check: point lies exactly on segment a-b
                if (pointOnSegment(a, b, p)) {
                    return true;
                }

                // 2) Ray-casting toggle
                boolean intersects =
                        ((a.y() > p.y()) != (b.y() > p.y())) &&
                                (p.x() < (double) (b.x() - a.x()) * (p.y() - a.y()) / (b.y() - a.y()) + a.x());

                if (intersects) {
                    inside = !inside;
                }
            }

            return inside;
        }

        static boolean pointOnSegment(Vec2 a, Vec2 b, Vec2 p) {
            // cross product == 0  -> collinear
            long cross =
                    (long) (p.y() - a.y()) * (b.x() - a.x()) -
                            (long) (p.x() - a.x()) * (b.y() - a.y());

            if (cross != 0) return false;

            // within bounding box
            return p.x() >= Math.min(a.x(), b.x()) &&
                    p.x() <= Math.max(a.x(), b.x()) &&
                    p.y() >= Math.min(a.y(), b.y()) &&
                    p.y() <= Math.max(a.y(), b.y());
        }

        static boolean rectangleEdgeCrossesPolygon(List<Vec2> poly, Vec2 bl, Vec2 br, Vec2 tr, Vec2 tl) {
            int n = poly.size();

            for (int i = 0; i < n; i++) {
                Vec2 p1 = poly.get(i);
                Vec2 p2 = poly.get((i + 1) % n);

                // skip edges that lie exactly on rectangle boundary
                if (Geometry.pointOnSegment(bl, br, p1) &&
                        Geometry.pointOnSegment(bl, br, p2)) continue;

                if (segmentsIntersect(bl, br, p1, p2)) return true;
                if (segmentsIntersect(br, tr, p1, p2)) return true;
                if (segmentsIntersect(tr, tl, p1, p2)) return true;
                if (segmentsIntersect(tl, bl, p1, p2)) return true;
            }
            return false;
        }

        @SuppressWarnings("CommentedOutCode")
        private static boolean segmentsIntersect(Vec2 a, Vec2 b, Vec2 c, Vec2 d) {
            /*
            long o1 = orient(a, b, c);
            long o2 = orient(a, b, d);
            long o3 = orient(c, d, a);
            long o4 = orient(c, d, b);

            if (o1 == 0 && Geometry.pointOnSegment(a, b, c)) return true;
            if (o2 == 0 && Geometry.pointOnSegment(a, b, d)) return true;
            if (o3 == 0 && Geometry.pointOnSegment(c, d, a)) return true;
            if (o4 == 0 && Geometry.pointOnSegment(c, d, b)) return true;

            return (o1 > 0) != (o2 > 0) && (o3 > 0) != (o4 > 0);
            */
            long o1 = orient(a, b, c);
            long o2 = orient(a, b, d);
            long o3 = orient(c, d, a);
            long o4 = orient(c, d, b);

            // Proper intersection: strict opposite orientations
            return (o1 > 0 && o2 < 0 || o1 < 0 && o2 > 0)
                    && (o3 > 0 && o4 < 0 || o3 < 0 && o4 > 0);
        }

        static long orient(Vec2 a, Vec2 b, Vec2 c) {
            return (long) (b.x() - a.x()) * (c.y() - a.y())
                    - (long) (b.y() - a.y()) * (c.x() - a.x());
        }
    }
}
