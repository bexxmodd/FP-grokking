object ChapterSix {
    case class TvShow(name: String, start: Int, end: Int)

    def parseShows(rawShows: List[String]): List[Option[TvShow]] = {
        rawShows.map(parseShow)
    }

    def parseShow(raw: String): Option[TvShow] = {
        for {
            name <- extractName(raw)
            yearStart <- extractYearStart(raw).orElse(extractSingleYear(raw))
            yearEnd <- extractYearEnd(raw).orElse(extractYearStart(raw))
        } yield TvShow(name, yearStart, yearEnd)
    }

    def extractSingleYearIfNameExists(rawShow: String): Option[Int] = {
        extractName(rawShow).flatMap(name => extractYearStart(rawShow))
    }

    def extractName(raw: String): Option[String] = {
        val bracketOpen = raw.indexOf('(')
        if (bracketOpen < 0) None
        else Some(raw.substring(0, bracketOpen).trim)
    }

    def extractNameV2(raw: String): Either[String, String] = {
        val bracketOpen = raw.indexOf('(')
        if (bracketOpen > 0) Right(raw.substring(0, bracketOpen).trim)
        else Left(s"Can't extract name from $show")
    }

    def extractSingleYear(raw: String): Option[Int] =  {
        val dash = raw.indexOf('-')
        val bracketOpen = raw.indexOf('(')
        val bracketClose = raw.indexOf(')')
        for {
          yearStr <- if (dash == -1 && bracketOpen != -1
                          && bracketClose > bracketOpen + 1)
                        Some(raw.substring(bracketOpen + 1, bracketClose))
                      else None
          year    <- yearStr.toIntOption
        } yield year
    }

    def extractYearStart(rawShow: String): Option[Int] = {
        val bracketOpen = rawShow.indexOf('(')
        val dash = rawShow.indexOf('-')
        for {
            yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1)
                            Some(rawShow.substring(bracketOpen + 1, dash))
                        else None
            year    <- yearStr.toIntOption
        } yield year
    }

    def extractYearEnd(rawShow: String): Option[Int] = {
        val bracketClose = rawShow.indexOf(')')
        val dash = rawShow.indexOf('-')
        for {
            end     <- if (bracketClose != -1 && dash < bracketClose - 1)
                            Some(rawShow.substring(dash + 1, bracketClose))
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

        // println(sortShows(shows))
        
        val rawShows: List[String] = List(
            "Breaking Bad (2008-2013)",
            "The Wire (2002-2008)",
            "Mad Men (2007-2015)"
        )

        val A: List[String] = List(
            "MovieA (1992-)", "MovieB (2002)",
            "MovieC (-2012)", "(2022)", "E (-)"
        )
        
        println(parseShows(rawShows))
        println(extractNameV2("(2022)"))
    }
}
