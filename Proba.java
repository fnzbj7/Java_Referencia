import position.Direction;
import position.Position;
import grid.AbstractGrid;
import grid.DenseGrid;
import grid.SparseGrid;
import grid.Grid;
import draughts.InvalidStepException;
import draughts.Piece;
import draughts.Man;
import draughts.King;


public class Proba{

	public static void main(String[] args){
		int[] tomb = new int[] {-10,10};
		System.out.println(new Position(1,2).next(Direction.UP_LEFT));
	}

}