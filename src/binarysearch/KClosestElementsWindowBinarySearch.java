package binarysearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array. The result should also be sorted in ascending order.
 *
 * An integer a is closer to x than an integer b if:
 *
 * |a - x| < |b - x|, or
 * |a - x| == |b - x| and a < b
 *
 * Example 1:
 * Input: arr = [1,2,3,4,5], k = 4, x = 3
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: arr = [1,2,3,4,5], k = 4, x = -1
 * Output: [1,2,3,4]
 */
public class KClosestElementsWindowBinarySearch {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        //  search for window starting directly using binary search
        int left = 0;
        int right = arr.length-k;

        while (left < right){
            int mid = left + (right-left)/2;
            // compare value at window beginning with window end.
            if (x-arr[mid] > arr[mid+k]-x)
                left = mid+1;
            else
                right = mid;
        }

        List<Integer> res = new ArrayList();

        for (int i=left;i<left+k;i++){
            res.add(arr[i]);
        }

        return res;
    }
}
