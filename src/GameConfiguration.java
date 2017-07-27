import java.util.LinkedList;
import java.util.List;

public class GameConfiguration {
  List<String> riverSide1 = new LinkedList<String>();
  List<String> riverSide2 = new LinkedList<String>();
  int raftSide = 1;
  List<String> raftPeople = new LinkedList<String>();

  public GameConfiguration() {
  }

  //1 - riverSide1
  //2 - riverSide2

  public GameConfiguration(GameConfiguration gameConfiguration) {

    this.riverSide1 = gameConfiguration.getRiverSide1();
    this.riverSide2 = gameConfiguration.getRiverSide2();
    this.raftSide = gameConfiguration.getRaftSide();
    this.raftPeople = gameConfiguration.getRaftPeople();
  }

  public List<String> getRiverSide1() {
    return riverSide1;
  }

  public void setRiverSide1(List<String> riverSide1) {
    this.riverSide1 = riverSide1;
  }

  public void setRiverSide2(List<String> riverSide2) {
    this.riverSide2 = riverSide2;
  }

  public List<String> getRaftPeople() {
    return raftPeople;
  }

  public void setRaftPeople(List<String> raftPeople) {
    this.raftPeople = raftPeople;
  }

  public List<String> getRiverSide2() {
    return riverSide2;
  }

  public int getRaftSide() {
    return raftSide;
  }

  public void setRaftSide(int raftSide) {
    this.raftSide = raftSide;
  }
}
