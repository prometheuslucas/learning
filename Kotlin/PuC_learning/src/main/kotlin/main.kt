sealed class Expression {
    data class Number(val number: Int) : Expression()
    data class Addition(val summand_1: Expression, val summand_2: Expression) : Expression()
    data class Multiplication(val factor_1: Expression, val factor_2: Expression) : Expression()
    data class Negation(val subtrahend: Expression) : Expression()
}

sealed class SmallerExpression {
    data class Number(val number: Int) : SmallerExpression()
    data class Addition(val summand_1: SmallerExpression, val summand_2: SmallerExpression) : SmallerExpression()
    data class Multiplication(val factor_1: SmallerExpression, val factor_2: SmallerExpression) : SmallerExpression()
}
// SmallerExpression without Multiplication lower to be solved
//sealed class Mini{
//    data class Number(val number: Int) : Mini()
//    data class Addition(val summand_1: Mini, val summand_2: Mini) : Mini()
//}

fun evaluate(expression: Expression): Int {
    return evaluateSmall(lower(expression))
}

fun evaluateSmall(expression: SmallerExpression): Int {
    return when (expression) {
        is SmallerExpression.Number -> expression.number
        is SmallerExpression.Addition -> evaluateSmall(expression.summand_1) + evaluateSmall(expression.summand_2)
        is SmallerExpression.Multiplication -> evaluateSmall(expression.factor_1) * evaluateSmall(expression.factor_2)
    }
}
// evaluateSmall without multiplication lower to be solved
//fun evaluateMini(expression: Mini): Int{
//    return when(expression){
//        is Mini.Addition -> evaluateMini(expression.summand_1) + evaluateMini(expression.summand_2)
//        is Mini.Number -> expression.number
//    }
//}

fun lower(expression: Expression): SmallerExpression {
    return when (expression) {
        is Expression.Addition -> SmallerExpression.Addition(
                lower(expression.summand_1),
                lower(expression.summand_2)
        )
        is Expression.Multiplication -> SmallerExpression.Multiplication(
                lower(expression.factor_1),
                lower(expression.factor_2)
        )
        is Expression.Negation -> SmallerExpression.Multiplication(
                SmallerExpression.Number(-1),
                lower(expression.subtrahend)
        )
        is Expression.Number -> SmallerExpression.Number(expression.number)
    }
}
// lower without multiplication to be solved
//fun lowerMini(small: SmallerExpression): Mini{
//    return when (small){
//        is SmallerExpression.Addition -> Mini.Addition(
//                lowerMini(small.summand_1),
//                lowerMini(small.summand_2)
//        )
//        is SmallerExpression.Multiplication ->
//        is SmallerExpression.Number -> Mini.Number(small.number)
//    }
//}

fun main(args: Array<String>) {
//    val addition = Expression.Addition(
//            Expression.Multiplication(
//                    Expression.Negation(Expression.Number(3)),
//                    Expression.Number(9)
//            ),
//            Expression.Number(3)
//    )
//    println("$addition = ${evaluateSmall(addition)}")
    val vExpression =
            Expression.Negation(
                    Expression.Multiplication(
                            Expression.Addition(
                                    Expression.Number(29),
                                    Expression.Negation(
                                            Expression.Multiplication(
                                                    Expression.Number(13),
                                                    Expression.Number(4)
                                            )
                                    )
                            ),
                            Expression.Number(42)
                    )
            )
    val vLowered = lower(vExpression)
    println("$vExpression => $vLowered = ${evaluate(vExpression)}")
}