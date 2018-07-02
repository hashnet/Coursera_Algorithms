import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static class Job{
		int weight;
		int length;
			
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/jobs.txt"));
		
		int count = sc.nextInt();
		System.out.println(count);
		
		List<Job> jobs = new ArrayList<>();
		for(int i=0; i<count; i++) {
			Job job = new Job();
			job.weight = sc.nextInt();
			job.length = sc.nextInt();
			
			jobs.add(job);
		}
		
		jobs.sort(new Comparator<Job>() {
			@Override
			public int compare(Job j1, Job j2) {
				int key1 = j1.weight - j1.length;
				int key2 = j2.weight - j2.length;
				
				return key2 == key1 ? j2.weight - j1.weight : key2 - key1;
			}
		});
		long sum = weightedSum(jobs);
		System.out.println(sum);
		
		jobs.sort(new Comparator<Job>() {
			@Override
			public int compare(Job j1, Job j2) {
				double key1 = (double)j1.weight / j1.length;
				double key2 = (double)j2.weight / j2.length;
				
				
				return key2 > key1 ? +1 : (key2 < key1 ? -1 : 0);
			}
		});
		sum = weightedSum(jobs);
		System.out.println(sum);
		
		sc.close();
	}
	
	private static long weightedSum(List<Job> jobs) {
		long sum = 0;
		int t = 0;
		for(Job job: jobs) {
			t += job.length;
			sum += job.weight * t;
		}
		
		return sum;
	}
}
