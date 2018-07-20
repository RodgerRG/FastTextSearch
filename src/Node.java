
public class Node {
	private int level;
	private int currentChild = 0;
	private char value;
	private boolean isWord;
	private Node parent;
	
	public Node(char value, int level, boolean isWord) {
		this.value = value;
		this.level = level;
		this.isWord = isWord;
	}
	
	public void levelUp() {
		level += 1;
	}
	
	public void levelDown() {
		level -= 1;
	}
	
	public int getLevel() {
		return level;
	}
	
	public char getValue() {
		return value;
	}
	
	public boolean isWord() {
		return isWord;
	}
	
	public void countUp() {
		currentChild++;
	}
	
	public int getCurrentChild() {
		return currentChild;
	}
	
	public void setParent(Node n) {
		this.parent = n;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void resetIndex() {
		currentChild = 0;
	}
	
	public void setIsWord() {
		isWord = true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Node) {
			Node n = (Node) obj;
			return n.getValue() == value && n.getLevel() == level;
		}
		return false;
	}
}
