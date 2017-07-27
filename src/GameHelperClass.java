import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class GameHelperClass {

  public static void printRules(){
    System.out.println("Rules:");
    System.out.println("1) Raft can hold upto 2 people at a time");
    System.out.println("2) Only Father, Mother and Cop can drive the raft");
    System.out.println("3) Father cannot be on the same side as the daughters without the mother");
    System.out.println("4) Mother cannot be on the same side as the sons without the father");
    System.out.println("5) Thief cannot be on the same side as any family member without the cop\n");
  }

  public static void printConfiguration(GameConfiguration gameConfiguration){
    System.out.println("\n\nf -> Father | m -> Mother | d1 -> Daughter1 | d2 -> Daughter2 | s1 -> Son1 | s2 -> Son2 | c -> Cop | t -> Thief");
    System.out.println("u -> Undo | mr -> Move Raft | r -> Reset | sol -> Solution | q -> Quit!\n");

    /** Creating copy of items of current gameConfiguration*/
    int raft = gameConfiguration.getRaftSide();
    List<String> riverside1 = gameConfiguration.getRiverSide1();
    List<String> riverside2 = gameConfiguration.getRiverSide2();
    List<String> raftPeople= gameConfiguration.getRaftPeople();

    /** Adding spaces if raft has empty space*/
    for(int i = raftPeople.size() ; i<2;i++){
      raftPeople.add(" ");
    }
    printRiverSide(riverside1);
    System.out.println();
    printRiverBank(1);
    System.out.println();
    if(raft==1){
      printRaft(raftPeople);
    }
    for(int i= 0 ; i<4;i++){
      System.out.println();
    }
    System.out.println();
    if(raft==2){
      printRaft(raftPeople);
    }
    System.out.println();
    printRiverBank(2);
    System.out.println();
    printRiverSide(riverside2);
    System.out.println();
    System.out.println();
    System.out.println();

    /** Removing blank space from raft*/
    raftPeople.remove(" ");
    raftPeople.remove(" ");

  }

  private static void printRiverBank(int no){
    for(int i= 0 ; i<10;i++){
      System.out.print("_" + " ");
    }
    System.out.print(" RiverBank " + no);
  }

  private static void printRiverSide(List<String> riverSide){
    for(int i= 0 ; i<8;i++){
      if(riverSide.size()>i){
        System.out.print(riverSide.get(i) +" ");
      }
    }
  }

  private static void printRaft(List<String> raftPeople){
    int count = 0;
    for(int i = 0;i<5;i++){
      if(i>1){
        System.out.print("_" + " ");
      } else {
        System.out.print(" ");
      }
    }
    System.out.println();
    for(int i = 0;i<4;i++){
      if(i==1 && count<2){
        /** Handling cases when raft people have 2 characters, eg: s1*/
        if(raftPeople.get(count).length()==2) {
          System.out.print("|" + raftPeople.get(count++) + " ");
        }else {
          System.out.print("|" + raftPeople.get(count++) + "  ");
        }
      } else if(i==2 || i == 3 && count<2){
        /** Handling cases when raft people have 2 characters, eg: s1*/
        if(raftPeople.get(count).length()>1) {
          i++;
        }
          System.out.print(raftPeople.get(count++));
      }else {
        System.out.print(" ");
      }
    }
    System.out.print("|" + "-->Raft");
    System.out.println();
    System.out.print(" "+"|");
    for(int i = 0;i<4;i++){
      if(i==3) {
        System.out.print("|");
      }else if(i==2){
        System.out.print("_");
      } else {
        System.out.print("_" + " ");
      }
    }
  }

  public static boolean validateInputs(String a){
    if("q".equals(a) || "r".equals(a) || "u".equals(a) || "d1".equals(a) || "d2".equals(a)
        || "s1".equals(a) || "s2".equals(a) || "c".equals(a) || "t".equals(a) || "f".equals(a) || "m".equals(a) || "mr".equals(a)
        || "sol".equals(a)){
      return true;
    }
    return false;
  }

  public static GameConfiguration initializeGame(){
    GameConfiguration gameConfiguration = new GameConfiguration();
    List<String> riverSide1 =  new LinkedList<String>();
    riverSide1.add("f");
    riverSide1.add("m");
    riverSide1.add("d1");
    riverSide1.add("d2");
    riverSide1.add("s1");
    riverSide1.add("s2");
    riverSide1.add("c");
    riverSide1.add("t");
    gameConfiguration.setRiverSide1(riverSide1);
    return new GameConfiguration(gameConfiguration);
  }

  private static Stack<GameConfiguration> movePerson(Stack<GameConfiguration> gameStack,String inputChar){
    /** Creating copy of items of current gameConfiguration*/
    GameConfiguration gameConfiguration = new GameConfiguration(gameStack.peek());
    List<String> riverSide1 = new LinkedList<String>(gameConfiguration.getRiverSide1());
    List<String> riverSide2 = new LinkedList<String>(gameConfiguration.getRiverSide2());
    List<String> raft = new LinkedList<String>(gameConfiguration.getRaftPeople());

    if(gameStack.peek().getRaftPeople().size()>1){
      System.out.println("Invalid Move");
      System.out.println("Raft is Full, Only 2 people allowed in the raft");
      return gameStack;
    }
    if(gameConfiguration.getRaftPeople().contains(inputChar)){
      System.out.println("Already in Raft");
      return gameStack;
    }
    if(checkRuleViolation(gameConfiguration,inputChar)){
      return gameStack;
    }

    if(gameConfiguration.getRaftSide()==1){
      riverSide1.remove(inputChar);
    } else {
      riverSide2.remove(inputChar);
    }
    gameConfiguration.setRiverSide1(riverSide1);
    gameConfiguration.setRiverSide2(riverSide2);
    raft.add(inputChar);
    gameConfiguration.setRaftPeople(raft);
    gameStack.push(gameConfiguration);
    return gameStack;
  }

  private static Stack<GameConfiguration> moveRaft(Stack<GameConfiguration> gameStack, String inputChar){
    /** Creating copy of items of current gameConfiguration*/
    GameConfiguration gameConfiguration = new GameConfiguration(gameStack.peek());
    List<String> riverSide1 = new LinkedList<String>(gameConfiguration.getRiverSide1());
    List<String> riverSide2 = new LinkedList<String>(gameConfiguration.getRiverSide2());
    List<String> raft = new LinkedList<String>(gameConfiguration.getRaftPeople());

    /** Check for Raft Drivers*/
    if(!raft.contains("c") && !raft.contains("m") && !raft.contains("f")){
      System.out.println("Invalid move");
      System.out.println("No Driver present in the raft");
      return gameStack;
    }
    /** Move when on RiverBank 1*/
    if(gameConfiguration.getRaftSide()==1){
      gameConfiguration.setRaftSide(2);
      Iterator<String> iterator = raft.iterator();
      while (iterator.hasNext()){
        String person = iterator.next();
        riverSide2.add(person);
        gameConfiguration.setRaftPeople(raft);
        gameConfiguration.setRiverSide2(riverSide2);
        iterator.remove();
        if(checkRuleViolation(gameConfiguration,inputChar)){
          return gameStack;
        }
      }
    } else {
      /** Move when on RiverBank 2*/
      gameConfiguration.setRaftSide(1);
      Iterator<String> iterator = raft.iterator();
      while (iterator.hasNext()){
        String person = iterator.next();
        riverSide1.add(person);
        gameConfiguration.setRaftPeople(raft);
        gameConfiguration.setRiverSide1(riverSide1);
        iterator.remove();
        if(checkRuleViolation(gameConfiguration,inputChar)){
          return gameStack;
        }

      }
    }
    gameStack.push(gameConfiguration);
    return gameStack;
  }

  public static Stack<GameConfiguration> makeMove(Stack<GameConfiguration> gameStack, String inputChar){

    /** Reset Game*/
    if("r".equals(inputChar)){
      gameStack.clear();
      gameStack.push(GameHelperClass.initializeGame());
      resetGameCounter();
      return gameStack;

      /** Move People*/
    } else if("d1".equals(inputChar) || "d2".equals(inputChar) || "s1".equals(inputChar)
        || "s2".equals(inputChar) || "c".equals(inputChar) || "t".equals(inputChar)
        || "f".equals(inputChar) || "m".equals(inputChar)) {
      return movePerson(gameStack,inputChar);
    } else if("u".equals(inputChar)){
      if(gameStack.size()==1){
        return gameStack;
      }
      gameStack.pop();

      /** Move Raft*/
    } else if("mr".equals(inputChar)){
      return moveRaft(gameStack,inputChar);
    }
    return gameStack;
  }

  public static Stack<GameConfiguration> runGame(Stack<GameConfiguration> gameStack, String inputCharacter){
    clearScreen();
    gameStack = GameHelperClass.makeMove(gameStack,inputCharacter);
    if(GameHelperClass.checkRuleViolation(gameStack.peek(),"")){
      gameStack.pop();
    }
    System.out.println();
    printRules();
    GameHelperClass.printConfiguration(gameStack.peek());
    return gameStack;
  }

  public static void clearScreen(){
    final String os = System.getProperty("os.name");
    try {
      if (os.indexOf( "Linux" ) >= 0) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }else {
        /** Scrolling blank lines in Windows and other platforms*/
        for(int clear = 0; clear < 500; clear++)
        {
          System.out.println("\b") ;
        }
      }
    }catch (Exception e){

    }
  }

  public static void printSolution(Stack<GameConfiguration> gameStack){
    String[] solution = {"r","c","t","mr","c","mr","c","s1","mr","c","t","mr","f","s2","mr","f","mr","f","m","mr","m","mr","c","t","mr","f","mr","m","f","mr","m","mr","m","d1","mr","c","t","mr","c","d2","mr","c","mr","c","t","mr"};
    for(String inputCharacter : solution){
      gameStack = GameHelperClass.runGame(gameStack,inputCharacter);
      System.out.println("Move Made: " + inputCharacter);
      try
      {
        Thread.sleep(1500);
      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }
    }
    printComputerWins();
    try
    {
      Thread.sleep(1500);
    }
    catch(InterruptedException ex)
    {
      Thread.currentThread().interrupt();
    }
    resetGameCounter();
  }

  private static void resetGameCounter(){
    System.out.print("Resetting in ");
    for(int i = 3;i>=1;i--){
      System.out.print(i + "..");
      try
      {
        Thread.sleep(1000);
      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static void printComputerWins(){
    System.out.println(" _______________________________________________");
    System.out.println("|                                               |");
    System.out.println("|                                               |");
    System.out.println("|                                               |");
    System.out.println("|               Computer WINS !!!!!!            |");
    System.out.println("|                                               |");
    System.out.println("|                                               |");
    System.out.println("|_______________________________________________|");
    System.out.println();
    System.out.println();
  }

  public static boolean checkRuleViolation(GameConfiguration gameConfiguration, String inputChar){
    boolean flag = false;
    if("u".equals(inputChar) || "r".equals(inputChar) || inputChar.isEmpty() || "sol".equals(inputChar)){
      return flag;
    }

    /** Creating copy of items of current gameConfiguration*/
    List<String> riverSide1 = new LinkedList<String>(gameConfiguration.getRiverSide1());
    List<String> riverSide2 = new LinkedList<String>(gameConfiguration.getRiverSide2());
    List<String> raft = new LinkedList<String>(gameConfiguration.getRaftPeople());

    /** Temporarily adding all people from raft to respective bank to test conditions with people included in the raft*/
    if(gameConfiguration.getRaftSide()==1){
      riverSide1.addAll(raft);
    }
    if(gameConfiguration.getRaftSide()==2){
      riverSide2.addAll(raft);
    }

    if(gameConfiguration.getRaftPeople().size()>2){
      System.out.println("Invalid move");
      System.out.println("Raft can only hold upto 2 people");
      flag = true;
    }
    if((riverSide1.contains("d1") || riverSide1.contains("d2")) && !riverSide1.contains("m") && riverSide1.contains("f")){
      System.out.println("Invalid move");
      System.out.println("Father cannot be with any daughter without the mother");
      flag = true;
    }
    if((riverSide1.contains("s1") || riverSide1.contains("s2")) && !riverSide1.contains("f") && riverSide1.contains("m")){
      System.out.println("Invalid move");
      System.out.println("Mother cannot be with any son without the father");
      flag = true;
    }
    if((riverSide1.contains("s1") || riverSide1.contains("s2") || riverSide1.contains("f") || riverSide1.contains("m") || riverSide1.contains("d1") || riverSide1.contains("d2")) && riverSide1.contains("t") && !riverSide1.contains("c")){
      System.out.println("Invalid move");
      System.out.println("Thief cannot be with any family member without the Cop");
      flag = true;
    }

    if((riverSide2.contains("d1") || riverSide2.contains("d2")) && !riverSide2.contains("m") && riverSide2.contains("f")){
      System.out.println("Invalid move");
      System.out.println("Father cannot be with any daughter without the mother");
      flag = true;
    }
    if((riverSide2.contains("s1") || riverSide2.contains("s2")) && !riverSide2.contains("f") && riverSide2.contains("m")){
      System.out.println("Invalid move");
      System.out.println("Mother cannot be with any Son without the father");
      flag = true;
    }
    if(!raft.contains("c") && !raft.contains("m") && !raft.contains("f") && raft.size()==2){
      System.out.println("Invalid move");
      System.out.println("No Driver");
      flag = true;
    }
    if((riverSide2.contains("s1") || riverSide2.contains("s2") || riverSide2.contains("f") || riverSide2.contains("m") || riverSide2.contains("d1") || riverSide2.contains("d2")) && riverSide2.contains("t") && !riverSide2.contains("c")){
      System.out.println("Invalid move");
      System.out.println("Thief cannot be with any family member without the Cop");
      flag = true;
    }
    if(gameConfiguration.raftSide==1 && !riverSide1.contains(inputChar) && !"mr".equals(inputChar)){
      System.out.println("Invalid move");
      System.out.println(inputChar + " is not on this side of the river");
      flag = true;
    }
    if(gameConfiguration.raftSide==2 && !riverSide2.contains(inputChar) && !"mr".equals(inputChar)){
      System.out.println("Invalid move");
      System.out.println(inputChar + " is not on this side of the river");
      flag = true;
    }
    return flag;
  }

  public static boolean checkWinGame(GameConfiguration gameConfiguration){
    if(gameConfiguration.getRiverSide2().size()==8){
      System.out.println(" _______________________________________________");
      System.out.println("|                                               |");
      System.out.println("|                                               |");
      System.out.println("|                                               |");
      System.out.println("|                YOU WIN !!!!!!                 |");
      System.out.println("|                                               |");
      System.out.println("|                                               |");
      System.out.println("|_______________________________________________|");
      System.out.println();
      System.out.println();
      return true;
    }
    return false;
  }

  public static boolean checkLoseGame(GameConfiguration gameConfiguration){
    if(gameConfiguration.getRiverSide2().size()!=8){
      System.out.println(" _______________________________________________");
      System.out.println("|                                               |");
      System.out.println("|                                               |");
      System.out.println("|                                               |");
      System.out.println("|                YOU LOSE !!!!!!                |");
      System.out.println("|                                               |");
      System.out.println("|                                               |");
      System.out.println("|_______________________________________________|");
      System.out.println();
      System.out.println();
      return true;
    }
    return false;
  }

}
