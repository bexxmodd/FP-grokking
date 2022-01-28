opaque type Location = String
object Location {
  def apply(value: String): Location = value
  extension(a: Location)
      def name: String = a
}

import Location.*

case class Artist(name: String, genre: String,
                  origin: Location, yearsActive: PeriodInYears)

case class PeriodInYears(start: Int, end: Option[Int])

// 7.18
case class User(name: String, city: Option[String], favArtists: List[String])

object ChapterSeven {
  def searchArtists(
    artists: List[Artist],
    genres: List[String],
    locations: List[String],
    searchByActiveYears: Boolean,
    activeAfter: Int,
    activeBefore: Int
  ): List[Artist] = {
    artists.filter(artist =>
      (genres.isEmpty || genres.contains(artist.genre)) &&
      (locations.isEmpty || locations.contains(artist.origin.name)) &&
      (!searchByActiveYears || (
        (artist.yearsActive.end.forall(_ >= activeAfter)) &&
          (artist.yearsActive.start <= activeBefore)))
      )
  }

  // 7.18 - 1
  def usersInCity(users: List[User]): List[User] = {
    users.filter(_.city.forall(_ == "Melbourne"))
  }

  // 7.18 - 2
  def usersInLagos(users: List[User]): List[User] = {
    users.filter(_.city.contains("Lagos"))
  }

  // 7.18 - 3
  def usersLikeBeeGees(users: List[User]): List[User] = {
    users.filter(_.favArtists.contains("Bee Gees"))
  }

  // 7.18 - 4
  def usersStartWithT(users: List[User]): List[User] = {
    users.filter(_.city.exists(_.startsWith("T")))
  }

  // 7.18 - 5
  def usersWithLongNames(users: List[User]): List[User] = {
    users.filter(_.favArtists.forall(_.size > 8))
  }

  // 7.18 - 6
  def usersLikeArtistsWithM(users: List[User]): List[User] = {
    users.filter(_.favArtists.exists(_.startsWith("M")))
  }

  val artists = List(
    Artist("Metallica", "Heavy Metal", Location("U.S."), PeriodInYears(1981, None)),
    Artist("Led Zeppelin", "Hard Rock", Location("England"), PeriodInYears(1968, Some(1980))),
    Artist("Bee Gees", "Pop", Location("England"), PeriodInYears(1958, Some(2003)))
  )

  println(searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))
  println(searchArtists(artists, List.empty, List.empty, true, 1950, 1979))
  println(searchArtists(artists, List("Heavy Metal"), List.empty, true, 2019, 2022))

  // 7.18
  val users = List(
    User("Alice", Some("Melbourne"), List("Bee Gees")),
    User("Bob", Some("Lagos"), List("Bee Gees")),
    User("Eve", Some("Tokyo"), List.empty),
    User("Mallory", None, List("Metallica", "Bee Gees")),
    User("Trent", Some("Buenos Aires"), List("Led Zeppelin"))
  )

  println("Users In City: \n\t" + usersInCity(users).map(_.name))
  println("Users In Lagos: \n\t" + usersInLagos(users).map(_.name))
  println("Users Like Bee Gees: \n\t" + usersLikeBeeGees(users).map(_.name))
  println("Users City Stars with T: \n\t" + usersStartWithT(users).map(_.name))
  println("Users Fav Artists Name > 8 Char: \n\t" + usersWithLongNames(users).map(_.name))
  println("Users Fav Artists Name with M: \n\t" + usersLikeArtistsWithM(users).map(_.name))

  def main(args: Array[String]) = {
    println("Done!")
  }
}

