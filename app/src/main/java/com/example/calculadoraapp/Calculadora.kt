package com.example.calculadoraapp
import java.util.Stack
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

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
                    throw Exception("Expresión inválida: los paréntesis no coinciden")
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
                throw Exception("Expresión inválida: los paréntesis no coinciden")
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
                    throw Exception("Expresión inválida: Valores insuficientes.")
                }
                val b = values.pop()
                val a = values.pop()
                values.push(applyOperator(token[0], a, b))
            }
        }

        if (values.size != 1) {
            throw Exception("Expresión inválida: Valores insuficientes.")
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
                if (b == 0.0) throw Exception("División por cero")
                a / b
            }
            '^' -> a.pow(b)
            '√' -> {
                if (a < 0) throw Exception("Valor negativo de una raíz")
                sqrt(a)
            }
            'e' -> a * exp(b)
            else -> throw Exception("Operador desconocido: $operator")
        }
    }
}

