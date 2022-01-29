opaque type Location = String
object Location {
  def apply(value: String): Location = value
  extension(a: Location)
      def name: String = a
}

case class PeriodInYears(start: Int, end: Int)

enum MusicGenre {
  case HeavyMetal
  case Pop
  case HardRock
}


enum YearsActive {
  case StillActive(since: Int)
  case ActiveBetween(period: List[PeriodInYears])
}

case class Artist(name: String, genre: MusicGenre,
                  origin: Location, yearsActive: YearsActive)


enum SearchCondition {
  case SearchByGenre(genre: List[MusicGenre])
  case SearchByOrigin(location: List[Location])
  case SearchByActiveYears(start: Int, end: Int)
}

// 7.18
case class User(name: String, city: Option[String], favArtists: List[String])

object ChapterSeven {
  import Location._
  import MusicGenre._
  import YearsActive._
  import SearchCondition._

  def searchArtists(artists: List[Artist],
    requiredConditions: List[SearchCondition]): List[Artist] = {
    artists.filter(artist => 
      requiredConditions.forall(condition => 
        condition match {
          case SearchByGenre(genres) => genres.contains(artist.genre)
          case SearchByOrigin(locations) => locations.contains(artist.origin)
          case SearchByActiveYears(start, end) => wasArtistActive(artist, start, end)
        }
      )
    )
  }

  def wasArtistActive(artist: Artist, yearStart: Int, yearEnd: Int): Boolean = {
    artist.yearsActive match {
      case StillActive(since) => since <= yearStart
      case ActiveBetween(period) => period.forall(p =>
                              p.start <= yearStart && p.end > yearEnd)
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

  // 7.29
  // def activeLength(artist: Artist, currentYear: Int): Int = {
  //   artist.yearsActive match {
  //     case StillActive(since) => currentYear - since
  //     case ActiveBetween(start, end) => end - start
  //   }
  // }

  val artists = List(
    Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(since = 1981)),
    Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(List(PeriodInYears(1968, 1980)))),
    Artist("Bee Gees", Pop, Location("England"), ActiveBetween(List(PeriodInYears(1958, 2003))))
  )

  println(searchArtists(artists, List(
    SearchByGenre(List(Pop)),
    SearchByOrigin(List(Location("England"))),
    SearchByActiveYears(1950, 2022))
  ))

  println(searchArtists(artists, List(SearchByActiveYears(1950, 2022))))
  println(searchArtists(artists, List.empty))
  println(searchArtists(artists, List(SearchByGenre(List(Pop)),                  
                        SearchByOrigin(List(Location("England")))))
  )

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

  println("------------ Tests for active lengths (7.29) ------------")
  // println(activeLength(
  //   Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981)), 2022)
  // )

  // println(activeLength(
  //   Artist("Led Zeppelin", HardRock, Location("England"),
  //     ActiveBetween(1968, 1980)),
  //     2022
  // ))

  // println(
  //   activeLength(
  //     Artist("Bee Gees", Pop, Location("England"),
  //     ActiveBetween(1958, 2003)), 2022)
  // )

  def main(args: Array[String]) = {
    println("Done!")
  }
}

