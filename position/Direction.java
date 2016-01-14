package position;


public enum Direction{
	UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;
	
	public static Direction fromDistance(int[] tomb){
		if(tomb[0]>0){
			if(tomb[1]>0){
				return UP_RIGHT;
			}else{
				return DOWN_RIGHT;
			}
		}
		else{
			if(tomb[1]>0){
				return UP_LEFT;
			}else{
				return DOWN_LEFT;
			}
		}
	}

}