package draughts;

import position.Position;
import position.Direction;
import draughts.Piece;
import grid.Grid;


public class King extends Piece{

	public King(Position p, Color c, Grid<Piece> g){
		super(p,c,g);
	}
	
	public boolean canStepTo(Position p){
		if(!g.isValid(p)) return false;
		
		if(!Position.isDiagonal(this.p,p)) return false;
		if( this.p.equals(p)) return false;
		
		int[] tavXY = this.p.distance(p);
		int tav = Math.abs(tavXY[0])+Math.abs(tavXY[1]);
		if(tav!=2 && tav!=4) return false;
		Direction d = Direction.fromDistance(tavXY);
		Position pNext = this.p.next(d); 
		Piece p1 = g.get(pNext);
		Piece p2 = null;
		if(tav==4) p2 = g.get(pNext.next(d));
		if(tav==2 && p1!=null) return false;
		if(tav==4){
			if(p2 != null) return false;
			if(p1 == null || p1.c == this.c) return false;
			
		}
		
		return true;
	}
	
	public String toString(){
		return c.toString().charAt(0)+"K";
	}

}


/* postairon8 17314389292WeR */
