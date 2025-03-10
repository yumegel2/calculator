import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var displayText by remember { mutableStateOf("0") }
    var currentNumber by remember { mutableStateOf(0.0) }
    var pendingOperation by remember { mutableStateOf("") }
    var isNewNumber by remember { mutableStateOf(true) }

    fun handleNumberInput(number: String) {
        if (isNewNumber) {
            displayText = number
            isNewNumber = false
        } else {
            displayText += number
        }
    }

    fun handleOperation(operation: String) {
        currentNumber = displayText.toDouble()
        pendingOperation = operation
        isNewNumber = true
    }

    fun calculateResult() {
        val secondNumber = displayText.toDouble()
        when (pendingOperation) {
            "+" -> displayText = (currentNumber + secondNumber).toString()
            "-" -> displayText = (currentNumber - secondNumber).toString()
            "×" -> displayText = (currentNumber * secondNumber).toString()
            "÷" -> displayText = (currentNumber / secondNumber).toString()
        }
        isNewNumber = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        // Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = displayText,
                fontSize = 48.sp,
                color = Color.White,
                textAlign = TextAlign.End
            )
        }

        // Buttons
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.weight(2f)
            {
                items(20) { index ->
                    val buttonText = when (index) {
                        0 -> "C"
                        1 -> "0"
                        2 -> "%"
                        3 -> "÷"
                        4 -> "7"
                        5 -> "8"
                        6 -> "9"
                        7 -> "×"
                        8 -> "4"
                        9 -> "5"
                        10 -> "6"
                        11 -> "-"
                        12 -> "1"
                        13 -> "2"
                        14 -> "3"
                        15 -> "+"
                        16 -> "±"
                        17 -> "0"
                        18 -> "."
                        19 -> "="
                        else -> ""
                    }

                    val backgroundColor = when {
                        buttonText in listOf("C", "±", "%") -> Color.LightGray
                        buttonText in listOf("÷", "×", "-", "+", "=") -> Color(0xFFFF9800)
                        else -> Color(0xFFE0E0E0)
                    }

                    TextButton(
                        onClick = {
                            when {
                                buttonText in listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".") -> handleNumberInput(buttonText)
                                buttonText in listOf("÷", "×", "-", "+") -> handleOperation(buttonText)
                                buttonText == "=" -> calculateResult()
                                buttonText == "C" -> {
                                    displayText = "0"
                                    currentNumber = 0.0
                                    pendingOperation = ""
                                    isNewNumber = true
                                }
                                buttonText == "±" -> {
                                    displayText = (-displayText.toDouble()).toString()
                                }
                                buttonText == "%" -> {
                                    displayText = (displayText.toDouble() / 100).toString()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = buttonText,
                            fontSize = 24.sp,
                            color = when {
                                buttonText in listOf("C", "±", "%") -> Color.Black
                                else -> Color.White
                            }
                        )
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorApp() {
    CalculatorApp()
}