import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;



public class SearchEngine {

	Maze maze;
	
	public final static int LEFT = 0;
	public final static int RIGHT = 2;
	public final static int TOP = 3;
	public final static int BOTTOM = 1;
	
	public SearchEngine(Maze maze) {
		this.maze = maze;	
	}
	
	public Dimension[] getPossibleMoves(Dimension location)
	{
		int value = maze.getMaze()[location.width][location.height].getContents();
		
		Dimension tmpMoves[] = new Dimension[4];
		
		tmpMoves[0] = tmpMoves[1] = tmpMoves[2] = tmpMoves[3] = null;
		
		int num = 0;
		
		LinkedList<Dimension> obsts = maze.getObstacles();
		
		int x = location.height;
		int y = location.width;
		
		System.out.println("Binary: "+value);
		
		String binary = Maze.toBinary(value);

		String[] splitBinary = binary.split("");
		
		System.out.println("Binary: "+binary);
		
		
		if(splitBinary[LEFT].equals("0") && checkForObstacles(obsts, new Dimension(y, x-1)) )
		{
			tmpMoves[num++] = new Dimension(y, x-1);
		}
		
		if(splitBinary[RIGHT].equals("0") && checkForObstacles(obsts, new Dimension(y, x+1)))
		{
			tmpMoves[num++] = new Dimension(y, x+1);
		}
		
		if(splitBinary[TOP].equals("0") && checkForObstacles(obsts, new Dimension(y-1, x)))
		{
			tmpMoves[num++] = new Dimension(y-1, x);
		}
		
		if(splitBinary[BOTTOM].equals("0") && checkForObstacles(obsts, new Dimension(y+1, x)))
		{
			tmpMoves[num++] = new Dimension(y+1, x);
		}
		
		return tmpMoves;
		
	}
	
	
	public ArrayList <Case> getPossibleMoves2(Case location)
	{
		ArrayList <Case> tempMoves = new ArrayList <Case>();
		
		int value = location.getContents();
		
	
		
		//int num = 0;
		
		LinkedList<Dimension> obsts = maze.getObstacles();
		
		int x = location.getX();
		int y = location.getY();
		
		System.out.println("Binary: "+value);
		
		String binary = Maze.toBinary(value);

		String[] splitBinary = binary.split("");
		
		System.out.println("Binary: "+binary);
		
		
		if(splitBinary[LEFT].equals("0") && checkForObstacles(obsts, new Dimension(y, x-1)) )
		{
			tempMoves.add(maze.getMaze()[y][x-1]);
			//tmpMoves[num++] = new Dimension(y, x-1);
		}
		
		if(splitBinary[RIGHT].equals("0") && checkForObstacles(obsts, new Dimension(y, x+1)))
		{
			//tmpMoves[num++] = new Dimension(y, x+1);
			tempMoves.add(maze.getMaze()[y][x+1]);
		}
		
		if(splitBinary[TOP].equals("0") && checkForObstacles(obsts, new Dimension(y-1, x)))
		{
			//tmpMoves[num++] = new Dimension(y-1, x);
			tempMoves.add(maze.getMaze()[y-1][x]);
		}
		
		if(splitBinary[BOTTOM].equals("0") && checkForObstacles(obsts, new Dimension(y+1, x)))
		{
			//tmpMoves[num++] = new Dimension(y+1, x);
			tempMoves.add(maze.getMaze()[y+1][x]);
		}
		
		return tempMoves;
		
	}
	
	
	public boolean checkForObstacles(LinkedList<Dimension> obsts,Dimension location)
	{
		
		for( Dimension obst : obsts )
		{
			if( obst.equals(location) )
			{
				
				System.out.println("obstacle "+ obst);
				return false;
			}		
		}
		
		return true;
	}
	
}
