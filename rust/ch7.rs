
struct Location(String);

enum MusicGenre {
    HeavyMetal,
    Pop,
    HardRock,
}

struct PeriodInYears {
    start: usize,
    end: Option<usize>,
}

struct Artist {
    name: String,
    genre: MusicGenre,
    origin: Location,
    years_active: PeriodInYears,
}


fn main() {
    println!("Hello, Chapter Seven!");
}
