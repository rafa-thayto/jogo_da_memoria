package br.com.fiap.jogodamemoria.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ASSharedPreferences(private val context: Context) {
    private val gson = Gson()
    private val sharedPref: SharedPreferences
    private val name = "game_prefs"
    private val playerName = "player"
    private val ranking = "ranking"

    init {
        sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun savePlayer(name: String) {
        val editor = sharedPref.edit()
        editor.putString(playerName, name)
        editor.apply()
    }

    fun getPlayer(): String? {
        return sharedPref.getString(playerName, "")
    }

    fun updateRanking(item: RankingItem) {
        val rankingList = calculateRankingPosition(getRanking(), item)
        val rankingString = gson.toJson(rankingList)

        val editor = sharedPref.edit()
        editor.putString(ranking, rankingString)
        editor.apply()
    }

    fun getRanking(): ArrayList<RankingItem> {
        val rankingString = sharedPref.getString(ranking, null) ?: return arrayListOf()
        return gson.fromJson(rankingString, object : TypeToken<ArrayList<RankingItem>>() {}.type)
    }

    private fun calculateRankingPosition(ranking: ArrayList<RankingItem>, item: RankingItem): ArrayList<RankingItem> {
        ranking.add(item)

        var sortedRanking = ArrayList(
            ranking.sortedBy { item -> item.score.times(-1) }
        )

        if (ranking.size > 10) {
            sortedRanking = ArrayList(sortedRanking.dropLast(1))
        }

        return  sortedRanking
    }
}