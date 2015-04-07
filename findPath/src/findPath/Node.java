package findPath;

import java.util.ArrayList;

public class Node {
	private String nodeName;
	private ArrayList<String> lineName = new ArrayList<String>();
	private ArrayList<Node> next = new ArrayList<Node>();

	public Node() {

	}

	public boolean equals(Node node) {
		if (this.getNodeName().equals(node.getNodeName()))
			return true;
		else
			return false;
	}

	public void addNext(Node node) {
		this.next.add(node);
	}

	public void addLineName(String lineName) {
		this.lineName.add(lineName);
	}

	public Node(String nodeName, String lineName) {
		setNodeName(nodeName);
		this.lineName.add(lineName);
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ArrayList<String> getLineName() {
		return lineName;
	}

	public void setLineName(ArrayList<String> lineName) {
		this.lineName = lineName;
	}

	public ArrayList<Node> getNext() {
		return next;
	}

	public void setNext(ArrayList<Node> next) {
		this.next = next;
	}

}
