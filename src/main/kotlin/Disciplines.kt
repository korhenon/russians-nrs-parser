enum class Disciplines(val discipline: String) {
    TwoByTwo("Кубик 2x2x2"),
    ThreeByThree("Кубик 3x3x3"),
    FourByFour("Кубик 4x4x4"),
    FiveByFive("Кубик 5x5x5"),
    SixBySix("Кубик 6x6x6"),
    SevenBySeven("Кубик 7x7x7"),
    ThreeByThreeBlindfolded("3x3x3 вслепую"),
    FourByFourBlindfolded("4x4x4 вслепую"),
    FiveByFiveBlindfolded("5x5x5 вслепую"),
    ThreeByThreeFewestMoves("Сборка 3x3x3 на количество ходов"),
    ThreeByThreeOneHanded("3x3x3 одной рукой"),
    Clock("Clock"),
    Megaminx("Мегаминкс"),
    Pyraminx("Пираминкс"),
    Skewb("Скьюб"),
    SquareOne("Square-1"),
}

fun getDiscipline(s: String): Disciplines {
    for (d in Disciplines.values()) {
        if (s == d.discipline) {
            return d
        }
    }
    return  Disciplines.Clock
}