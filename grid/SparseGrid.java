package grid;

import grid.AbstractGrid;
import position.Position;

import java.util.Map;
import java.util.HashMap;


public class SparseGrid<A> extends AbstractGrid<A>{
	private Map<Position, A> map;
	
	public SparseGrid(int row, int col){
		super(row,col);
		map = new HashMap<>();
	}
	
	
	public void set(Position p, A a){
		if(this.isValid(p)){
			map.put(p,a);
		} else
		throw new IndexOutOfBoundsException("Invalid position: "+p);
		
	}
	
	public A get(Position p){
		if(this.isValid(p)){
			return map.get(p);
		}
		throw new IndexOutOfBoundsException("Invalid position: "+p);
	}
	

}