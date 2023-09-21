package Task_1_1_1;
import java.util.Scanner;

public class Main {
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    void heapify(int[] arr, int n, int i) {
        int max_elem = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[max_elem]) {
            max_elem = l;
        }
        if (r < n && arr[r] > arr[max_elem]) {
            max_elem = r;
        }
        if (max_elem != i) {
            int tmp = arr[i];
            arr[i] = arr[max_elem];
            arr[max_elem] = tmp;
            heapify(arr, n, max_elem);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            int elem = scan.nextInt();
            arr[i] = elem;
        }
        Main ob = new Main();
        ob.sort(arr);
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}