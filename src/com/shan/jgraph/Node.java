package com.shan.jgraph;

import java.util.ArrayList;
import java.util.List;

public class Node {

	int nValue;
	int playJerNum;
	Node(int num,int playerJerseyNum){
		this.nValue = num;
		this.playJerNum = playerJerseyNum;
	}
	
	Node(int num){
		this.nValue = num;
	}
	
	int returnNodeValue(){
		return this.nValue;
	}
	
	int playerJerseyNum(){
		return this.playJerNum;
	}
	
	
}
