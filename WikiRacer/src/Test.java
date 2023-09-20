import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Test {
	public static void main(String[] args) {
		Set<Integer> test1 = new HashSet<>();
		Set<Integer> test2 = new HashSet<>();
		
		test1.add(1);
		test1.add(2);
		test1.add(3);
		test1.add(4);
		test1.add(5);
		test1.add(6);
		test1.add(7);
		
		test2.add(11);
		test2.add(22);
		test2.add(33);
		test2.add(44);
		test2.add(55);
		test2.add(66);
		test2.add(7);
		
		System.out.println(test1);
		
		test1.retainAll(test2);
		
		System.out.println(test1);
	}
}
