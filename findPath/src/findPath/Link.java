package findPath;

/**
 * @author Makone_Xia
 * The Link class is reserved in case of the need of next edition which may use the
 * time spent between stations to estimate the total time.
 */

public class Link {
	
	private Node nodeFrom;
    private Node nodeTo;
    private String stringFrom;
    private String stringTo;
    private String lineName;
    private float price;

	public Link(Node nodeFrom, Node nodeTo){
		
	}

	public Link(String stringFrom,String stringTo,String lineName){
		setStringFrom(stringFrom);
		setStringTo(stringTo);
		setLineName(lineName);
		if(lineName.equals("Line2")||lineName.equals("Line5")) setPrice(1);
		else setPrice(0.5f);
	}

	public Node getNodeFrom() {
		return nodeFrom;
	}


	public void setNodeFrom(Node nodeFrom) {
		this.nodeFrom = nodeFrom;
	}


	public Node getNodeTo() {
		return nodeTo;
	}


	public void setNodeTo(Node nodeTo) {
		this.nodeTo = nodeTo;
	}


	public String getStringFrom() {
		return stringFrom;
	}


	public void setStringFrom(String stringFrom) {
		this.stringFrom = stringFrom;
	}


	public String getStringTo() {
		return stringTo;
	}


	public void setStringTo(String stringTo) {
		this.stringTo = stringTo;
	}


	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


}
