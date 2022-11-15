package pgdp.convexhull;

import java.util.Arrays;
import java.util.Objects;

public class QuickHull {


	/* ================ Zu implementierende Methoden ================ */


	// Returns hull as {p, ..., q, ..., p}, where p is the leftmost point
	public static int[][] quickHull(int[][] points) {
		return null;
	}

	// Returns hull-points in counter-clockwise fashion: {q, ..., p}
	public static int[][] quickHullLeftOf(int[][] points, int[] p, int[] q) {
		return null;
	}

	public static int[][] combineHulls(int[][] firstHull, int[][] secondHull) {
		return null;
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
		int[][] in = new int[][] { { 0, 0 }, { 0, 2 }, { 2, 0 }, { 2, 2 }, { 1, 1 } };
		int[][] hull = quickHull(in);
		System.out.println(pointsToPlotString(in));
		System.out.println(pointsToPlotString(hull));
	}

}
