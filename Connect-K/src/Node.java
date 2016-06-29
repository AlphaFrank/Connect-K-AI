import connectK.BoardModel;

import java.awt.Point;
import java.util.*;


public class Node {
	
	private BoardModel board;
	private byte player;
	private byte thePlayer;

	
	public Node(BoardModel b, byte p, byte tp){
		board = b;
		player = p;
		thePlayer = tp;
	}
	
	
	public BoardModel getBoard(){
		return board;
	}
	
	
	

	
	public double getHeuristic(){
		if (board.winner() == opposite(thePlayer)){
			return  Double.NEGATIVE_INFINITY;
		}
		else if (board.winner() == thePlayer){
			return Double.POSITIVE_INFINITY;
		}
		else if (board.winner() == 0){
			return 0;
		}
		else{		
			
			double answer = 0;
			
			int danger = countThreaten(board, board.getkLength()-2, opposite(thePlayer));
			int chance = countThreaten(board, board.getkLength()-2, thePlayer);
						
//			if (danger >= 1 || chance >= 1){
//				System.out.println("Board:");
//				System.out.print(board);
//				System.out.print("chance: ");
//				System.out.println(chance);
//				System.out.print("Danger: ");
//				System.out.println(danger);
//			}
			
			
			
			
			
			if (chance >= 2){
//				System.out.println("Board:");
//				System.out.print(board);
//				System.out.print("chance: ");
//				System.out.println(chance);
//				System.out.print("Danger: ");
//				System.out.println(danger);
				return Double.POSITIVE_INFINITY;
			}
			else if (danger >= 2){
//				System.out.println("Board:");
//				System.out.print(board);
//				System.out.print("chance: ");
//				System.out.println(chance);
//				System.out.print("Danger: ");
//				System.out.println(danger);
				return  Double.NEGATIVE_INFINITY;
			}
			else if (chance > danger)
				answer += 100;
			else if (chance < danger)
				answer -= 100;
				
			
			
			
			for (int i=0; i<board.getWidth(); i++)
				for (int j=0; j<board.getHeight(); j++)
					if (board.getSpace(i,j) == thePlayer)
						answer += neighbour(board, i, j);
			
			
			return answer;
			
		}
		

	}
	
	
	
	
	
	
	public Vector<Node> getChildren(){
		Vector<Node> children = new Vector<Node>();
		
		if (board.gravityEnabled() == false){
			for (int i=0; i<board.getWidth(); i++)
				for (int j=0; j<board.getHeight(); j++)
					if (board.getSpace(i, j) == 0){
						Point newPoint = new Point(i,j);
						BoardModel newBoard = board.placePiece(newPoint, player);
						children.addElement(new Node(newBoard, opposite(player), thePlayer));
					}
		} else {
			for (int i=0; i<board.getWidth(); i++)
				for (int j=0; j<board.getHeight(); j++)
					if (board.getSpace(i, j) == 0){
						Point newPoint = new Point(i,j);
						BoardModel newBoard = board.placePiece(newPoint, player);
						children.addElement(new Node(newBoard, opposite(player), thePlayer));
						break;
					}
						
			
		}
		
		return children;
	}

	
	public boolean isTerminal(){
		return (board.winner() != -1);
	}
	
	
	
	
	
	
	
	
	public String toString(){
		return board.toString();
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
	private byte opposite(byte p){
		if (p == 1){
			return 2;
		} else {
			return 1;
		}
	}
	
	private int countThreaten(BoardModel b, int k, byte p){
		int result = 0;
		
		for (int i=0; i<b.getWidth(); i++)
			for (int j=0; j<b.getHeight(); j++)
				if (b.getSpace(i,j) == p)
					result += inRow(b,k,i,j,p); 
		
		return result;
	}
	
	
	
	private int inRow(BoardModel b, int k, int w, int h, byte p){
		
		int wb = b.getWidth();
		int hb = b.getHeight();
		
		int result = 0;
		boolean skip = false;
		
//		// left
//		if (w >= k){
//			for (int i=1; i<k; i++){
//				if (b.getSpace(w-i, h) != p){
//					skip = true;
//					break;
//				}
//			}
//			if (skip == true){
//				skip = false;
//			}
//			else{
//				if (w <= wb-2){
//					if (b.getSpace(w-k, h)!=opposite(p) && b.getSpace(w+1, h)!=opposite(p)){
//						result++;
//						//System.out.println("left");
//					}
//				}
//			}
//		}	
		
		
		
		// right
		if (w <= wb-k-1){
			for (int i=1; i<k; i++){
				if (b.getSpace(w+i, h) != p){
					skip = true;
					break;
				}
			}
			if (skip == true){
				skip = false;
			}
			else{
				if (w >= 1){
					if (b.getSpace(w-1, h)!=opposite(p) && b.getSpace(w+k, h)!=opposite(p)){
						result++;
						//System.out.println("right");
					}
				}
			}
		}	
		
		
//		// bottom
//		if (h >= k){
//			for (int i=1; i<k; i++){
//				if (b.getSpace(w, h-i) != p){
//					skip = true;
//					break;
//				}
//			}
//			if (skip == true){
//				skip = false;
//			}
//			else{
//				if (h <= hb-2){
//					if (b.getSpace(w, h-k)!=opposite(p) && b.getSpace(w, h+1)!=opposite(p)){
//						result++;
//						//System.out.println("bottom");
//					}
//				}
//			}
//		}	
		
		
		// up
		if (h <= hb-k-1){
			for (int i=1; i<k; i++){
				if (b.getSpace(w, h+i) != p){
					skip = true;
					break;
				}
			}
			if (skip == true){
				skip = false;
			}
			else{
				if (h >= 1){
					if (b.getSpace(w, h-1)!=opposite(p) && b.getSpace(w, h+k)!=opposite(p)){
						result++;
						//System.out.println("up");
					}
				}
			}
		}	
		
//		// upper left
//		if (w >= k && h <= hb-k-1){
//			for (int i=1; i<k; i++){
//				if (b.getSpace(w-i, h+i) != p){
//					skip = true;
//					break;
//				}
//			}
//			if (skip == true){
//				skip = false;
//			}
//			else{
//				if (w <= wb-2 && h >= 1){
//					if (b.getSpace(w+1, h-1)!=opposite(p) && b.getSpace(w-k, h+k)!=opposite(p)){
//						result++;
//						//System.out.println("upper left");
//					}
//				}
//			
//			}
//		}
		
		
		// upper right
		if (w <= wb-k-1 && h <= hb-k-1){
			for (int i=1; i<k; i++){
				if (b.getSpace(w+i, h+i) != p){
					skip = true;
					break;
				}
			}
			if (skip == true){
				skip = false;
			}
			else{
				if (w >= 1 && h >=1){
					if (b.getSpace(w-1, h-1)!=opposite(p) && b.getSpace(w+k, h+k)!=opposite(p)){
						result++;
						//System.out.println("upper right");
					}
				}
			
			}
		}
		

//		// bottom left
//		if (w >= k && h >= k){
//			for (int i=1; i<k; i++){
//				if (b.getSpace(w-i, h-i) != p){
//					skip = true;
//					break;
//				}
//			}
//			if (skip == true){
//				skip = false;
//			}
//			else{
//				if (w <= wb-2 && h <= hb-2){
//					if (b.getSpace(w+1, h+1)!=opposite(p) && b.getSpace(w-k, h-k)!=opposite(p)){
//						result++;
//						//System.out.println("bottom left");
//					}
//				}
//			
//			}
//		}

		
		// bottom right
		if (w <= wb-k-1 && h >= k){
			for (int i=1; i<k; i++){
				if (b.getSpace(w+i, h-i) != p){
					skip = true;
					break;
				}
			}
			if (skip == true){
				skip = false;
			}
			else{
				if (w >= 1 && h <= hb-2){
					if (b.getSpace(w-1, h+1)!=opposite(p) && b.getSpace(w+k, h-k)!=opposite(p)){
						result++;
						//System.out.println("bottom right");
					}
				}
			
			}
		}
		
	
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	private double neighbour(BoardModel b, int w, int h){
		double answer = 0;
		
		int wb = b.getWidth();
		int hb = b.getHeight();
		
		// left
		if (w >= 1)
			if (b.getSpace(w-1,h) == opposite(b.getSpace(w,h)))
				answer++;
		
		// right
		if (w <= wb-2)
			if (b.getSpace(w+1,h) == opposite(b.getSpace(w,h)))
				answer++;
	
		// bottom
		if (h >= 1)
			if (b.getSpace(w,h-1) == opposite(b.getSpace(w,h)))
				answer++;
		
		// up
		if (h <= hb-2)
			if (b.getSpace(w,h+1) == opposite(b.getSpace(w,h)))
				answer++;
		
		// upper left
		if (w >= 1 && h <= hb-2)
			if (b.getSpace(w-1, h+1) == opposite(b.getSpace(w,h)))
				answer++;
		
		// upper right
		if (w <= wb-2 && h <= hb-2)
			if (b.getSpace(w+1, h+1) == opposite(b.getSpace(w,h)))
				answer++;
		
		// bottom left
		if (w >= 1 && h >= 1)
			if (b.getSpace(w-1, h-1) == opposite(b.getSpace(w,h)))
				answer++;
		
		// bottom right
		if (w <= wb-2 && h >= 1)
			if (b.getSpace(w+1, h-1) == opposite(b.getSpace(w,h)))
				answer++;
		
		
		return answer;
	}
	
	
	
}