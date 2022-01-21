object ChapterSix {
    case class TvShow(name: String, start: Int, end: Int)

    def parseShows(rawShows: List[String]): List[TvShow] = {
        rawShows.map(parseShow)
    }

    def parseShow(rawShow: String): Option[TvShow] = {
        for {
            name <- extractName(rawShow)
            yearStart <- extractYearStart(rawShow)
            yearEnd <- extractYearEnd(rawShow)
        } yield TvShow(name, yearStart, yearEnd)
    }

    def extractName(raw: String): Option[String] = {
        val bracketOpen = raw.indexOf('(')
        if (bracketOpen < 0) None
        else Some(raw.substring(0, bracketOpen).trim)
    }

    def extractYearStart(raw: String): Option[Int] = {
        val bracketOpen = rawShow.indexOf('(')
        val dash = rawShow.indexOf('-')
        for {
            yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1)
                            Some(rawShow.substring(bracketOpen + 1, dash))
                        else None
            year    <- yearStr.toIntOption
        } yield year
    }

    def extractYearEnd(raw: String): Option[Int] = {
        val bracketClose = rawShow.indexOf(')')
        val dash = rawShow.indexOf('-')
        for {
            end     <- if (bracketClose != -1 && dash < bracktClose - 1)
                            Some(rawShow.substring(dash + 1, bracktClose))
                        else None
            year    <- end.toIntOption
        } yield year
    }

    def sortShows(shows: List[TvShow]): List[TvShow] = {
        shows.sortBy(show => show.end - show.start)
             .reverse
    }

    def sortRawShows(rawShows: List[String]): List[TvShow] = {
        val tvShows = parseShows(rawShows)
        sortShows(tvShows)
    }

    def main(args: Array[String]) = {
        val shows = List(
            TvShow("Always Sunny", 2006, 2022),
            TvShow("The Wire", 2002, 2008),
            TvShow("X-Files", 1993, 2001),
            TvShow("Lost", 2004, 2010)
        )

        println(sortShows(shows))
        
        val rawShows: List[String] = List(
            "Breaking Bad (2008-2013)",
            "The Wire (2002-2008)",
            "Mad Men (2007-2015)"
        )
        
        println(parseShows(rawShows))
    }
}