import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/IntegerArray.txt"));
//		Scanner sc = new Scanner(new FileInputStream("files/test_6_3.txt"));
		
		int n = sc.nextInt();
		int[] arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = sc.nextInt();
		}
		
		long inv = inversion(arr);
		
		System.out.println(inv);
	}
	
	private static long inversion(int[] arr) {
		int len = arr.length;
		if(len == 1) return 0;
		
		int mid = len/2;
		int leftLen = mid;
		int rightLen = len-mid;
		
		int[] left = new int[leftLen];
		System.arraycopy(arr, 0, left, 0, leftLen);
		int[] right = new int[rightLen];
		System.arraycopy(arr, leftLen, right, 0, rightLen);
		
		long inv = inversion(left) + inversion(right);
		
		if(len == 6) {
			//stop here;
			len = 6;
		}
		int l=0;
		int r=0;
		for(int k=0; k<len; k++) {
			if(l<leftLen) {
				if(r<rightLen) {
					if(left[l] > right[r]) {
						inv += leftLen - l;
						arr[k] = right[r++];
					} else {
						arr[k] = left[l++];
					}
				} else {
					arr[k] = left[l++];
				}
			} else {
				arr[k] = right[r++];
			}
		}
		
		return inv;
	}
}
