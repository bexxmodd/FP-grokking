use std::collections::HashSet;

#[derive(Debug, Clone)]
struct User(String);

struct Playlist {
    name: String,
    songs: Vec<Song>,
    pl_type: PlType,
}

enum PlType {
    UserCurated(User),
    ArtistSpecific(Artist),
    GenreSpecific(HashSet<MusicGenre>)
}

#[derive(PartialEq, Debug, Clone)]
struct Artist {
    name: String,
    genre: MusicGenre,
}

#[derive(Debug, Clone)]
struct Song {
    artist: Artist,
    name: String,
}

#[derive(PartialEq, Eq, Debug, Clone, Hash)]
enum MusicGenre {
    HardRock,
    Alternative,
    Techno,
}

fn gather_songs(playlists: Vec<Playlist>, artist: Artist, genre: MusicGenre) -> Vec<Song> {
    playlists.into_iter()
         .fold(vec![], |mut songs, playlist| {
            let mut matching_song = match &playlist.pl_type {
                PlType::UserCurated(_user) => playlist.songs
                                                .into_iter()
                                                .filter(|s| s.artist == artist)
                                                .collect(),
                PlType::ArtistSpecific(pl_artist) => {
                    if pl_artist == &artist { playlist.songs }
                    else { vec![] }
                }
                PlType::GenreSpecific(genres) => {
                    if genres.contains(&genre) { playlist.songs }
                    else { vec![] }
                }
            }; 
            songs.append(&mut matching_song);
            songs
        }
    )
}

fn main() {
    let pink_floyd = Artist {
        name: String::from("Pink Floyd"), genre: MusicGenre::HardRock
    };

    let pink_songs = vec![
        Song {
            artist: pink_floyd.clone(),
            name: String::from("With You Were Here")
        },
        Song {
            artist: pink_floyd.clone(),
            name: String::from("Great Gig In The Sky"),
        },
    ];

    let mix_songs = vec![
        Song {
            artist: Artist { name: String::from("Daft Punk"), genre: MusicGenre::Techno },
            name: String::from("One More Time")
        },
        Song {
            artist: Artist { name: String::from("Chemical Brothers"), genre: MusicGenre::Techno },
            name: String::from("Hey Boy Hey Girl")
        }
    ];

    let rock_songs = vec![
        Song {
            artist: Artist { name: String::from("Jimmy Hendrix"), genre: MusicGenre::HardRock },
            name: String::from("Hey Joe")
        },
        Song {
            artist: Artist { name: String::from("Led Zeppelin"), genre: MusicGenre::HardRock },
            name: String::from("Stairway to Heaven")
        }
    ];

    let user1 = User(String::from("Bexx"));

    let pl1 = Playlist { 
        name: "This is Pink Floyd".to_string(),
        songs: pink_songs,
        pl_type: PlType::ArtistSpecific(pink_floyd.clone()),
    };

    let pl2 = Playlist {
        name: String::from("Deep Focus"),
        songs: mix_songs,
        pl_type: PlType::UserCurated(user1),
    };

    let mut genres = HashSet::<MusicGenre>::new();
    genres.insert(MusicGenre::HardRock);

    let pl3 = Playlist {
        name: String::from("Rock 'n Roll"),
        songs: rock_songs,
        pl_type: PlType::GenreSpecific(genres),
    };

    let res = gather_songs(vec![pl1, pl2, pl3], pink_floyd, MusicGenre::HardRock);
    println!("Found : {:#?}", res);
}
