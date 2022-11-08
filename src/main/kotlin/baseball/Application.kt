package baseball

import camp.nextstep.edu.missionutils.Randoms

const val gameStart = 0
const val gameInProgress = 1
const val gameScoreCount = 2
const val gameScoreNothing = 3
const val gameEnd = 4
const val gameReplay = 5
var ballCount = 0
var strikeCount = 0


fun main() {

    var gameCoin = true

    while (gameCoin) {
        compareNum()
        printState(gameReplay)
        gameCoin = restartBoolean()
    }
}


fun restartBoolean(): Boolean {
    val input = readLine()?.toInt()
    if ((input != 1) and (input != 2)) {
        printState(gameReplay)
    }

    return when (input) {
        1 -> true
        2 -> false
        else -> restartBoolean()
    }

}

fun compareNum() {
    printState(gameStart)
    val comNum = getComNum()

    while (true) {
        printState(gameInProgress)
        val userNum = getUserNum()

        ballCount(userNum, comNum)
        strikeCount(userNum, comNum)

        if ((ballCount == 0) and (strikeCount == 0)) {
            printState(gameScoreNothing)
        } else printState(gameScoreCount)

        if (strikeCount == 3) break
    }

    printState(gameEnd)
}

fun getComNum(): MutableList<Int> {
    val computerNum = mutableListOf<Int>()
    while (computerNum.size < 3) {
        val randomNumber = Randoms.pickNumberInRange(1, 9)
        if (!computerNum.contains(randomNumber)) {
            computerNum.add(randomNumber)
        }
    }
    return computerNum
}

fun printState(state: Int) {
    when (state) {
        gameStart -> println("숫자 야구 게임을 시작합니다.")
        gameInProgress -> print("숫자를 입력해주세요 : ")
        gameScoreCount -> println("$ballCount 볼 $strikeCount 스트라이크")
        gameScoreNothing -> println("낫싱")
        gameEnd -> println("3개의 숫자를 모두 맞히셨습니다! 게임종료")
        gameReplay -> println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")
    }
}

fun getUserNum(): List<Int> {
    val userNum = readLine()!!.map { it.digitToInt() }

    inputException(userNum)
    return userNum
}

fun ballCount(userNum: List<Int>, comNum: List<Int>) {
    ballCount = 0

    for (element in userNum) {
        if (comNum.contains(element)) {
            ballCount++
        }
    }
}

fun strikeCount(userNum: List<Int>, comNum: List<Int>) {
    strikeCount = 0

    for (index in userNum.indices) {
        if (userNum[index] == comNum[index]) {
            strikeCount++
            ballCount--
        }
    }
}

fun inputException(userNum: List<Int>) {
    if (userNum.contains(0)) throw IllegalArgumentException("1 ~ 9 사이의 숫자만 입력가능합니다.")
    if (userNum.size != 3) throw IllegalArgumentException("1 ~ 9 사이의 숫자, 3개를 입력해야합니다.")
    if (userNum.distinct().size != 3) throw IllegalArgumentException("중복값이 존재합니다.")
}
