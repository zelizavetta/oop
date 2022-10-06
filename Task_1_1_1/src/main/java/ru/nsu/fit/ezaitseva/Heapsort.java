package ru.nsu.fit.ezaitseva;

/**
 * Heapsort.
 * Sorting using heap.
 */
public class Heapsort {
    /**
     * Sorting specify arr.
     *
     * @param arr for sorting.
     */
    static public void sort(int arr[]) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    static private void heapify(int arr[], int n, int i) {
        int max_elem = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[max_elem])
            max_elem = l;
        if (r < n && arr[r] > arr[max_elem])
            max_elem = r;
        if (max_elem != i) {
            int tmp = arr[i];
            arr[i] = arr[max_elem];
            arr[max_elem] = tmp;
            heapify(arr, n, max_elem);
        }
    }
}
