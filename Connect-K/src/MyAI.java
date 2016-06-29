import connectK.CKPlayer;
import connectK.BoardModel;

import java.awt.Point;
import java.io.*;
import java.util.*;




public class MyAI extends CKPlayer {

	public MyAI(byte player, BoardModel state) {
		super(player, state);
		teamName = "MyAI";
	}
	
	

	@Override
	public Point getMove(BoardModel state) {
		
		GameTree gt;
		
		if (state.gravityEnabled()){
			gt = new GameTree(state, player, player, 7);
		} else {
			gt = new GameTree(state, player, player, 4);
		}
		
		return gt.bestMove();

	}

	@Override
	public Point getMove(BoardModel state, int deadline) {
		return getMove(state);
	}
	
}	

	
	
	
	
	
	
	
	
	
	
	
