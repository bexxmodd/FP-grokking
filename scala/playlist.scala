opaque type User = String
object User {
  def apply(name: String): User = name
}

case class Playlist(name: String, songs: List[Song], plType: PlaylistType)
case class Artist(name: String, genre: MusicGenre)
case class Song(artist: Artist, name: String)

enum PlaylistType {
  case UserCurated(user: User)
  case ArtistSpecific(artist: Artist)
  case GenreSpecific(genres: Set[MusicGenre])
}

enum MusicGenre {
  case HardRock
  case Alternative
  case Techno
}

object HelloPlaylist {
  import MusicGenre._
  import PlaylistType._
  
  def gatherSongsSimple(playlists: List[Playlist], artist: Artist,
    genre: MusicGenre): List[Song] = {
      for {
        play  <- playlists
        song  <- play.songs
        if (song.artist.name == artist.name || song.artist.genre == genre)
      } yield song
  }

  def gatherSongs(playlists: List[Playlist], artist: Artist,
    genre: MusicGenre): List[Song] = {
    playlists.foldLeft(List.empty[Song])((songs, playlist) =>
        val matchingSongs = playlist.plType match {
          case UserCurated(user) => playlist.songs.filter(_.artist == artist)
          case ArtistSpecific(plArtist) => if (plArtist == artist) playlist.songs
                                           else List.empty
          case GenreSpecific(genres) => if (genres.contains(genre)) playlist.songs
                                        else List.empty
        }
        songs.appendedAll(matchingSongs)
      )
  }

  val foo = Artist("Foo Fighters", HardRock)
  // val pls = List(
  //   Playlist("This is Foo Fighters", List(Song(foo, "Breakout"),
  //     Song(foo, "Learn To Fly")), ArtistSpecific),
  //   Playlist("Deep Focus", List(Song(Artist("Daft Punk", Techno), "One More Time"),
  //     Song(Artist("Chemical Brothers", Techno), "Hey Boy Hey Girl")), UserCurated),
  //   Playlist("Classic Rock", List(Song(Artist("Pink Floyed", HardRock), "With You Were Here"),
  //     Song(Artist("Jimmy Hendrix", HardRock), "Watchtowers")), GenreSpecific)
  // )
  // println(gatherSongs(pls, foo, HardRock).map(_.name))

  def main(args: Array[String]) = {
    println("Success!")
  }
}
