package pgdp.convexhull;

import java.util.Arrays;
import java.util.Objects;

public class QuickHull {


	/* ================ Zu implementierende Methoden ================ */


	// Returns hull as {p, ..., q, ..., p}, where p is the leftmost point
	public static int[][] quickHull(int[][] points) {
		//Tests enthalten mindestens 2 Elemente, welche sich in x-Richtung unterscheiden

		int[] p = findLeftmostPoint(points);
		int[] q = findRightmostPoint(points);
		return combineHulls(quickHullLeftOf(points, q, p), quickHullLeftOf(points, p, q));
	}

	// Returns hull-points in counter-clockwise fashion: {q, ..., p}
	public static int[][] quickHullLeftOf(int[][] points, int[] p, int[] q) {
		return qHLO_helper(points, p, q, findPointFurthestLeftFrom(points, p, q));
	}

	public static int[][] qHLO_helper(int[][] points, int[] p, int[] q, int[] r) {
		//catche falls einer der Punkte leer ist oder null
		if (p != null && p.length > 0 && q != null && q.length > 1) {
			if (existsPointLeftOf(points, p, q)) {
				//erstelle rekursiv Dreiecke und teile sie wieder in rq und pr, verbinde diese mit combineHulls
				return combineHulls(qHLO_helper(points, r, q, findPointFurthestLeftFrom(points, r, q)), qHLO_helper(points, p, r, findPointFurthestLeftFrom(points, p, r)));
			} else {
				//falls kein Punkt weiter links liegt gib zwei Punkte der Grundseite des Dreiecks zurück, gegen Uhrzeigersinn
				return new int[][] {q, p};
			}
		} else {
			return new int[0][];
		}
	}

	public static int[][] combineHulls(int[][] firstHull, int[][] secondHull) {
		//konkateniere firstHull und secondHull, lösche dabei den Endpunkt von firstHull bzw. Startpunkt von secondHull, sodass dieser Punkt nur einmal vorkommt
		//falls einer der beiden Arrays == null catchen
		if (firstHull != null && secondHull != null) {
			//falls eines der Arrays leer catchen
			if (firstHull.length < 1 && secondHull.length > 0) {
				return secondHull;
			} else if (secondHull.length < 1 && firstHull.length > 0) {
				return firstHull;
			} else if (firstHull.length < 1 && secondHull.length < 1) {
				return new int[0][];
			} else {
				int[][] combinedHull = new int[firstHull.length + secondHull.length - 1][];
				for (int i = 0; i < combinedHull.length; i++) {
					if (i < firstHull.length) {
						combinedHull[i] = firstHull[i];
					} else {
						combinedHull[i] = secondHull[i - firstHull.length + 1];
					}
				}
				return combinedHull;
			}
		} else {
			return new int[0][];
		}
	}


	/* ================ Vorgegebene Methoden ================ */


	public static int[] findPointFurthestLeftFrom(int[][] points, int[] firstLinePoint, int[] secondLinePoint) {
		double maxDistance = 0.0;
		int[] leftmostPoint = null;
		for(int i = 0; i < points.length; i++) {
			double distance = Math.abs(signedDistance(points[i], firstLinePoint, secondLinePoint));
			if(isPointLeftOf(points[i], firstLinePoint, secondLinePoint) && distance > maxDistance) {
				maxDistance = distance;
				leftmostPoint = points[i];
			}
		}
		return leftmostPoint;
	}

	public static int[] findLeftmostPoint(int[][] points) {
		int[] currentLeftmost = points[0];
		for(int i = 1; i < points.length; i++) {
			if(points[i][0] < currentLeftmost[0]) {
				currentLeftmost = points[i];
			}
		}
		return currentLeftmost;
	}

	public static int[] findRightmostPoint(int[][] points) {
		int[] currentRightmost = points[0];
		for(int i = 1; i < points.length; i++) {
			if(points[i][0] > currentRightmost[0]) {
				currentRightmost = points[i];
			}
		}
		return currentRightmost;
	}

	public static boolean isPointLeftOf(int[] point, int[] firstLinePoint, int[] secondLinePoint) {
		double n = signedDistance(point, firstLinePoint, secondLinePoint);
		return n < 0;
	}

	public static boolean existsPointLeftOf(int[][] points, int[] firstLinePoint, int[] secondLinePoint) {
		for(int i = 0; i < points.length; i++) {
			if(isPointLeftOf(points[i], firstLinePoint, secondLinePoint)) {
				return true;
			}
		}
		return false;
	}

	public static double signedDistance(int[] point, int[] firstLinePoint, int[] secondLinePoint) {
		int det = (secondLinePoint[0] - firstLinePoint[0]) * (firstLinePoint[1] - point[1])
				- (firstLinePoint[0] - point[0]) * (secondLinePoint[1] - firstLinePoint[1]);
		double len = Math.sqrt(
				Math.pow(secondLinePoint[0] - firstLinePoint[0], 2) + Math.pow(secondLinePoint[1] - firstLinePoint[1], 2)
		);

		return det / len;
	}

	public static String pointsToPlotString(int[][] points) {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(points).filter(Objects::nonNull)
				.forEach(point -> sb.append("point(").append(point[0]).append("|").append(point[1]).append(")\n"));
		return sb.toString();
	}

	public static void main(String[] args) {
		int[][] in = new int[][] {{-1, 0}, {1, 1}, {3, -1}, {3, 2}, {2, 3}, {1, 4}, {4, 3}, {3, 5}, {2, 5}, {0, 4}, {1, 6}, {-1, 4}, {-2, 5}, {-2, 3}, {-3, 2}, {-2, 1}};
		int[][] hull = quickHull(in);
		System.out.println(pointsToPlotString(in));
		System.out.println(pointsToPlotString(hull));
	}

}
