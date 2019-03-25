import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Board board = new Board();
		
		Scanner sc = new Scanner(new FileInputStream("files/input.txt"));
		
		board.populateBoared(sc);
		if(board.populateBuckets()) {
			board.printBoard();
		} else {
			System.out.println("Invalid Board");
		}
		
	}
}
