package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class InsertionSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        if(nums == null) {
            throw new IllegalArgumentException("Nums array cannot be null");
        }

        insertionSort(nums);
    }

    private void insertionSort(double[] nums) {
        int n = nums.length;

        for (int i = 1; i < n; i++) {
            double temp = nums[i];
            int j = i - 1;

            while (j >= 0 && nums[j] > temp) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = temp;
        }
    }
}
