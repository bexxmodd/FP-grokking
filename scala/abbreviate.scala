object ChapterThree {
  def main(args: Array[String]) = {
    var name = "Nerse Modd";
    var abr = abbreviate(name)

    var y = List("A", "B", "C")
    assert(List("A", "B") == firstTwo(y))
    assert(List("B", "C") == lastTwo(y))
    assert(List("C", "A", "B") == movedFirstTwoToTheEnd(y))
    assert(List("A", "B", "D", "C") == insertedBeforeLast(y, "D"))
    println(abr)
  }

  def abbreviate(name: String): String = {
    name.slice(0, 1) + '.' + name.substring(name.indexOf(' '))
  }

  def firstTwo(x: List[String]): List[String] =  {
    assert(x.size > 1)
    x.slice(0, 2)
  }

  def lastTwo(x: List[String]): List[String] = {
    assert(x.size > 1)
    var len = x.size
    x.slice(len - 2, len)
  }

  def movedFirstTwoToTheEnd(x: List[String]): List[String] = {
    assert(x.size > 1)
    x.slice(2, x.size) ++ firstTwo(x)
  }

  def insertedBeforeLast(x: List[String], new_el: String): List[String] = {
    assert(x.size > 0)
    var without_last = x.slice(0, x.size - 1)
    var last = x.slice(x.size - 1, x.size)
    without_last ::: new_el :: last
  }
}
