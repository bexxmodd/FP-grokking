use std::cmp;

#[derive(Debug, cmp::PartialEq)]
enum Category {
    Entertainment,
    Food,
    Health,
    Home,
    Work,
    Other,
}

#[derive(Debug, cmp::PartialEq)]
struct Purchase {
    venue: String,
    date: String,
    amount: f32,
    category: Category,
}

impl Purchase {
    fn new(venue: String, date: String,
        amount: f32, category: Category) -> Self {
        Purchase { venue, date, amount, category }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn it_works() {
        let p1 = Purchase::new("Two Cinema".to_string(),
            "6.8.2021".to_string(),
            11.25,
            Category::Entertainment);

        let p2 = Purchase::new("Joe's".to_string(),
            "1.13.2021".to_string(),
            20.65,
            Category::Food);

        let p3 = Purchase::new("Mcd".to_string(),
            "1.16.2021".to_string(),
            5.95,
            Category::Food);

        let p4 = Purchase::new("Shitty Hospital".to_string(),
            "2.23.2021".to_string(),
            145.35,
            Category::Health);

        let bills: Vec<Purchase> = vec![p1, p2, p3, p4];
        let food_cat = |x: &Purchase| -> bool { Category::Food == x.category };
        let food_bills: Vec<_> = bills.iter()
                                 .filter(|x| food_cat(x))
                                 .collect();

        assert_eq!(food_bills[0].venue, "Joe's".to_string());
        assert_eq!(food_bills[1].venue, "Mcd".to_string());
    }
}
