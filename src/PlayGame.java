import java.lang.reflect.GenericArrayType;
import java.util.*;

public class PlayGame {

  public static void main(String[] args){
    GameHelperClass.clearScreen();
    GameConfiguration gameConfiguration = GameHelperClass.initializeGame();

    GameHelperClass.printConfiguration(gameConfiguration);

    /** Game stack maintains the history of moves*/
    Stack<GameConfiguration> gameStack = new Stack<GameConfiguration>();

    gameStack.push(gameConfiguration);

    GameHelperClass.printRules();

    while (true) {

      Scanner input = new Scanner(System.in);
      String inputCharacter = input.next();
      if(!GameHelperClass.validateInputs(inputCharacter)){
        System.out.println("Invalid Input");
        continue;
      }
      if("q".equals(inputCharacter)){
        break;
      }
      if("sol".equals(inputCharacter)){
        GameHelperClass.printSolution(gameStack);
        /** Restting the game for player to try again*/
        inputCharacter = "r";
      }
      gameStack = GameHelperClass.runGame(gameStack,inputCharacter);
      if(GameHelperClass.checkWinGame(gameStack.peek())){
        break;
      }
    }
    GameHelperClass.checkLoseGame(gameStack.peek());
  }
}
