import java.util.ArrayList;

public class TestInputMain {

	public static void main(String[] args) {
		Node root = new Node('a', 0, true);
		root.setParent(root);
		Node nodeA = new Node('r', 1, false);
		Node nodeB = new Node('e', 2, true);
		Node nodeC = new Node('t', 1, true);
		Node nodeD = new Node('e', 2, true);
		PrefixTree tree = new PrefixTree(root);
		tree.addEdge(new Edge(root, nodeA));
		tree.addEdge(new Edge(nodeA, nodeB));
		tree.addEdge(new Edge(root, nodeC));
		tree.addEdge(new Edge(nodeC, nodeD));
		
		ArrayList<String> dict = tree.preOrder(tree.getRoot());
		dict = tree.preOrder(tree.getRoot());
		for(int i = 0; i < dict.size(); i++) {
			System.out.println(dict.get(i));
		}
		
		System.out.println(tree.doesWordExist("are".toCharArray()));
		
		ArrayList<String> potentialWords = (ArrayList<String>) tree.autoComplete("a".toCharArray());
		for(int i = 0; i < potentialWords.size(); i++) {
			System.out.println(potentialWords.get(i));
		}
	}

}
