package draughts;

import position.Position;
import java.lang.Exception;

public class InvalidStepException extends Exception{
	public InvalidStepException(Position p1, Position p2){
		super("Invalid step from: "+p1+" to: "+p2);
	}


}