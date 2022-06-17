fun main() {
    val currentQuestion = generateQuestion()
    println(currentQuestion.statement())
    println(currentQuestion.result)
}

fun generateQuestion(): Question {
    val symbol: MathSymbol = MathSymbol.random()
    val firstNumber: Int = if (symbol == MathSymbol.times) (2..50).random() else (2..100).random()
    val secondNumber: Int = if (symbol == MathSymbol.times) (2..50).random() else (2..100).random()
    
    return Question(symbol, firstNumber, secondNumber, symbol.calculate(firstNumber, secondNumber))
}

data class Question(
    val symbol: MathSymbol,
    val firstNumber: Int,
    val secondNumber: Int,
    val result: Int
)

fun Question.statement(): String = "Quanto é $firstNumber ${symbol.toString()} $secondNumber ?"

enum class MathSymbol(var type: String){
    plus("+"), minus("-"), times("*");
    
    override fun toString(): String {
        return type
    }
    
    fun calculate(firstNumber: Int, secondNumber: Int): Int {
        return when (type) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            else -> firstNumber * secondNumber
        }
    }
    
    companion object {
        fun random(): MathSymbol {
            return when ((0..2).random()) {
                0 -> MathSymbol.plus
                1 -> MathSymbol.minus
                else -> MathSymbol.times
            }
        }
    }
}