import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PrefixTree {
	private Node root;
	private HashMap<Integer, ArrayList<Node>> nodes = new HashMap<>();
	private HashMap<Node, ArrayList<Edge>> edges = new HashMap<>();
	private int height = 0;

	public PrefixTree(Node root) {
		this.root = root;
		nodes.put(root.getLevel(), new ArrayList<Node>());
		nodes.get(0).add(root);
		edges.put(root, new ArrayList<Edge>());
		height++;
	}

	public ArrayList<String> preOrder(Node start) {

		ArrayList<String> output = new ArrayList<>();

		//current string that is being evaluated.
		String currentString = "";
		//append the root node as the start point
		currentString += start.getValue();
		ArrayList<Edge> lowerEdges = edges.get(start);
		Node parentNode = start;
		int index = 0;

		while(!lowerEdges.isEmpty()) {
			//check if the current index value goes outside of the bounds of the number of child edges.
			if(index >= lowerEdges.size()) {
				parentNode.resetIndex();
				
				//if this happens for the root then you have traversed the entire tree.
				if(!parentNode.equals(start)) {
					parentNode = parentNode.getParent();
					lowerEdges = edges.get(parentNode);
					currentString = currentString.substring(0, currentString.length() - 1);
					//reset the index here as you've returned to your parent node.
					index = parentNode.getCurrentChild();
					lowerEdges = edges.get(parentNode);
				} else {
					break;
				}
			} else {
				//the currentEdge is the index into the edges of the current parent;
				Edge currentEdge = lowerEdges.get(index);
				//the current child node being evaluated.
				Node child = currentEdge.getChild();
				currentString += child.getValue();
				lowerEdges = edges.get(child);

				//check if there is a word.
				if(child.isWord()) {
					output.add(currentString);
				}

				currentEdge.getParent().countUp();
				//if the child has no children, it is a leaf
				if(lowerEdges.isEmpty()) {
					lowerEdges = edges.get(currentEdge.getParent());
					//remove the last char from the currentString
					currentString = currentString.substring(0, currentString.length() - 1);
					index++;
				} else {
					parentNode = child;
					index = 0;
				}
			}
		}
		return output;
	}

	public void addEdge(Edge e) {
		if(e.getParent().getLevel() == e.getChild().getLevel() - 1) {
			if(nodes.containsKey(e.getChild().getLevel())) {
				nodes.get(e.getChild().getLevel()).add(e.getChild());
			} else {
				nodes.put(e.getChild().getLevel(), new ArrayList<Node>());
				nodes.get(e.getChild().getLevel()).add(e.getChild());
				height++;
			}
			edges.put(e.getChild(), new ArrayList<Edge>());
			edges.get(e.getParent()).add(e);
		}
	}

	public boolean doesWordExist(char[] word) {
		Node currentNode = root;

		for(int i = 0; i < word.length - 1; i++) {
			if(currentNode.getValue() == word[i]) {
				Edge[] currentEdges = new Edge[edges.get(currentNode).size()];
				edges.get(currentNode).toArray(currentEdges);
				Edge eval = binarySearch(currentEdges, word[i + 1]);
				if(eval != null) {
					currentNode = eval.getChild();
				} else {
					return false;
				}
			}
		}
		
		return true;
	}

	private Edge binarySearch(Edge[] edges, char letter) {
		int alphabetIndex = letter - 'a';
		int middle = edges.length / 2;
		int pointer = edges.length;
		int start = 0;

		while(edges.length > 1) {
			if(edges[middle].getChild().getValue() == letter) {
				return edges[middle];
			}

			if(edges[middle].getChild().getValue() < letter) {
				start = middle;
				middle = (pointer - start) / 2;
			}

			if(edges[middle].getChild().getValue() > letter) {
				pointer = middle;
				middle = (pointer - start) / 2;
			}
		}
		
		if(edges[0].getChild().getValue() == letter) {
			return edges[0];
		}
		
		return null;
	}

	public List<String> autoComplete(char[] currentWord) {
		Node currentNode = root;
		String prefix = "";
		//loop to the current node
		for(int i = 0; i < currentWord.length - 1; i++) {
			if(currentNode.getValue() == currentWord[i]) {
				Edge[] currentEdges = new Edge[edges.get(currentNode).size()];
				edges.get(currentNode).toArray(currentEdges);
				Edge eval = binarySearch(currentEdges, currentWord[i + 1]);
				if(eval != null) {
					currentNode = eval.getChild();
					prefix += eval.getParent().getValue();
				} else {
					
				}
			}
		}
		
		ArrayList<String> suffixes = preOrder(currentNode);
		ArrayList<String> output = new ArrayList<>();
		for(String s : suffixes) {
			String temp = prefix.concat(s);
			output.add(s);
		}
		
		return output;
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void sortChildren(Node s) {
		edges.get(s).sort(new EdgeComparator());
	}
	
	public Edge DoesEdgeExist(Edge n) {
		ArrayList<Edge> nodesAtLevel = edges.get(n.getParent());
		for(Edge e : nodesAtLevel) {
			if(e.getChild().getValue() == n.getChild().getValue()) {
				if(n.getChild().isWord()) {
					e.getChild().setIsWord();
				}
				return e;
			}
		}
		return null;
	}
	
	private class EdgeComparator implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
			Edge e1 = (Edge) arg0;
			Edge e2 = (Edge) arg1;
			return e1.getChild().getValue() - e2.getChild().getValue();
		}
		
	}
}
