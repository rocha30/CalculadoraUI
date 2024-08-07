import java.util.Stack

class Calculadora {

    @Throws(Exception::class)
    fun evaluate(expression: String): Double {
        return evaluatePostfix(infixToPostfix(expression))
    }

    @Throws(Exception::class)
    private fun infixToPostfix(expression: String): String {
        val operators = Stack<Char>()
        val output = StringBuilder()
        val tokens = expression.toCharArray()

        for (token in tokens) {
            if (Character.isWhitespace(token)) continue

            if (Character.isDigit(token) || token == '.') {
                output.append(token)
            } else if (token == '(') {
                operators.push(token)
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.append(' ').append(operators.pop())
                }
                if (operators.isEmpty() || operators.peek() != '(') {
                    throw Exception("Expresión invalidad: los parentesís no coinciden")
                }
                operators.pop()
            } else if (isOperator(token)) {
                output.append(' ')
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                    output.append(operators.pop()).append(' ')
                }
                operators.push(token)
            }
        }

        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw Exception("Expresión invalidad: los parentesís no coinciden")
            }
            output.append(' ').append(operators.pop())
        }

        return output.toString()
    }

    @Throws(Exception::class)
    private fun evaluatePostfix(postfix: String): Double {
        val values = Stack<Double>()
        val tokens = postfix.split("\\s+".toRegex()).toTypedArray()

        for (token in tokens) {
            if (token.isEmpty()) continue

            if (isNumber(token)) {
                values.push(token.toDouble())
            } else if (isOperator(token[0])) {
                if (values.size < 2) {
                    throw Exception("Expresión invalida: Valores insuficientes.")
                }
                val b = values.pop()
                val a = values.pop()
                values.push(applyOperator(token[0], a, b))
            }
        }

        if (values.size != 1) {
            throw Exception("Expresión invalida: Valores insuficientes.")
        }

        return values.pop()
    }

    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '√' || c == 'e'
    }

    private fun precedence(c: Char): Int {
        return when (c) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            '√', 'e' -> 4
            else -> -1
        }
    }

    private fun isNumber(str: String): Boolean {
        return try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    @Throws(Exception::class)
    private fun applyOperator(operator: Char, a: Double, b: Double): Double {
        return when (operator) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw Exception("Division por cero")
                a / b
            }
            '^' -> Math.pow(a, b)
            '√' -> {
                if (a < 0) throw Exception("Valor negativo de una raiz")
                Math.sqrt(a)
            }
            'e' -> a * Math.exp(b)
            else -> throw Exception("Operador desconocido: $operator")
        }
    }
}
