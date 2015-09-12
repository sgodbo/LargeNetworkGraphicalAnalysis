package com.shan.jgraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import sun.text.normalizer.UBiDiProps;

import jxl.read.biff.BiffException;

public class MyThaiAlgo {

	public static List<Node> subtractList(List<Node> tempList, ArrayList<Node> members){
		ListIterator<Node> litr = tempList.listIterator();
		int i = 0;
		while(litr.hasNext()){
			if(members.contains(litr.next())){
				tempList.set(i, new Node(-1));
				//litr.remove();
			}
			i++;
		}
		return tempList;
	}
	
	public static List<Edge> subtractListOfEdges(List<Edge> tempList, ArrayList<Node> members){
		ListIterator<Edge> litr = tempList.listIterator();
		List<Edge> subList = new ArrayList<Edge>();
		int i = 0;
		Edge nextEdge;
		while(litr.hasNext()){
			nextEdge = litr.next();
			if(!containsNode(members,nextEdge.getEndingNode())){
				subList.add(nextEdge);
			}
			i++;
		}
		return subList;
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws BiffException, IOException {
		
		
		SolveGraphAsAdjacencyMatrix();
		//SolveGraphAsHashMap();
		// TODO Auto-generated method stub
		
	}
	private static void SolveGraphAsAdjacencyMatrix() throws BiffException, IOException {
		// TODO Auto-generated method stub
		GraphByAdjacencyMatrix g1 = new GraphByAdjacencyMatrix();
		int d = 20;
		ArrayList<Node> leaders = new ArrayList<Node>();
		ArrayList<Node> members = new ArrayList<Node>();
		ArrayList<Node> orbiters = new ArrayList<Node>();
		Map<Node,ArrayList<Node>> leadersAndMembers = new HashMap<Node,ArrayList<Node>>();
		Map<Node,ArrayList<Node>> communities = new HashMap<Node,ArrayList<Node>>();
		Node endingNode;
		Node startingNode;
		Edge tempEdge;
		List<Edge> tempList;
		List<Edge> reducedTempList;
		ListIterator<List<Edge>> litr1 = g1.getGraphAsListOfNodes().listIterator();
		while(litr1.hasNext()){
			tempList = returnTempList(litr1.next());
			ListIterator<Edge> litr2 = tempList.listIterator();
			if(litr2.hasNext()) {
				tempEdge = litr2.next();
				endingNode = tempEdge.getEndingNode();
				startingNode = tempEdge.getStartingNode();
				if((!containsNode(members,startingNode) && !containsNode(leaders,startingNode))){
					reducedTempList = subtractListOfEdges(tempList,members);
					endingNode = reducedTempList.get(0).getEndingNode();
					if(!reducedTempList.isEmpty()){
						members.add(startingNode);
						communities = containsNodeinCommunity(communities,endingNode,startingNode);
						//if(!containsNode(leaders,endingNode)){
							//leaders.add(endingNode);
						//}							
					} else {
						if(!containsNode(orbiters,startingNode))
							orbiters.add(startingNode);
						if(!containsNode(members,endingNode))
							members.add(endingNode);
					}
					
				}
			}
			
		
		}
		Iterator<Node> mItr = members.iterator();
		Iterator<Node> lItr = leaders.iterator();
		Iterator<Node> oItr = orbiters.iterator();
		
		for(Entry<Node,ArrayList<Node>> entry:communities.entrySet()){
			System.out.println(entry.getKey().returnNodeValue() + " adjacent to ");
			Iterator<Node> adjacentNodes = entry.getValue().iterator();
			while(adjacentNodes.hasNext()){
				System.out.println(adjacentNodes.next().returnNodeValue());
			}
		}
		System.out.println("Members");
		while(mItr.hasNext()){
			System.out.println(mItr.next().returnNodeValue());
		}
		System.out.println("Leaders");
		while(lItr.hasNext()){
			System.out.println(lItr.next().returnNodeValue());
		}
		System.out.println("Orbiters");
		while(oItr.hasNext()){
			System.out.println(oItr.next().returnNodeValue());
		}

		
	}
	private static Map<Node, ArrayList<Node>> containsNodeinCommunity(Map<Node,ArrayList<Node>> communities,
			Node endingNode,Node startingNode) {
		// TODO Auto-generated method stub
		ArrayList<Node> adjacentNodesToLeader = communities.get(endingNode);
		if(null != adjacentNodesToLeader){
			adjacentNodesToLeader.add(startingNode);
		} else {
			ArrayList<Node> newAdjacentNodesToLeader = new ArrayList<Node>();
			newAdjacentNodesToLeader.add(startingNode);
			communities.put(endingNode, newAdjacentNodesToLeader);
		}	
		return communities;
	}

	private static boolean containsNode(ArrayList<Node> members,
			Node startingNode) {
		// TODO Auto-generated method stub
		Iterator<Node> memIterator = members.iterator();
		while(memIterator.hasNext()){
			if(startingNode.nValue == memIterator.next().nValue){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("null")
	private static List<Edge> returnTempList(List<Edge> tempList) {
		// TODO Auto-generated method stub
		int i = 0;
		List<Edge> usableTempList = new ArrayList<Edge>();
		ListIterator<Edge> litr = tempList.listIterator();
		while(litr.hasNext()){
			Edge e = litr.next();
			if(0 != e.getEdgeWeight()){
				usableTempList.add(e);
				i++;
			}
				
		}
		return usableTempList;
	}

	private static void InputGraphAsHashMap(Graph g1) {
		// TODO Auto-generated method stub
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		for(int i = 0 ;i < 6;i++){
			listOfNodes.add(new Node(i+1));
		}
		g1.fillAdjacencyList(listOfNodes.get(0), Arrays.asList(listOfNodes.get(1),listOfNodes.get(2),listOfNodes.get(3),listOfNodes.get(5)));
		g1.fillAdjacencyList(listOfNodes.get(1), Arrays.asList(listOfNodes.get(0)));
		g1.fillAdjacencyList(listOfNodes.get(2), Arrays.asList(listOfNodes.get(0)));
		g1.fillAdjacencyList(listOfNodes.get(3), Arrays.asList(listOfNodes.get(0),listOfNodes.get(4)));
		g1.fillAdjacencyList(listOfNodes.get(4), Arrays.asList(listOfNodes.get(3)));
		g1.fillAdjacencyList(listOfNodes.get(5), Arrays.asList(listOfNodes.get(0)));
	}
	private static void SolveGraphAsHashMap() {
		// TODO Auto-generated method stub
		Graph g1 = new Graph();
		int d = 2;
		InputGraphAsHashMap(g1);
		ArrayList<Node> leaders = new ArrayList<Node>();
		ArrayList<Node> members = new ArrayList<Node>();
		ArrayList<Node> orbiters = new ArrayList<Node>();
		Node tempNode;
		List<Node> tempList;
		List<Node> reducedTempList;
		for(Entry <Node,List<Node>> entry : g1.entrySet()){
			tempNode = entry.getKey();
			tempList = entry.getValue();
			if(tempList.size() <= d && (!leaders.contains(tempNode) || !members.contains(tempNode))){
				reducedTempList = subtractList(tempList,members);
				if(-1 != reducedTempList.get(reducedTempList.size()-1).returnNodeValue()){
					members.add(tempNode);
					leaders.add(tempList.get(0));
				} else {
					orbiters.add(tempNode);
					members.add(tempList.get(0));
				}
				
			}
		}
		Iterator<Node> mItr = members.iterator();
		Iterator<Node> lItr = leaders.iterator();
		Iterator<Node> oItr = orbiters.iterator();
		System.out.println("Members");
		while(mItr.hasNext()){
			System.out.println(mItr.next().returnNodeValue());
		}
		System.out.println("Leaders");
		while(lItr.hasNext()){
			System.out.println(lItr.next().returnNodeValue());
		}
		System.out.println("Orbiters");
		while(oItr.hasNext()){
			System.out.println(oItr.next().returnNodeValue());
		}
	}

}
