package position;

import position.Direction;


public class Position{
	public final int h, v;
	
	public Position(int x, int y){
		h = x;
		v = y;
	}
	
	public Position next(Direction d){
		switch(d){
			case UP_LEFT:
				return new Position(h-1,v+1);
			case UP_RIGHT:
				return new Position(h+1,v+1);
			case DOWN_LEFT:
				return new Position(h-1,v-1);
			case DOWN_RIGHT:
				return new Position(h+1,v-1);
		}
		return null;
	}
	
	public int[] distance(Position p){
		return new int[] {p.h-h, p.v-v};
	}
	
	public static boolean isDiagonal(Position p1, Position p2){
		int[] tav = p1.distance(p2);
		if(tav[0]==tav[1] || tav[0]+tav[1]==0) return true;
		return false;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Position){
			Position p = (Position)o;
			return p.h==h && p.v==v;
		} else return false;
		
	}
	
	@Override
	public int hashCode(){
		return 100 * v + h;
	}
	
	@Override
	public String toString(){
		return "("+h+","+v+")";
	}

}