fn increment(x: i32) -> i32 {
    x + 1
}

fn main() {
    assert_eq!(Some(2).unwrap_or_else(|| increment(2)), 2);
    assert_eq!(None.unwrap_or_else(|| increment(2)), 3);

    assert_eq!(Some(String::from("Beka")).map(|i| increment(i.len() as i32)),
                Some(5));

    let a = Some(1);
    assert_eq!(a.iter().next(), Some(&1));
}
