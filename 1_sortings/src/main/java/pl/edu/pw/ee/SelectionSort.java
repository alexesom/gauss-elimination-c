package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class SelectionSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        int minimumID = 0;
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            minimumID = smallestNumber(nums, i, n);


            double temp = nums[minimumID];
            nums[minimumID] = nums[i];
            nums[i] = temp;
        }
    }

    private int smallestNumber(double[] array, int start, int end) {
        int minId = start;
        int i = start + 1;
        while (i < end) {
            if (array[minId] > array[i]) 
                minId = i;
            i++;
        }

        return minId;
    }
}
