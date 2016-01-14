package grid;

import grid.AbstractGrid;
import position.Position;
import java.util.*;

public class DenseGrid<A> extends AbstractGrid<A>{
	private ArrayList<ArrayList<A>> grid;
	
	public DenseGrid(int row, int col){
		super(row,col);
		grid = new ArrayList<>();
		
		for(int i = 0; i<row; i++){
			ArrayList<A> lista = new ArrayList<>();
			for(int j=0; j<col; j++){
				lista.add(null);
			}
			grid.add(lista);
		}
		
	}
	
	
	public A get(Position p){
		
		if(this.isValid(p)){
			return grid.get(p.v).get(p.h);
		}
		throw new IndexOutOfBoundsException("Invalid position: " + p);
	}
	
	public void set(Position p, A a){
		if(this.isValid(p)){
			grid.get(p.v).set(p.h, a);
		}else
		throw new IndexOutOfBoundsException("Invalid position: " + p);
	}
	
	
}