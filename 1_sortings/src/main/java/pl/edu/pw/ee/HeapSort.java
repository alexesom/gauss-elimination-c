package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class HeapSort implements Sorting{
    private final Heap<Double> heap;

    public HeapSort() {
        heap = new Heap<>();
    }

    @Override
    public void sort(double[] nums) {
        for (double num : nums) {
            heap.put(num);
        }
        for(int i = nums.length - 1; i >= 0; i--) {
            nums[i] = heap.pop();
        }
    }
}
