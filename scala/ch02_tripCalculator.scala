object Main extends App {
  println(TipCalculator.getTipPercentage(List.empty) == 0);

  println(TipCalculator.getTipPercentage(List("Alice", "Bob", "Charlie")) == 10);

  var names3 = List("Alice", "Bob", "Charlie", "Daniel", "Emily", "Frank");
  println("names3 gets discount = " + TipCalculator.getTipPercentage(names3));


}

object TipCalculator {
  def getTipPercentage(names: List[String]): Int = {
    if (names.size > 5)
      20
    else if (names.size > 0)
      10
    else
      0
  }
}

object Mess {
  def increment(x: Int): Int = {
    x + 1
  }

  def add(a: Int, b: Int): Int = {
    a + b
  }

  def wordScore(word: String): Int = {
    word.replaceAll("a", "").length
  }
}

