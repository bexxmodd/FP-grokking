opaque type Location = String
object Location {
  def apply(value: String): Location = value
  extension(a: Location)
      def name: String = a
}

import Location.*

case class Artist(name: String, genre: String, origin: Location,
                  startYear: Int, yearActiveEnd: Option[Int])
// def searchArtists(
//   artists: List[Artist],
//   genres: List[String],
//   locations: List[String],
//   searchByActiveYears: Boolean,
//   activeAfter: Int,
//   activeBefore: Int
// ): List[Artist] = {
//   val res = artists.filter(a => genres.contains(a.genre)
//                                 && (locations.contains(a.origin) || locations.isEmpty))
//   if (searchByActiveYears)
//     res.filter(a => (a.startYear < activeAfter && a.active) 
//                     || (a.startYear > activeAfter) && a.endYear < activeBefore)
//   else
//     res
// }

object ChapterSeven {
  def main(args: Array[String]) = {
    val artists = List(
      Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, None),
      Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, Some(1980)),
      Artist("Bee Gees", "Pop", Location("England"), 1958, Some(2003))
    )
    println("Done!")
  }

  // println(searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))
  // println(searchArtists(artists, List.empty, List.empty, true, 1950, 1979))
  // println(searchArtists(artists, List("Heavy Metal"), List.empty, true, 2019, 2022))
}

