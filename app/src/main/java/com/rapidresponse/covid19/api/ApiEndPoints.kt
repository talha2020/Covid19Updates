package com.rapidresponse.covid19.api

object ApiEndPoints {
    private const val NOVEL_COVID_HOST = "https://corona.lmao.ninja/"
    private const val NOVEL_COVID_HOST_V2 = "https://corona.lmao.ninja/v2/"

    const val SUMMARY = NOVEL_COVID_HOST + "all"
    const val COUNTRIES = NOVEL_COVID_HOST + "countries"
    const val COUNTRIES_HISTORICAL = NOVEL_COVID_HOST_V2 + "historical/"

    const val WHO_RSS = "https://www.who.int/rss-feeds/covid19-news-english.xml"
}