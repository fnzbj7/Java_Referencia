package grid;

import grid.Grid;
import position.Position;

public abstract class AbstractGrid <A> implements Grid<A>{
	public final int rows, cols;
	
	public AbstractGrid(int row, int col){
		rows = row;
		cols = col;
	}
	
	public boolean isValid(Position p){
		return p.h >=0 && p.h < cols && p.v >=0 && p.v < rows;
	}
	

}