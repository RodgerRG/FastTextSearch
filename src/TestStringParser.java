import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class TestStringParser {

	public static void main(String[] args) throws IOException {
		ArrayList<PrefixTree> trees = new ArrayList<>();
		for(int i = 'a'; i <= 'z'; i++) {
			Node root = new Node((char) i, 0, false);
			trees.add(new PrefixTree(root));
		}
		
		StringParser parser = new StringParser(trees);
		BufferedReader reader = new BufferedReader(new FileReader(Paths.get("/home/rodger/words.txt").toFile()));
		double startTime = System.nanoTime();
		for(int i = 0; i < 10000; i++) {
			parser.parseWord(reader.readLine());
		}
		
		double endTime = System.nanoTime();
		
		for(PrefixTree t : trees) {
			for(String s : t.preOrder(t.getRoot())) {
				System.out.println(s);
			}
		}
	
		System.out.println((endTime - startTime) / 1e9);
	}

}
