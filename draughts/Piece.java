package draughts;

import position.Position;
import position.Direction;
import grid.Grid;
import draughts.InvalidStepException;

import java.io.*;
import java.util.*;

public abstract class Piece{
	public enum Color{
		WHITE, BLACK;
	}

	protected Position p;
	protected final Color c;
	protected final Grid<Piece> g;
	
	public Piece(Position p,Color c,Grid<Piece> g){
		this.p = p;
		this.c = c;
		this.g = g;
	}
	
	public abstract boolean canStepTo(Position p);
	
	public void stepTo(Position p) throws InvalidStepException{
		if(canStepTo(p)){
			int[] tav = this.p.distance(p);
			Direction d = Direction.fromDistance(tav);
			Position pNew;
			do{
			pNew = this.p.next(d);
			g.set(this.p,null);
			g.set(pNew,this);
			this.p = pNew;
			}while(!pNew.equals(p));
		}else
		throw new InvalidStepException(this.p,p);
	}
	
	public abstract String toString();

}