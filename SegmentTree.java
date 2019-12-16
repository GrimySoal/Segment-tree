package SegmentTree;

import java.util.ArrayList;
import java.util.List;

public class SegmentTree {
    private Double[] arrayOfSums;

    public SegmentTree(ArrayList<Double> array) {
        arrayOfSums = new Double[array.size() * 4];
        CreateSegmentsTree(array, 1);
    }

    private double CreateSegmentsTree(List<Double> subList, int index) {
        if (subList.size() == 1) {
            return arrayOfSums[index] = subList.get(0);
        }
        int size = subList.size();
        double leftSubArray = CreateSegmentsTree(subList.subList(0, size / 2), index * 2);
        double rightSubArray = CreateSegmentsTree(subList.subList(size / 2, size), index * 2 + 1);
        return arrayOfSums[index] = leftSubArray + rightSubArray;
    }

    public double getSum(int leftBound, int rightBound) {
        if (leftBound > rightBound) {
            throw new IllegalArgumentException("the left bound is bigger than the right one");
        }
        return FindSumOfSegment(leftBound, rightBound, 0, arrayOfSums.length / 4 - 1, 1);
    }

    private double FindSumOfSegment(int leftBound, int rightBound, int leftPosition, int rightPosition, int index) {
        if (leftBound == leftPosition && rightBound == rightPosition) {
            return arrayOfSums[index];
        }
        int centrePosition = (leftPosition + rightPosition + 1) / 2;
        if (leftBound >= centrePosition) {
            return FindSumOfSegment(leftBound, rightBound, centrePosition, rightPosition, index * 2 + 1);
        }
        if (rightBound < centrePosition) {
            return FindSumOfSegment(leftBound, rightBound, leftPosition, centrePosition - 1, index * 2);
        }
        return FindSumOfSegment(leftBound, centrePosition - 1, leftPosition, centrePosition - 1, index * 2)
                + FindSumOfSegment(centrePosition, rightBound, centrePosition, rightPosition, index * 2 + 1);
    }

    private double changeValue(int position, double value, int leftPosition, int rightPosition, int index) {
        if (leftPosition == rightPosition) {
            return arrayOfSums[index] - (arrayOfSums[index] = value);
        }
        int centrePosition = (leftPosition + rightPosition + 1) / 2;
        double difference;
        if (position < centrePosition) {
            difference = changeValue(position, value, leftPosition, centrePosition - 1, index * 2);
        } else {
            difference = changeValue(position, value, centrePosition, rightPosition, index * 2 + 1);
        }
        arrayOfSums[index] -= difference;
        return difference;
    }

    public void set(int index, double value) {
        if (index + 1 > arrayOfSums.length / 4) {
            throw new IllegalArgumentException("Index out of range!");
        }
        changeValue(index, value, 0, arrayOfSums.length / 4 - 1, 1);
    }
}
