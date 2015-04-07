package findPath;

import java.util.ArrayList;

public class Path {

	private ArrayList<Node> nodesList = new ArrayList<Node>();
	// private ArrayList<Link> links = new ArrayList<Link>();
	private int transferCount = 0;
	private float totalPrice = Float.MAX_VALUE;

	public Path() {
		super();
	}

	public Path(Path rawPath) {
		this.nodesList = new ArrayList<Node>(rawPath.getNodesList());
		this.totalPrice = rawPath.getTotalPrice();
		this.transferCount = rawPath.getTransferCount();
	}

	public void changeTo(Path path) {
		this.nodesList = path.getNodesList();
		this.totalPrice = path.getTotalPrice();
		this.transferCount = path.getTransferCount();
	}

	public void changeTransferCount() {
		this.transferCount++;
	}

	public ArrayList<Node> getNodesList() {
		return nodesList;
	}

	public void setNodesList(ArrayList<Node> nodes) {
		this.nodesList = nodes;
	}

	public int getTransferCount() {
		return transferCount;
	}

	public int getTransferCount(Node destination) {
		if (this.getNodesList().get(this.getNodesList().size() - 1).equals(destination))
			return transferCount;
		else
			return Integer.MAX_VALUE;
	}

	public void setTransferCount(int transferCount) {
		this.transferCount = transferCount;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	private void addTotalPrice(float price) {
		this.totalPrice += price;
	}

	public boolean isReached(Node destination) {
		if (this.getNodesList().get(this.getNodesList().size() - 1).equals(destination))
			return true;
		else
			return false;
	}

	public int addNode(Node node) {
		this.nodesList.add(node);
		changeTransferCount();
		return this.getTransferCount();
	}

	public float calculatePriceTo(Node destination) {
		setTotalPrice(0f);
		for (int loop = 0; loop < this.getTransferCount() - 1; loop++) {
			switch (whichLine(loop, this.getNodesList())) {
			case "LINE1":
				this.addTotalPrice(0.5f);
				break;
			case "LINE2":
				this.addTotalPrice(1f);
				break;
			case "LINE3":
				this.addTotalPrice(0.5f);
				break;
			case "LINE4":
				this.addTotalPrice(0.5f);
				break;
			case "LINE5":
				this.addTotalPrice(1f);
				break;
			}
		}
		if (this.getNodesList().get(this.getNodesList().size() - 1).equals(destination))
			return this.totalPrice;
		else
			return Float.MAX_VALUE;

	}

	public String toString() {
		StringBuffer outPut = new StringBuffer();
		outPut.append(totalPrice + ":" + getNodesList().get(0).getNodeName());
		for (int loop = 1; loop < this.getTransferCount(); loop++) {
			outPut.append("," + getNodesList().get(loop).getNodeName());
		}
		return outPut.toString();
	}

	public boolean contains(Node node) {
		for (int loop = 0; loop < this.getTransferCount(); loop++) {
			if (this.getNodesList().get(loop).equals(node))
				return true;
		}
		return false;
	}

	/**
	 * Determine the line. TODO BUG: IF THERE ARE TWO NEARBY STATIONS CONNECTED BY TWO DIFFERENT LINES,THERE WILL BE NO UNIQUE RESULT.
	 * 
	 * @param location
	 * @param nodesList
	 * @return
	 */
	public String whichLine(int location, ArrayList<Node> nodesList) {
		if (nodesList.get(location).getLineName().size() == 1)
			return nodesList.get(location).getLineName().get(0);
		else {
			ArrayList<String> lineNameList1 = new ArrayList<String>(nodesList.get(location).getLineName());
			ArrayList<String> lineNameList2 = new ArrayList<String>();
			if (location != this.nodesList.size() - 1) {
				if (nodesList.get(location + 1).getLineName().size() == 1)
					return nodesList.get(location + 1).getLineName().get(0);
				else {
					lineNameList2 = nodesList.get(location + 1).getLineName();
					int temp1 = lineNameList1.size(), temp2 = lineNameList2.size();
					for (int loop1 = 0; loop1 < temp1; loop1++) {
						String lineName1 = lineNameList1.get(loop1);
						for (int loop2 = 0; loop2 < temp2; loop2++) {
							String lineName2 = lineNameList2.get(loop2);
							if (lineName1.equals(lineName2))
								return lineName1;
						}
					}
				}
			} else {
				if (nodesList.get(location - 1).getLineName().size() == 1)
					return nodesList.get(location - 1).getLineName().get(0);
				else {
					lineNameList2 = nodesList.get(location - 1).getLineName();
					int temp1 = lineNameList1.size(), temp2 = lineNameList2.size();
					for (int loop1 = 0; loop1 < temp1; loop1++) {
						String lineName1 = lineNameList1.get(loop1);
						for (int loop2 = 0; loop2 < temp2; loop2++) {
							String lineName2 = lineNameList2.get(loop2);
							if (lineName1.equals(lineName2))
								return lineName1;
						}
					}
				}
			}
		}
		return null;
	}
}
