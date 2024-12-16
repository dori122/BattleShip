/*
 * Author: Dorian Lleshi
 * Student at Aegean College and Essex University
 * 
 * Description:
 * This program implements a simple command-line version of the Battleship game in Java. 
 * Players will take turns guessing coordinates on a 7x7 grid to try and hit ships. 
 * The game provides feedback on whether the shot hits or misses and gives hints 
 * on how many ships are in the same row or column as the shot.
 * 
 * Features:
 * - Randomized ship placement
 * - User input validation
 * - Hint system
 * - Game over message showing the number of attempts
 * 
 * Date: December 2024 - January 2025
 */



package battleship;
import java.util.Random;
import java.util.Scanner;

public class BattleShip {
	
	static int [][] board = new int[7][7];//game board
	static int [][] ships;//hidden ships
	static int  shipsDown = 0;//ships that have been hit
	//method to initialize the game board
	static void initBoard()
	{
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				board[i][j] = -1;
	}
	//method to display the current state of the board to the user
	static void showBoard()
	{
		System.out.println("  1  2  3  4  5  6  7");//print the collum number 
		
		for(int i = 0; i < 7; i++ )//print row number 
		{
			System.out.println(i+1);
			for (int j = 0; j < 7; j++)
				//print the status of each cell on the board
				switch (board[i][j])
				{
					case 1 -> {System.out.print("  X");}// for hit ship
					case 0 -> {System.out.print("  *");}//for missed shot
					default -> {System.out.print("  ~");} //for water
				}
			System.out.println();
		}
	}
	//method to randomly place ships
	static void initShips()
	{
		ships = new int[6][2];
		Random random = new Random();
		int shipIndex = 0;
		//place two ships of size 2 
		while (shipIndex < 4)//loop to place 4 ships 
		{
			int row = random.nextInt(7);
			int col = random.nextInt(7);
			boolean horizontal = random.nextBoolean();
			
			if (horizontal)//horizontal placement of the ship
			{   //checks if space is free
				if (col <6 && !shipIsOn(row, col) && !shipIsOn(row, col + 1))
				{
					ships[shipIndex][0] = row;
					ships[shipIndex][1] = col;
					shipIndex++;
					ships[shipIndex][0] = row;
					ships[shipIndex][1] = col + 1;
					shipIndex++;
					
					
				}
			}
			else//vertical placement of the ship
			{
				if (row <6 && !shipIsOn(row, col) && !shipIsOn(row + 1, col))
				{
					ships[shipIndex][0] = row;
					ships[shipIndex][1] = col;
					shipIndex++;
					ships[shipIndex][0] = row + 1;
					ships[shipIndex][1] = col;
					shipIndex++;
				}
			}
		}
		//place 2 ships of size 1 
		while (shipIndex < 6)
		{
			int row = random.nextInt(7);
			int col = random.nextInt(7);
			
			if (!shipIsOn(row, col))//check if space is free
			{
				ships[shipIndex][0] = row;
				ships[shipIndex][1] = col;
				shipIndex++;
			}
		}
	}
	
	//method to check if the ship is already placed at the specified position
	static boolean shipIsOn(int trow, int tcol)
	{
		for (int[] aShip : ships)
		{
			if (aShip[0] == trow && aShip[1] == tcol)
			{
				return true;//ship is at the position
			}
			
		}
		return false;// no ship at the position
	}
	//method to handle the shooting action and update the board 
	static void shootAndUpdate(int tRow, int tCol)
	{
		if (shipIsOn(tRow, tCol))//check if a ship is hit 
		{
			board[tRow] [tCol] = 1;//mark the spot with X 
			shipsDown++;
			System.out.println("a ship was hit");
		}
		else //if the shot missed
		{
			board[tRow][tCol] = 0;//mark the spot with '*' 
			System.out.println("shot missed");
		}
	} 
	//method to provide hints to the player on  how many ships are on the same row and collum 
	static void Hint(int trow, int tcol)
	{
		int shipsRow = 0, shipsCol = 0;
		//count how many ships are in the same row and collum
		for (int[] aShip : ships)
		{
			if (aShip[0] == trow)
				shipsRow++;
			if (aShip[1] == tcol)
				shipsCol++;
		}
		System.out.printf("row %d has %d ships. Collum %d has %d ships.\n",trow+1,shipsRow, tcol+1, shipsCol);
	}
	//main method where game starts
	public static void main(String[] args) 
	{
		initBoard();
		initShips();
		Scanner scanner = new Scanner(System.in);
		int attempts = 0;
		//main game loops: continue until all ships are hit 
		while (shipsDown < 4 )
		{
			showBoard();
			int row, col;
			
			//prompt the player for valid input 
			while (true)
			{
				System.out.print("enter row 1-7: ");
				row = scanner.nextInt() - 1;
				System.out.print("enter collum 1-7: ");
				col = scanner.nextInt() - 1;
				//checks if the input is valid 
				if (row >= 0 && row < 7 && col >= 0 && col <7 && board[row][col] == -1)
				{
					break;
				}
				else 
				{
					System.out.println("invalid input or already tried, try again");
				}
				
			}
			shootAndUpdate(row, col);//make the  shot and update the board 
			Hint(row, col);//provide a hint to the player 
			attempts++;//records the number of attempts of the user  
		}
		
		//GAME OVER!!!!!!!!!
		System.out.println("Bravo you sank the ships in "+ attempts+"attempts");
		scanner.close();
	}

}
