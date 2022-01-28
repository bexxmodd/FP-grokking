opaque type Location = String
object Location {
  def apply(value: String): Location = value
  extension(a: Location)
      def name: String = a
}

enum MusicGenre {
  case HeavyMetal
  case Pop
  case HardRock
}

enum YearsActive {
  case StillActive(since: Int)
  case ActiveBetween(start: Int, end: Int)
}

import Location._
import MusicGenre._
import YearsActive._

case class Artist(name: String, genre: MusicGenre,
                  origin: Location, yearsActive: YearsActive)

case class PeriodInYears(start: Int, end: Option[Int])

// 7.18
case class User(name: String, city: Option[String], favArtists: List[String])

object ChapterSeven {
  def searchArtists(artists: List[Artist], genres: List[MusicGenre],
                    locations: List[String], searchByActiveYears: Boolean,
                    activeAfter: Int, activeBefore: Int
  ): List[Artist] = {
    artists.filter(artist =>
      (genres.isEmpty || genres.contains(artist.genre)) &&
      (locations.isEmpty || locations.contains(artist.origin.name)) &&
        (!searchByActiveYears ||
        wasArtistActive(artist, activeAfter, activeBefore))
    )
  }

  def wasArtistActive(artist: Artist, yearStart: Int, yearEnd: Int): Boolean = {
    artist.yearsActive match {
      case StillActive(since) => since <= yearStart
      case ActiveBetween(start, end) => start <= yearStart && end > yearEnd
    }
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
    Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(since = 1981)),
    Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1968, 1980)),
    Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
  )

  println(searchArtists(artists, List(Pop), List("England"), true, 1950, 2022))
  println(searchArtists(artists, List.empty, List.empty, true, 1950, 1979))
  println(searchArtists(artists, List(HeavyMetal), List.empty, true, 2019, 2022))

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

