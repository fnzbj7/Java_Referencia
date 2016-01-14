package grid;

import position.Position;

public interface Grid<A>{
	
	public boolean isValid(Position p);
	public A get(Position p);
	public void set(Position p, A a);

}