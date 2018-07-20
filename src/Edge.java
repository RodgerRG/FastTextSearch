
public class Edge implements Comparable{
	private Node parent;
	private Node child;
	
	public Edge(Node parent, Node child) {
		this.parent = parent;
		this.child = child;
		this.child.setParent(this.parent);
	}
	
	public Node getChild() {
		return child;
	}
	
	public Node getParent() {
		return this.parent;
	}

	@Override
	public int compareTo(Object arg0) {
		Edge e = (Edge) arg0;
		return child.getValue() - e.getChild().getValue();
	}
}
