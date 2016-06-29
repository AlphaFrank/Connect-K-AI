import connectK.BoardModel;

import java.awt.Point;
import java.util.*;


public class GameTree {
	
	private Node root;
	private int depth;
	
	public GameTree(BoardModel b, byte p, byte tp, int d){
		root = new Node(b,p,tp);
		depth = d;
	}
	
	public Point bestMove(){
		if (root.getBoard().spacesLeft == (root.getBoard().getWidth()*root.getBoard().getHeight())){
			return new Point(root.getBoard().getWidth()/2, root.getBoard().getHeight()/2);
		}
		
		
		double value = alphabeta(root, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		System.out.println(value);
		Vector<Node> children = root.getChildren();
		Node target = children.elementAt(0);
		
		System.out.print("[");
		for (int i=0; i<children.size(); i++){
			double targetValue = alphabeta(children.elementAt(i), depth-1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
			System.out.print(targetValue);
			System.out.print(",");
			if (value == targetValue){
				System.out.println("]");
				target = children.elementAt(i);
				System.out.println("This must happen.");
				break;
			}
		}
		
		return difference(root.getBoard(), target.getBoard());
	}
	
	
	
	
	
	
	private double alphabeta(Node n, int d, double a, double b, boolean m){
		if (d == 0 || n.isTerminal()){
			return n.getHeuristic();
		}
		if (m == true){
			double v = Double.NEGATIVE_INFINITY;
			Vector<Node> children = n.getChildren();
			for (int i=0; i<children.size(); i++){
				v = Math.max(v, alphabeta(children.elementAt(i), d-1, a, b, false));
				a = Math.max(a, v);
				if (b <= a)
					break;
			}
			return v;
				
		} else {
			double v = Double.POSITIVE_INFINITY;
			Vector<Node> children = n.getChildren();
			for (int i=0; i<children.size(); i++){
				v = Math.min(v, alphabeta(children.elementAt(i), d-1, a, b, true));
				b = Math.min(b, v);
				if (b <= a)
					break;
			}
			return v;
		}
	}
	
	
	
	
	

	
	private Point difference(BoardModel b1, BoardModel b2){
		for (int i=0; i<b1.getWidth(); i++)
			for (int j=0; j<b1.getHeight(); j++)
				if (b1.getSpace(i,j) != b2.getSpace(i,j))
					return new Point(i,j);
		return null;
	}
	

	


}


