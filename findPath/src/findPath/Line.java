package findPath;

import java.util.ArrayList;

public class Line {

	private boolean isCircle;
	private String lineName;
	private ArrayList<Node> nodesInLine = new ArrayList<Node>();

	public Line() {

	}

	public Line(boolean isCircle, String lineName) {
		setCircle(isCircle);
		setLineName(lineName);
	}

	public boolean isCircle() {
		return isCircle;
	}

	public void setCircle(boolean isCircle) {
		this.isCircle = isCircle;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public ArrayList<Node> getNodesInLine() {
		return nodesInLine;
	}

	public void setNodesInLine(ArrayList<Node> nodesInLine) {
		this.nodesInLine = nodesInLine;
	}

	public void addNodeToLine(Node node) {
		this.nodesInLine.add(node);
	}

	public void linkToCircle() {
		System.out.println("Linking to a circle...");
		Node aNode = nodesInLine.get(0);
		aNode.addNext(nodesInLine.get(nodesInLine.size() - 1));
		nodesInLine.set(0, aNode);
		aNode = nodesInLine.get(nodesInLine.size() - 1);
		aNode.addNext(nodesInLine.get(0));
		nodesInLine.set(nodesInLine.size() - 1, aNode);
	}

}
