#[derive(Debug)]
struct Book {
    name: String,
    authors: Vec<String>,
}

#[derive(Debug)]
struct Movie { title: String }

fn book_adaptations(author: &String) -> Vec<Movie> {
    match author as &str {
        "Tolkien" => vec![
            Movie { title: "An Unexpected Journey".to_string() },
            Movie { title: "The Desolation of Smaug".to_string() },
        ],
        _ => vec![]
    }
}

fn recommended_books(friend: &String) -> Vec<Book> {
    let programming = vec![
        Book{ name: "FP in Scala".to_string(),
            authors: vec!["Chisano".to_string(), "Bjarnason".to_string()]},
        Book{ name: "The Hobbit".to_string(),
            authors: vec!["Tolkien".to_string()]}
    ];

    let fiction = vec![
        Book { name: "Harry Potter".to_string(),
            authors: vec!["Rowling".to_string()] },
        Book { name: "The Lord Of The Rigs".to_string(),
            authors: vec!["Tolkien".to_string()] }
    ];
    match friend as &str {
        "Alice" => programming,
        "Bob" => fiction,
        _ => vec![]
    }
}

#[derive(Debug)]
struct Point {
    x: i32,
    y: i32,
}

fn is_within(point: &Point, radius: u32) -> bool {
    (radius * radius) as i32 >= point.x * point.x + point.y * point.y
}


fn main() {
    let books = vec![
        Book{ name: "FP in Scala".to_string(),
            authors: vec!["Chisano".to_string(), "Bjarnason".to_string()]},
        Book{ name: "The Hobbit".to_string(),
            authors: vec!["Tolkien".to_string()]},
        Book{ name: "Rust in Action".to_string(),
            authors: vec!["McNamera".to_string()]}
    ];

    let avts: Vec<Movie> = books.into_iter()
                                 .flat_map(|b| b.authors)
                                 .flat_map(|a| book_adaptations(&a))
                                 .collect();
    println!("{:#?}", avts);

    let friends = vec![
        String::from("Alice"), String::from("Bob"), String::from("Charlie")
    ];
    let recs: Vec<String> = friends.into_iter()
                      .flat_map(|f| recommended_books(&f))
                      .flat_map(|b| b.authors)
                      .collect();
    println!("{:#?}", recs);
}
