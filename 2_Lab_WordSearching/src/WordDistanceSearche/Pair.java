package WordDistanceSearche;
public class Pair<X, Y> { 
  private X first; 
  private Y second; 
  public Pair(X first, Y second) { 
    this.first = first; 
    this.second = second; 
  } 
  public X getFirst() {
	  return first;
  }
  public void setFirst(X value) {
	  first = value;
  }
  
  public Y getSecond() {
	  return second;
  }
  public void setSecond(Y value) {
	  second = value;
  }
}