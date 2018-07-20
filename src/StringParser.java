import java.util.ArrayList;

/**Contains the logic for parsing the string into the array of trees*/
public class StringParser {
	private ArrayList<PrefixTree> trees;
	
	public StringParser(ArrayList<PrefixTree> trees) {
		this.trees = trees;
	}
	
	public void parseWord(String word) {
		String inputWord = word.toLowerCase();
		char[] letters = inputWord.toCharArray();
		PrefixTree currentTree = trees.get(letters[0] - 'a');
		Node previousNode = currentTree.getRoot();
		
		for(int i = 1; i < letters.length - 1; i++) {
			Node newNode = new Node(letters[i], i, false);
			Edge potentialEdge = new Edge(previousNode, newNode);
			Edge treeEdge = currentTree.DoesEdgeExist(potentialEdge);
			if(treeEdge == null) {
				currentTree.addEdge(potentialEdge);
				currentTree.sortChildren(previousNode);
				previousNode = newNode;
			} else {
				previousNode = treeEdge.getChild();
			}
			
		}
		Node finalNode = new Node(letters[letters.length - 1], letters.length - 1, true);
		Edge finalEdge = new Edge(previousNode, finalNode);
		
		if(currentTree.DoesEdgeExist(finalEdge) == null) {
			currentTree.addEdge(finalEdge);
			currentTree.sortChildren(previousNode);
		}
	}
}
