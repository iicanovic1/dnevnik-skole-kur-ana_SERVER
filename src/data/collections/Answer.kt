package ba.unsa.etf.data.collections

import ba.unsa.etf.data.collections.Surah.*

data class Answer (
    val type: AnswerType,
    val juzNumber: Juz,
    val surah: Surah,
    val ajehMin : Int?,
    val ajehMax : Int?,
    val date: Long,
    val mark: Int
        )

enum class AnswerType {
    SURAH,
    JUZ,
    AJEH
}

enum class Surah (var surahName : String, val numberOfAjat : Int?) {
    SURAH_NULL("Odgovor bez sure",null),
    AL_FATIHA("1.AL-FATIHA",7),
    AL_BEKARA("2.AL-BEKARA",286),
    ALI_IMRAN("3.ALI-IMRAN",200),
    AN_NISA("4.AN-NISA",176),
    AL_MAIDA("5.AL-MAIDA",120),
    AL_ANAM("6.AL-ANAM",165),
    AL_ARAF("7.AL-ARAF",206),
    AL_ENFAL("8.AL-ENFAL",75),
    AT_TEVBA("9.AT-TEVBA",129),
    JUNUS("10.JUNUS",109),
    HUD("11.HUD",123),
    JUSUF("12.JUSUF",111),
    AR_RAD("13.AR-RAD",43),
    IBRAHIM("14.IBRAHIM",52),
    AL_HIJR("15.AL-HIDŽR",99),
    AN_NAHL("16.AN-NAHL",128),
    AL_ISRA("17.AL-ISRA",111),
    AL_KEHF("18.AL-KEHF",110),
    MERJEM("19.MERJEM",98);

    override fun toString(): String {
        return surahName
    }
}

enum class Juz (val juzName : String, val surahs : List<Surah>) {
    JUZ_NULL("Odaberite Džuz", emptyList()),
    JUZ1("Džuz 1", listOf(AL_FATIHA,AL_BEKARA)),
    JUZ2("Džuz 2", listOf(AL_BEKARA)),
    JUZ3("Džuz 3", listOf(ALI_IMRAN)),
    JUZ4("Džuz 4", listOf(AN_NISA)),
    JUZ5("Džuz 5", listOf(AN_NISA)),
    JUZ6("Džuz 6", listOf(AL_MAIDA)),
    JUZ7("Džuz 7", listOf(AL_ANAM)),
    JUZ8("Džuz 8", listOf(AL_ARAF)),
    JUZ9("Džuz 9", listOf(AL_ENFAL)),
    JUZ10("Džuz 10", listOf(AT_TEVBA)),
    JUZ11("Džuz 11", listOf(JUNUS,HUD)),
    JUZ12("Džuz 12", listOf(JUSUF)),
    JUZ13("Džuz 13", listOf(AR_RAD, IBRAHIM)),
    JUZ14("Džuz 14", listOf(AL_HIJR, AN_NAHL)),
    JUZ15("Džuz 15", listOf(AL_ISRA, AL_KEHF)),
    JUZ16("Džuz 16", listOf(MERJEM));

    override fun toString(): String {
        return juzName
    }

}