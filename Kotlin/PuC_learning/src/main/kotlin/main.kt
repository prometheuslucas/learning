
sealed class Expression{
    data class Number(val number : Int) : Expression()
    data class Addition(val summand_1: Expression,val summand_2: Expression) : Expression()
}

fun evaluate(expression: Expression): Int{
    return when(expression){
        is Expression.Number -> expression.number
        is Expression.Addition -> evaluate(expression.summand_1) + evaluate(expression.summand_2)
    }
}

fun main(args: Array<String>) {
    val addition = Expression.Addition(
            Expression.Addition(
                    Expression.Number(1),
                    Expression.Number(9)
            ),
            Expression.Number(3)
    )
    println("$addition = ${evaluate(addition)}")
}