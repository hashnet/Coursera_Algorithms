import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static long m = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/QuickSort.txt"));
		//Scanner sc = new Scanner(new FileInputStream("files/sample1.txt"));
		
		int count = sc.nextInt();
		int[] arr = new int[count];
		
		for(int i=0; i<count; i++) {
			arr[i] = sc.nextInt();
		}
		
		qSort3(arr, 0, count);
		System.out.print(m + ": ");
		for(int i : arr) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	private static void qSort1(int[] arr, int left, int right) {
		int count = right - left;
		if(count <= 1) return;
		
		m += count-1;
		
		int pivot = arr[left];
		int i = left+1;
		for(int j=left+1; j<right; j++) {
			if(arr[j] < pivot) {
				swap(arr, i, j);
				
				++i;
			}
		}
		
		int newPivot = i-1;
		swap(arr, left, newPivot);
		
		qSort1(arr, left, newPivot);
		qSort1(arr, newPivot+1, right);
		
	}
	
	private static void qSort2(int[] arr, int left, int right) {
		int count = right - left;
		if(count <= 1) return;
		
		m += count-1;
		
		swap(arr, left, right-1);	//to use the right most element as the pivot;
		
		int pivot = arr[left];
		int i = left+1;
		for(int j=left+1; j<right; j++) {
			if(arr[j] < pivot) {
				swap(arr, i, j);
				
				++i;
			}
		}
		
		int newPivot = i-1;
		swap(arr, left, newPivot);
		
		qSort2(arr, left, newPivot);
		qSort2(arr, newPivot+1, right);
		
	}
	
	private static void qSort3(int[] arr, int left, int right) {
		int count = right - left;
		if(count <= 1) return;
		
		m += count-1;
		
		int l = arr[left];
		int r = arr[right-1];
		int m = arr[(right-left-1)/2 + left];
		int max = Math.max(Math.max(l, r), m);
		int min = Math.min(Math.min(l,  r), m);
		if(l < max && l > min) {} 	//to use the left most element as the pivot;
		else if(r < max && r > min) {swap(arr, left, right-1);} 	//to use the right most element as the pivot;
		else if(m < max && m > min) {swap(arr, left, (right-left-1)/2 + left);} 	//to use the middle most element as the pivot;
		
		int pivot = arr[left];
		int i = left+1;
		for(int j=left+1; j<right; j++) {
			if(arr[j] < pivot) {
				swap(arr, i, j);
				
				++i;
			}
		}
		
		int newPivot = i-1;
		swap(arr, left, newPivot);
		
		qSort3(arr, left, newPivot);
		qSort3(arr, newPivot+1, right);
		
	}
	
	private static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
}
