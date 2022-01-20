struct Book {
    name: String,
    authors: Vec<String>,
}

fn main() {
    let books = vec![
        Book{"FP in Scala".to_string(), vec!["Chisano".to_string(), "Bjarnason".to_string()]},
        Book{"The Hobbit".to_string(), vec!["Tolkien".to_string()]},
        Book{"Rust in Action".to_string(), vec!["McNamera".to_string()]}
    ];

    let res = books.iter().map(|book| books.title).filter(|w| w.contains("Rust")).len();
}
