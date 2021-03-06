package com.example.testtask

import com.google.gson.Gson
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

data class Page (val Date: String,
                 val PreviousDate : String,
                 val PreviousURL: String,
                 val Timestamp : String,
                 val Valute: Valutes) {}

data class Valutes (val AUD : ValuteJS, val AZN : ValuteJS, val GBP : ValuteJS,
                    val AMD : ValuteJS, val BYN : ValuteJS, val BGN : ValuteJS,
                    val BRL : ValuteJS, val HUF : ValuteJS, val HKD : ValuteJS,
                    val DKK : ValuteJS, val USD : ValuteJS, val EUR : ValuteJS,
                    val INR : ValuteJS, val KZT : ValuteJS, val CAD : ValuteJS,
                    val KGS : ValuteJS, val CNY : ValuteJS, val MDL : ValuteJS,
                    val NOK : ValuteJS, val PLN : ValuteJS, val RON : ValuteJS,
                    val XDR : ValuteJS, val SGD : ValuteJS, val TJS : ValuteJS,
                    val TRY : ValuteJS, val TMT : ValuteJS, val UZS : ValuteJS,
                    val UAH : ValuteJS, val CZK : ValuteJS, val SEK : ValuteJS,
                    val CHF : ValuteJS, val ZAR : ValuteJS, val KRW : ValuteJS,
                    val JPY : ValuteJS) {

    fun getValutes() : List<ValuteJS> =
         listOf(AUD, AZN, GBP, AMD, BYN, BGN, BRL, HUF, HKD,
                DKK, USD, EUR, INR, KZT, CAD, KGS, CNY, MDL, NOK,
                PLN, RON, XDR, SGD, TJS, TRY, TMT, UZS, UAH, CZK,
                SEK, CHF, ZAR, KRW, JPY)

}

data class ValuteJS (val ID: String,
                   val NumCode : String,
                   val CharCode : String,
                   val Nominal : Int,
                   val Name : String,
                   val Value : Double,
                   val Previous : Double) {}

class DataPage {

    var data : List<ValuteJS>? = null
    var url = "https://www.cbr-xml-daily.ru/daily_json.js"

    private fun setDataPage(dataFromPage : List<ValuteJS>) {
        data = ArrayList(dataFromPage.map {it.copy()})
    }

    fun request()  {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val connection = URL(url)
                    .openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val json = connection.inputStream.bufferedReader().readText()

                val page = Gson().fromJson(json, Page::class.java)
                setDataPage(page.Valute.getValutes())

            } catch (error: Error) {
                error.printStackTrace()
            }
        }
        executor.shutdown()
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }
}






