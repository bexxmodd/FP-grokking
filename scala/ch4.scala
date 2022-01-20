object ChapterFour {
  def inc(x: Int): Int = x + 1
  def score(word: String): Int = word.count(_ != 'a')
  def bonus(word: String): Int = if (word.contains('c')) 5 else 0
  def penalty(word: String): Int = if (word.contains('s')) 7 else 0
  def rankedWords(wordScore: String => Int,
    words: List[String]): List[String] = {
    words.sortBy(wordScore).reverse
  }

  def wordScores(wordScore: String => Int,
    words: List[String]): List[Int] = words.map(wordScore)

  def highScoringWords(wordScore: String => Int,
    words: List[String]): Int => List[String] = {
      higherThan => words.filter(word => wordScore(word) > higherThan)
  }

  def betterHighScorigWords(wordScore: String => Int):
    Int => List[String] => List[String] = {
    higherThan => words => words.filter(word => wordScore(word) > higherThan)
  }

  def evenBetterHighScoringWords(wordScore: String => Int)
    (higherThan: Int)(words: List[String]): List[String] = {
    words.filter(word => wordScore(word) > higherThan)
  }

  // 4.41 - 1
  def firstScores(numbers: List[Int]): Int => List[Int] = {
    largerThan => numbers.filter(i => i > largerThan)
  }

  // 4.41 - 2
  def divisibleBy(numbers: List[Int]): Int => List[Int] = {
    divisible => numbers.filter(n => n % divisible == 0)
  }

  // 4.41 - 3
  def shorterThan(words: List[String]): Int => List[String] = {
    shortestSize => words.filter(w => w.length < shortestSize)
  }

  // 4.41 - 4 v1
  def containsSChar(words: List[String]): Int => List[String] = {
    sCount => words.filter(w => w.count(_ == 's') > sCount)
  }

  // 4.41 - 4 v2
  def containsS(count: Int): String => Boolean = w => w.count(_ == 's') > count

  // 4.50 - 1
  def higherThan2(n: Int)(i: Int): Boolean = i > n

  // 4.50 - 2
  def divisibleBy2(n: Int)(i: Int): Boolean = i % n == 0

  // 4.50 - 3
  def shorterThan2(n: Int)(word: String): Boolean = word.length < n

  // 4.50 - 4
  def containsSChar2(count: Int)(word: String): Boolean =
    word.count(_ == 's') > count

  def cumulativeScore(wordScore: String =>Int, words: List[String]): Int = {
    words.foldLeft(0)((total, word) => total + wordScore(word))
  }

  case class ProgrammingLanguage(name: String, year: Int)

  /////// Main Function ///////
  def main(args: Array[String]) = {
    println(rankedWords(w => score(w) + bonus(w) - penalty(w),
      List("haskell", "ada", "scala", "rust", "java")))

    val words = List("rust", "scala", "ada")
    val wordsWithScoreHigherThan: Int => List[String] => List[String] =
      betterHighScorigWords(w => score(w) + bonus(w) - penalty(w))
    println(wordsWithScoreHigherThan(1)(words))
    println(wordsWithScoreHigherThan(0)(words))
    println(wordsWithScoreHigherThan(5)(List("football", "hokey")))

    val res: Int => List[Int] = firstScores(List(5, 1, 2, 4, 0))
    println(res(4))
    println(res(1))

    val res2: Int => List[Int] = divisibleBy(List(5, 1, 2, 4, 15))
    println("divisible by 5: " + res2(5))
    println("divisible by 2: " + res2(2))

    val res3: Int => List[String] = shorterThan(List("scala", "ada"))
    println("shorter than 4: " + res3(4))
    println("shorter than 7: " + res3(7))

    val res4: Int => List[String] = containsSChar(List("rust", "ada"))
    println("contains 2 's': " + res4(2))
    println("contains 0 's': " + res4(0))
    println("New version of res4: " + List("rust", "ada").filter(containsS(0)))

    val scalalang = ProgrammingLanguage("Scala", 2004)
    println("Name: " + scalalang.name)
    println("Released in: " + scalalang.year)
  }
}
