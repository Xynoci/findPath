package findPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindPath {

	public static final int LINECOUNT = 5;// the total count of lines
	public static String PATH = "source/map.txt";
	public static final int TRANSFERS = 8;// the total count of transfer stations

	public static void main(String[] args) {

		ArrayList<Line> theMap = new ArrayList<Line>();
		ArrayList<ArrayList<int[]>> transfers = new ArrayList<ArrayList<int[]>>(LINECOUNT);

		try {
			transfers = initial(theMap);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("************[INITIAL ERROR]************");
		}

		do {
			System.out.println("\n************[READY FOR INPUT]************");
			ArrayList<Node> startAndDestNodes = new ArrayList<Node>(2);
			startAndDestNodes = startAndDest(theMap, transfers);// get and convert the input.
			Path thePath = new Path();
			thePath.addNode(startAndDestNodes.get(0));
			Find(startAndDestNodes, thePath, thePath);
			System.out.println("**************[PRICE & PATH]*************\n" + thePath.toString() + "\n");
		} while (true);
	}

	/**
	 * TODO BUG:The method will CRASH when there are not only one possible path with the same price and pass though same amount of stations.
	 * 
	 * @param startAndDestNodes
	 * @param currentPath
	 * @param thePath
	 * @return
	 */
	private static Path Find(ArrayList<Node> startAndDestNodes, Path current, Path thePath) {
		Path currentPath = new Path(current);
		Node start = currentPath.getNodesList().get(currentPath.getNodesList().size() - 1);
		Node destination = startAndDestNodes.get(1);
		int count = start.getNext().size();

		for (int loop = 0; loop < count; loop++) {
			Node nextNode = start.getNext().get(loop);
			if (currentPath.contains(nextNode) && (!nextNode.equals(startAndDestNodes.get(0)))) {
				continue;
			} else {
				int newPathCount;
				if (nextNode.equals(destination)) {
					Path newPath = new Path(currentPath);
					newPathCount = newPath.addNode(destination);
					if (newPath.calculatePriceTo(destination) <= thePath.calculatePriceTo(destination)) {
						if (newPathCount <= thePath.getTransferCount(destination)) {
							thePath.changeTo(newPath);
							continue;
						} else
							continue;
					} else
						continue;
				} else {
					Path newPath = new Path(currentPath);
					newPath.addNode(nextNode);
					if (newPath.getTransferCount() >= thePath.getTransferCount(destination))
						continue;
					else {
						newPath = Find(startAndDestNodes, newPath, thePath);
						if (newPath.isReached(destination)) {
							if (newPath.calculatePriceTo(destination) < thePath.calculatePriceTo(destination)) {
								if (newPath.getTransferCount() <= thePath.getTransferCount(destination)) {
									thePath.changeTo(newPath);
									continue;
								} else
									continue;
							} else
								continue;
						} else
							continue;
					}
				}
			}
		}
		return currentPath;
	}

	/**
	 * Convert the input string into usable data structure.
	 * 
	 * @param theMap
	 * @param transfers
	 * @return ArrayList<Node>(2) <start station, destination>
	 */
	@SuppressWarnings("resource")
	private static ArrayList<Node> startAndDest(ArrayList<Line> theMap, ArrayList<ArrayList<int[]>> transfers) {
		ArrayList<Node> startAndDestNodes = new ArrayList<Node>(2);
		String[] startAndDest = new String[2];
		do {
			System.out.println("Enter the starting station and the destination, please.\n"
					+ "The formation like \"L3-2,L1-3 \" is preferred (L3-2 represents the 2nd station of LINE3,and so on).");
			Scanner sc = new Scanner(System.in);
			startAndDest = sc.nextLine().toUpperCase().split(",");
		} while (inputIsWrong(startAndDest));
		for (int loop = 0; loop < 2; loop++) {
			// TODO BUG:If there are more than 10 lines or transfer stations, the line number will be wrong.
			int lineNumber = Integer.parseInt(startAndDest[loop].substring(1, 2)) - 1;
			if (startAndDest[loop].startsWith("X"))
				startAndDestNodes.add(theMap.get(transfers.get(lineNumber).get(0)[0]).getNodesInLine().get(transfers.get(lineNumber).get(0)[1]));
			else {
				int nodesCount = theMap.get(lineNumber).getNodesInLine().size();
				for (int loop1 = 0; loop1 < nodesCount; loop1++) {
					if (theMap.get(lineNumber).getNodesInLine().get(loop1).getNodeName().equals(startAndDest[loop]))
						startAndDestNodes.add(theMap.get(lineNumber).getNodesInLine().get(loop1));
				}
			}
		}
		return startAndDestNodes;
	}

	/**
	 * Showing the input sample and checking the formation of input, ask for another input until it matches the sample.
	 * 
	 * @param startAndDest
	 * @return boolean
	 */
	public static boolean inputIsWrong(String[] startAndDest) {
		if (startAndDest.length < 2) {
			System.err.println("You are supposed to enter a STARTING STATION and a DESTINATION, try again please.");
			return true;
		} else {
			if (startAndDest[0].isEmpty() || startAndDest[1].isEmpty()) {
				System.err.println("You are supposed to enter a STARTING STATION and a DESTINATION, try again please.");
				return true;
			} else if ((isNormal(startAndDest[0]) || isTransfer(startAndDest[0])) && (isNormal(startAndDest[1]) || isTransfer(startAndDest[1])))
				return false;
			else {
				System.err.println("There seems to be some mistakes in your input, try again please.");
				return true;
			}
		}
	}

	/**
	 * Checking the station's type, return true if it is not a transfer station.
	 * 
	 * @param station
	 * @return boolean
	 */
	public static boolean isNormal(String station) {
		if (station.length() >= 4)
			if (station.startsWith("L") && isNumber(station.substring(3)) && isNumber(station.substring(1, 2)) && station.substring(2, 3).equals("-"))
				return true;
			else
				return false;
		else
			return false;

	}

	/**
	 * Checking the station's type, return true if it is a transfer station.
	 * 
	 * @param station
	 * @return boolean
	 */
	public static boolean isTransfer(String station) {
		if (station.startsWith("X") && isNumber(station.substring(1)))
			return true;
		else
			return false;
	}

	/**
	 * Checking the string, return true if it could be convert to an integer.
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 1.Reading data from file, then link them to a line. 
	 * 2.Finding transfer stations in each line, then merge different priori and next nodes together.
	 * 3.Setting merged transfer stations into the line they belong to.
	 * 
	 * @param map
	 * @return map
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static ArrayList<ArrayList<int[]>> initial(ArrayList<Line> map) throws IOException {
		System.out.println("************[LOADING DATA]************");
		File file = new File(PATH);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		temp = br.readLine();

		// Initialing the map...
		System.out.println("Initialing the map...");
		while (temp != null) {
			System.out.println(temp.toString());
			String[] lineInfor = temp.split(",");
			boolean isCircle = false;
			if (Integer.parseInt(lineInfor[0]) == 1)
				isCircle = true;
			Line aLine = new Line(isCircle, lineInfor[1]);
			for (int loop = 2; loop < lineInfor.length; loop++) {
				Node aNode = new Node(lineInfor[loop], lineInfor[1]);
				aLine.addNodeToLine(aNode);
			}
			for (int loop = 0; loop < aLine.getNodesInLine().size() - 1; loop++) {
				aLine.getNodesInLine().get(loop).getNext().add(aLine.getNodesInLine().get(loop + 1));
				aLine.getNodesInLine().get(loop + 1).getNext().add(aLine.getNodesInLine().get(loop));
			}
			if (isCircle)
				aLine.linkToCircle();
			map.add(aLine);
			temp = br.readLine();
		}

		// Merging the transfer stations in different lines together...
		System.out.println("\nMerging the transfer stations in different lines together...");
		ArrayList<ArrayList<int[]>> transfers = new ArrayList<ArrayList<int[]>>(LINECOUNT);
		initialTransfers(transfers);
		for (int line = 0; line < LINECOUNT; line++) {// Search the transfer station and store them into "transfers".
			for (int node = 0; node < map.get(line).getNodesInLine().size(); node++) {
				String nodeName = map.get(line).getNodesInLine().get(node).getNodeName();
				if (nodeName.subSequence(0, 1).equals("X")) {
					int[] loc = new int[] { line, node };
					int transferId = Integer.parseInt(nodeName.substring(1)) - 1;
					// System.out.println(nodeName+":Line "+(line+1)+", Node "+(node+1));
					ArrayList<int[]> temp1 = new ArrayList<int[]>();
					if (isNotChanged(transfers.get(transferId))) {
						temp1.add(loc);
						transfers.set(transferId, temp1);
					} else {
						temp1 = transfers.get(transferId);
						temp1.add(loc);
						transfers.set(transferId, temp1);
					}
				}
			}
		}
		// Merging all the nearby nodes of one "transfers" station in different lines, then update them.
		for (int loop = 0; loop < TRANSFERS; loop++) {
			int length = transfers.get(loop).size();
			Node theNode = map.get(transfers.get(loop).get(0)[0]).getNodesInLine().get(transfers.get(loop).get(0)[1]);
			for (int loop1 = 1; loop1 < length; loop1++) {
				// "nextCount" is the number of nearby nodes the to-be-merged node has.
				// (Considering the starting station which is also a transfer station has just only one nearby station.)
				int nextCount = map.get(transfers.get(loop).get(loop1)[0]).getNodesInLine().get(transfers.get(loop).get(loop1)[1]).getNext().size();
				Node theOtherNode = map.get(transfers.get(loop).get(loop1)[0]).getNodesInLine().get(transfers.get(loop).get(loop1)[1]);
				theNode.addLineName(theOtherNode.getLineName().get(0));
				for (int loop2 = 0; loop2 < nextCount; loop2++)
					theNode.addNext(theOtherNode.getNext().get(loop2));
			}
			for (int loop3 = 1; loop3 < length; loop3++) {
				Line theLine = new Line();
				theLine = map.get(transfers.get(loop).get(loop3)[0]);
				ArrayList<Node> theNodeList = new ArrayList<Node>(theLine.getNodesInLine());
				Node toBeReplaced = theNodeList.get(transfers.get(loop).get(loop3)[1]);
				for (int loop4 = 0; loop4 < toBeReplaced.getNext().size(); loop4++)
					connect(theNode, toBeReplaced.getNext().get(loop4));
				theNodeList.set(transfers.get(loop).get(loop3)[1], theNode);
				theLine.setNodesInLine(theNodeList);
				map.set(transfers.get(loop).get(loop3)[0], theLine);
			}
		}
		System.out.println("Initial completed.The map is ready.");
		return transfers;
	}

	/**
	 * Connecting the new node with the line.
	 * 
	 * @param theTransfer
	 * @param theNext
	 */
	public static void connect(Node theTransfer, Node theNext) {
		int count = theNext.getNext().size();
		for (int loop = 0; loop < count; loop++) {
			if (theNext.getNext().get(loop).getNodeName().equals(theTransfer.getNodeName())) {
				ArrayList<Node> nodeList = new ArrayList<Node>(theNext.getNext());
				nodeList.set(loop, theTransfer);
				theNext.setNext(nodeList);
			}
		}
	}

	/**
	 * Initialize the "transfers" so that values could be set into it.
	 * 
	 * @param transfers
	 */
	private static void initialTransfers(ArrayList<ArrayList<int[]>> transfers) {
		int[] tempInt = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE };
		ArrayList<int[]> temp = new ArrayList<int[]>();
		temp.add(tempInt);
		for (int loop = 0; loop < TRANSFERS; loop++) {
			transfers.add(temp);
		}
	}

	private static boolean isNotChanged(ArrayList<int[]> temp) {
		if (temp.get(0)[0] == Integer.MAX_VALUE)
			return true;
		else
			return false;
	}

}
