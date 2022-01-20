fn inc(x: isize) -> isize {
    x + 1
}

fn score(word: &String) -> isize {
    word.matches('a').count() as isize
}

fn bonus(word: &String) -> isize {
    if word.contains('c') { 5 } else { 0 }
}

fn penalty(word: &String) -> isize {
    if word.contains('s') { 7 } else { 0 }
}

fn rank_words(score: impl Fn(&String) -> isize,
    words: &Vec<String>) -> Vec<String> {
    let mut res = words.clone();
    res.sort_by_key(|w| std::cmp::Reverse(score(w)));
    res
}

fn word_scores(score: impl Fn(&String) -> isize,
    words: &Vec<String>) -> Vec<isize> {
    let res = words.iter()
                   .map(|w| score(w))
                   .collect();
    res
}

fn high_scoring_words(score: impl Fn(&String) -> isize,
    words: Vec<String>) -> impl Fn(isize) -> Vec<String> {
    move |higher_than| words.clone()
                        .into_iter()
                        .filter(|w| score(w) > higher_than)
                        .collect()
}

// 4.41 - 1
fn first_scores(numbers: Vec<isize>) -> impl Fn(isize) -> Vec<isize> {
    move |larger_than| numbers.clone()
                        .into_iter()
                        .filter(|i| i > &larger_than)
                        .collect()
}

// 4.41 - 2
fn divisible_by(numbers: Vec<isize>) -> impl Fn(isize) -> Vec<isize> {
    move |by| numbers.clone()
                .into_iter()
                .filter(|n| n % by == 0)
                .collect()
}

// 4.41 - 3
fn shorter_than(words: Vec<String>) -> impl Fn(isize) -> Vec<String> {
    move |limit| words.clone()
                    .into_iter()
                    .filter(|w| w.len() < limit as usize)
                    .collect()
}

// 4.41 - 4
fn contains_s(count: isize) -> impl Fn(String) -> bool {
    |word| word.contains('s')
}

fn main() {
    assert_eq!(score(&"ada".to_string()), 2);
    assert_eq!(bonus(&"scala".to_string()), 5);
    let v = vec!["ada".to_string(), "rust".to_string(),
                "scala".to_string(), "haskell".to_string()];
    let w = rank_words(score, &v);
    println!("{:?}", w);
    println!("{:?}", word_scores(score, &w));
    let f1 = high_scoring_words(score, w)(1);
    println!("{:?}", f1);

    let ws = vec!["rust".to_string(), "ada".to_string(), "scala".to_string()];
    let words_with_hs = rank_words(|w| score(w) + bonus(w) - penalty(w), &ws);
    println!("TOTAL SCORES: {:#?}", words_with_hs);
}
