package application;


//this can be cleaned up
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//game of playing tic tac toe! see img folder for screenshots
public class TicTacToe extends Application{

			char whoIsPlaying = 'O'; //O starts
			GridPane newGrid = new GridPane(); //sets up the gridPane we will be using
			int moves = 0; //total moves
			int[][] testArray = new int[3][3];//array to make sure you don't put the same image twice in the same place
			boolean game = true; //game variable to see if the game is still on...
			
			char whoWon = 'e'; //this will be used to print who won
			
			//labels to be used later
			Label congrats = new Label("Congratulations!\n\n"); //once someone wins, this is displayed (even if it's a draw)
			Label whosTurn = new Label("It is the turn of " + whoIsPlaying); //keeps track of who's turn it is
		    Label startOfGame = new Label(whoIsPlaying + " starts the game"); //this is only used when starting new games
		    
		    //button for restarting the game
			Button restart = new Button("Game Over!\nClick here to restart");
			
			//CHECK variable, not necessary. it's just used for troubleshooting
			int counterX = 0;
			
			
	@Override //set the stage
	public void start(Stage primaryStage) {
	
		
		//set the stage looks
		newGrid.setPadding(new Insets(5,5,5,5));
		newGrid.setVgap(5); //vertical gap in pixels
	    newGrid.setHgap(5);//horizontal gap in pixels
	  
	  
	    //double array set at the beginning in order to display area for gridPane and game
	    for (int x = 0; x < 3; x++) {
	    	for(int j = 0; j < 3; j++)
	    	{
	    		//put an empty img on all the usable squares
	    		ImageView imageToSet = new ImageView(new Image("file:src/resources/e.jpg"));
				imageToSet.setFitHeight(100); //set the size to be 100 x 100
			 	imageToSet.setFitWidth(100); 
			 	testArray[x][j] = 0; //this fills another array to 0. this will be used to check who wins
	    		newGrid.add(imageToSet, x, j); //puts the image of empty into the grid for the game
	    		
	    		
	    	}
	    	
	    }
	   
	 
	    //this sets the outline of the grid to visible if you need it. Looks better without it
	    //newGrid.setStyle("-fx-grid-lines-visible: true");
	    
	    //adds the start of game label to the game...so we know who starts!
	    newGrid.add(startOfGame, 3, 0);
	    

	    //FILTER Event to catch mouse pressed
	    newGrid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent mouseEvent) { 
	    		   try {
	    		  //get the location of the click
	    		  Node source = (Node)mouseEvent.getTarget() ;
	    	      
	    		  //get the col & row index of the click
	    		  Integer colIndex = GridPane.getColumnIndex(source);
	    	      Integer rowIndex = GridPane.getRowIndex(source);
	    	      
	    	      int col = colIndex.intValue(); //I could have used these later on but just used them for my if statement... 
	    	      int row = rowIndex.intValue(); //...to make sure we only care about clicks within my limits 3x3
	    	      if( col < 3 && row < 3 && (game))
	    	      {
	    	    	  	    	     
	    	      //if the colIndex & the rowIndex don't exist or can be found
	    	      if (colIndex == null || rowIndex == null) {
	
	    	    	  System.out.println("not valid click"); //used for troubleshooting
	    	    	  
	    	      } 
	    	      
	    	      //else if the col & row ARE FOUND....
	    	      else {
	    	    	  //but first check to see IF there are any moves left...max is 9 (3x3)
	    	    	  
	    	    	  if(moves < 9)
	    	    	  {
	    	    		  //check to see who is playing AND if spot hasn't been clicked yet AND if game is still going
		    	    	  if(whoIsPlaying == 'O' && testArray[colIndex.intValue()][rowIndex.intValue()]== 0 && game == true)
		    	    		{
		    	    		    //add the new image based on whoIsPlaying
		    	       			newGrid.add(checkPlay(), colIndex.intValue(), rowIndex.intValue()); //return image
		    	   
		    	       			//mark the new position as 'taken' by 'O' on the testArray
		    	       			testArray[colIndex.intValue()][rowIndex.intValue()] = 1;//1 is O
		    	       			
		    	    			whoIsPlaying='X'; //change turn to the next player
		    	    			moves +=1; //add a move to moves to keep track
		    	    		}
		    	    	  
	    	    		  //check to see who is playing AND if spot hasn't been clicked yet AND if game is still going
		    	    	  else if(whoIsPlaying == 'X' && testArray[colIndex.intValue()][rowIndex.intValue()]== 0 && game == true) 
		    	    		{	
		    	    		    //add the new image based on whoIsPlaying
		    	    		    newGrid.add(checkPlay(), colIndex.intValue(), rowIndex.intValue());
		    	    		    
		    	    		    //mark the new position as 'taken' by 'O' on the testArray
		    	    		    testArray[colIndex.intValue()][rowIndex.intValue()] = 2; //2 is X
		    	    			
		    	    		    whoIsPlaying = 'O';//change turn to the next player
		    	    			moves +=1;//add a move to moves to keep track
		    	    		}
		    	    	  
		    	    	 
		    	    	  //run game() to see if the game is OVER or not...AFTER user has played a moved...
		    	    	  //NOTE: game is less than 9 moves still or else it'd go to the next check (else)
		    	    	  game = game();
		    	    	  
		    	    	  //remove old label of who starts
		    	    	  newGrid.getChildren().removeAll(startOfGame);
		    	    	  
		    	    	  
		    	    	 //Keep track of Who's turn it is and display that
		    	    	 whosTurn.setText("It's the turn of " + whoIsPlaying);
			    	     whosTurn.setTextFill(Color.web("#0076a3"));
		    	    	 //whosTurn.getText();
			    	     
			    	     //#FIXME
			    	     //So I couldn't update the whosTurn's label :( so I cheated and decided to just catch the error
			    	     //if someone improves it or I, I will update
			    	     try {
		    	    	 newGrid.add(whosTurn, 3,0);
			    	     	} catch(IllegalArgumentException e)
			    	     {
			    	     		//System.out.println("Adding a second child with same name");
			    	     }
			  			    	      
		    	    	  //Check to see if the game has returned to false...in less than 9 moves
			    	      //If it is less than 9 and game returned as 'false' meaning game over
		    	    	  if(!game) {
		    	    		
		    	    		  //remove whosTurn label
		    	    		  newGrid.getChildren().removeAll(whosTurn);
		    	    		  
		    	    		  //ADD restart (button to ask for a restart) and take up ALL three cols
		    	    		  //..........(node,  col, row, colspan, row span
		    	    		  newGrid.add(restart, 0,   1,  3,           1);
		    	    		  
		    	    		  //make sure the new node can GROW as large as it needs to
		    	    		  restart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			    				 	
		    	    	  }
		    	    	  
		    	    	  //THIS check is important because if 9 moves have passed, THEN, it will get to check the game again
		    	    	  //and come up with Draw.
		    	    	  //#FIXME there is a better way, but this is what I have so far :)
		    	    	  if(moves == 9) {
		    	    		
		    	    		  //game() should return false already here because that last click had who won, we are just going to check if it's a draw
		    	    		  //if it comes back TRUE (meaning no one has won) then it's a draw
		    	    		  if(game)
		    	    		  {
			    	    		  //remove restart button from previous run  
			    	    		  newGrid.getChildren().removeAll(restart);
			    	    		  
			    	    		  returnGame('n'); //return the draw
			    	    		  
			    	    		  //ADD restart (button to ask for a restart) and take up ALL three cols
			    	    		  //..........(node,  col, row, colspan, row span
			    	    		  newGrid.add(restart, 0,   1,  3,           1);
			    	    		  
			    	    		  //make sure the new node can GROW as large as it needs to
			    	    		  restart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			    	    		
			    	    		  //set the game to over
			    	    		  game = false;
		    	    		  }//if
							}//if
		    	    		  
		    	    		  
	    	    	  }
		    	    		  
		    	    	  }//else
		    	    	  
		    	    	  ///THESE are print statements for checks & troubleshooting...gives col value and row value when mouse is pressed
		    	    	 // System.out.printf("valued clicked [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
		    	          //System.out.printf("player's new turn " + whoIsPlaying + "\n"); //keeps track of whoIsPlaying
		    	       // System.out.printf("if moves " + moves + "\n"); //keeps tracks of mouse
			    	      	
		    	        
	      
	    	      }//if col & row are less than 3...which means if we clicked on the game area
	    	
	        }//try
  	      
  	      catch(NullPointerException e) {
  	    	 //null pointer error caught
  	    	  // System.out.println("We caught an error");
  	    	 //System.out.println(e);
  	      }
	    	}
	    	
	    });	
	    
	    //event handler for button restart()
	       restart.setOnAction(new EventHandler<ActionEvent>() {
	    	   @Override
	    	   public void handle(ActionEvent event) {
	    	   try {
	    		   restartGame(); //calls restartGame();
	    		   
	    	   } catch (NumberFormatException e) {
	    		   System.out.println(e);
	    	   }
	    	   }
	    	   });
	    
	 
		    //Sets the new scene and primary stage & newGrid to scene
		    Scene scene2 = new Scene(newGrid, 450, 350);
		    primaryStage.setTitle("Tic Tac Toe");
		    primaryStage.setScene(scene2);
		    primaryStage.show();
		}

//restart the game function()
private void restartGame() {
	
	//loop through array and set 3x3 board again
	  for (int x = 0; x < 3; x++) {
	    	for(int j = 0; j < 3; j++)
	    	{
	    		
	    		ImageView imageToSet = new ImageView(new Image("file:src/resources/e.jpg"));
				imageToSet.setFitHeight(100);
			 	imageToSet.setFitWidth(100);
			 	testArray[x][j] = 0;
	    		newGrid.add(imageToSet, x, j);
	    		
	    		
	    	}
	    	
	    }
	  
	  //remove children & set variables back to start of a new game
	  newGrid.getChildren().removeAll(restart);
	  newGrid.getChildren().removeAll(congrats);
	  newGrid.getChildren().removeAll(startOfGame);
	  newGrid.getChildren().remove(whosTurn);
	  newGrid.add(startOfGame, 3,0);
	  whoIsPlaying = 'O'; //sets player back to O
	  moves = 0; //clears moves
	  game = true; //game is alive again
	  whoWon = 'e'; //no one has won
	  
}

//checks if someone has won
private boolean game() {
	//returns FALSE if the game is over or if no one won -- game still over
	//returns TRUE if the game is still going...

	if(game)
	{//rows check #FIXME there's probably a better way for this :d
		if((testArray[0][0] == 1 && testArray[1][0] ==1 && testArray[2][0]==1) || 
				(testArray[0][1] == 1 && testArray[1][1] ==1 && testArray[2][1]==1) || 
				(testArray[0][2] == 1 && testArray[1][2] ==1 && testArray[2][2]==1)   )
		{
			returnGame('O');
			return false; //game over
		}

		else if((testArray[0][0] == 2 && testArray[1][0] ==2 && testArray[2][0]==2) ||
				(testArray[0][1] == 2 && testArray[1][1] ==2 && testArray[2][1]==2) ||
				(testArray[0][2] == 2 && testArray[1][2] ==2 && testArray[2][2]==2) )

		{
			returnGame('X');
			return false; //game over
		}

		//cols
		else if((testArray[0][0] == 1 && testArray[0][1] ==1 && testArray[0][2]==1) ||
				(testArray[1][0] == 1 && testArray[1][1] ==1 && testArray[1][2]==1) ||
				(testArray[2][0] == 1 && testArray[2][1] ==1 && testArray[2][2]==1) )

		{
			returnGame('O');
			return false;
		}

		else if((testArray[0][0] == 2 && testArray[0][1] ==2 && testArray[0][2]==2) ||
				(testArray[1][0] == 2 && testArray[1][1] ==2 && testArray[1][2]==2) ||
				(testArray[2][0] == 2 && testArray[2][1] ==2 && testArray[2][2]==2) )

		{
			returnGame('X');
			return false;
		}


		//diagonals
		else if((testArray[0][0] == 1 && testArray[1][1] ==1 && testArray[2][2]==1) ||
				(testArray[0][2] == 1 && testArray[1][1] ==1 && testArray[2][0]==1) )
		{
			returnGame('O');
			return false;

		}

		else if((testArray[0][0] == 2 && testArray[1][1] ==2 && testArray[2][2]==2) || 
				(testArray[0][2] == 2 && testArray[1][1] ==2 && testArray[2][0]==2) )
		{
			returnGame('X');
			return false;
		}

		//no one has won
		else {
			return true;
		}


	}

	//if game = false, then the game is over; returns false for draw
	return false;



}

//returns the Who has won or draw messages
private void returnGame(char who) {

	//remove old child
	newGrid.getChildren().removeAll(congrats);
	//System.out.println(counterX + " " + who); //for checks
	
	if(who == 'O' || who == 'X')
	{
	//System.out.println(who + " has won. Game over"); //for checks
	game = false; //set the game to false if O or X have won already
	congrats.setText("Congratulations! " + who + " has won\n"); //update text

	}
	
	//if it's a draw
	else if(who == 'n'){
		
		System.out.println("WOOPS! It's a draw. Game over");//
		game = false;
		
		congrats.setText("Whoops! It's a draw. Game over.\n\n");
		

	}
	
	counterX += 1; //for us to keep track of games played
	congrats.setStyle("-fx-background-color:  #87ceeb" ); //set color of background of message displayed
	
	newGrid.add(congrats, 0,2,3,1); //add it to the grid but take up all three cols
	GridPane.setHalignment(congrats, HPos.CENTER); //center the label
	congrats.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //Make sure it expands
	congrats.setAlignment(Pos.CENTER);		//position text in the center
		
}
	
	

//RETURNS the image to display based on who's turn it is	
 private ImageView checkPlay(){
			
			if(whoIsPlaying == 'O')
			{
				//if O is playing set the right image for O
				ImageView imageToSet = new ImageView(new Image("file:src/resources/o.jpg"));
				imageToSet.setFitHeight(100); 
			 	imageToSet.setFitWidth(100);
				return imageToSet; //return imageToSet
			}
			
			else if(whoIsPlaying == 'X')
			{
				//return image for X
				ImageView imageToSet = new ImageView(new Image("file:src/resources/x.jpg"));
				imageToSet.setFitHeight(100);
			 	imageToSet.setFitWidth(100);
			 	
				return imageToSet;
			}
			
			//if neither player is playing return the default img if necessary
			else
				{
				ImageView imageToSet = new ImageView(new Image("file:src/resources/e.jpg"));
		
				imageToSet.setFitHeight(100);
			 	imageToSet.setFitWidth(100);
				return imageToSet;
				}
			
			}
 


public static void main(String[] args) {
	launch(args);
}
}

