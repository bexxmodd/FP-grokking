object ChapterFive {

    case class Book(title: String, authors: List[String])
    case class Movie(title: String)
    def bookAdaptations(author: String): List[Movie] =
        if (author == "Tolkien")
            List(Movie("An Unexpected Journey"),
                Movie("The Desolation of Smaug"))
        else List.empty

    val books = List(
        Book("FP in Scala", List("Chisano", "Bjarnason")),
        Book("The Hobbit", List("Tolkien")),
        Book("Rust in Action", List("McNamera"))
    )

    val res = books.map(_.title)
                .filter(_.contains("Rust"))
                .size
    println("Count " + res)

    println(books.flatMap(_.authors))
    val a = books.flatMap(_.authors)
                 .flatMap(bookAdaptations)
    println("Adaptations: " + a)

    def recommendedBooks(friend: String): List[Book] = {
        val scala = List(
            Book("FP in Scala", List("Chiusano", "Bjarnason")),
            Book("Get Programming with Scala", List("Sfregola")))
        val fiction = List(
            Book("Harry Potter", List("Rowling")),
            Book("The Lord of the Rings", List("Tolkien")))
        if(friend == "Alice") scala
        else if(friend == "Bob") fiction
        else List.empty
    }

    val friends = List("Alice", "Bob", "Charlie")
    val recommendations = friends.flatMap(recommendedBooks)
                                 .flatMap(_.authors)
    println("Friends recommended: " + recommendations)

    //  5.12
    case class Point(x: Int, y: Int)
    val res5 = for {
        book <- books
        author <- book.authors
        movie <- bookAdaptations(author)
    } yield s" You may like ${movie.title}, because you liked $author's ${book.title}"
    println(res5)

    def isInside(point: Point, radius: Int): Boolean =
        radius * radius >= point.x * point.x + point.y * point.y
    
    val points = List(Point(5, 2), Point(1, 1))
    val radiuses = List(2, 1)
    println(
        for {
            r     <- radiuses
            point <- points.filter(p => isInside(p, r))
        } yield s"$point is withing a radius of $r"
    )
    
    // for comprehension using a guard expression
    println("\t With Guard Expression:")
    println(
        for {
            r     <- radiuses
            point <- points
            if isInside(point, r)
        } yield s"$point is within a radius of $r"
    )

    val riskyRadiuses = List(-10, 0, 2)
    def insideFilter(point: Point, radius: Int): List[Point] = {
        if (isInside(point, radius)) List(point) else List.empty
    }

    def validateRadius(radius: Int): List[Int] = {
        if (radius > 0) List(radius) else List.empty
    }

    println(
        for {
            r     <- riskyRadiuses.filter(r => r > 0)
            point <- points.filter(p => isInside(p, r))

        } yield s"$point is within a radius of $r"
    )

    println(
        for {
            r <- riskyRadiuses
            validRadius <- validateRadius(r)
            point <- points
            inPoint <- insideFilter(point, validRadius)
        } yield s"$inPoint is within $validRadius"
    )

    case class Event(name: String, start: Int, end: Int)

    // bad solution
    def parse(name: String, start: Int, end: Int): Event = {
        if (name.size > 0 && end < 3000 && start <= end)
            Event(name, start, end)
        else
            null
    }

    // better one
    def parseB(name: String, start: Int, end: Int): Option[Event] = {
        if (name.size > 0 && end < 3000 && start <= end)
            Some(Event(name, start, end))
        else
            None
    }

    // even better
    def validateName(name: String): Option[String] =
        if (name.size > 0) Some(name) else None
    
    def validateEnd(end: Int): Option[Int] =
        if (end < 3000) Some(end) else None
    
    def validateStart(start: Int, end: Int): Option[Int] =
        if (start <= end) Some(start) else None

    def validateLength(start: Int, end: Int, minLength: Int): Option[Int] =
        if (end - start >= minLength) Some(end - start) else None

    def parseLongEvent(
        name: String, start: Int, end: Int, minLength: Int): Option[Event] = {
        for {
            validName   <- validateName(name)
            validEnd    <- validateEnd(end)
            validStart  <- validateStart(start, validEnd)
            validLen    <- validateLength(validStart, validEnd, minLength)
        } yield Event(name, validStart, validEnd)
    }

    println(parseLongEvent("Apollo Program", 1961, 1972, 10))
    println(parseLongEvent("Other Program", 1971, 1972, 10))

    def main(args: Array[String]) = {
        
    }
}
