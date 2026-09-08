package bo.edu.uajms.sistemasbjo.programacionmovil.jeremaus.super_xamen

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private val filas = 4
    private val columnas = 4

    private lateinit var botones: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botones = listOf(
            findViewById(R.id.btnCasilla0), findViewById(R.id.btnCasilla1), findViewById(R.id.btnCasilla2), findViewById(R.id.btnCasilla3),
            findViewById(R.id.btnCasilla4), findViewById(R.id.btnCasilla5), findViewById(R.id.btnCasilla6), findViewById(R.id.btnCasilla7),
            findViewById(R.id.btnCasilla8), findViewById(R.id.btnCasilla9), findViewById(R.id.btnCasilla10), findViewById(R.id.btnCasilla11),
            findViewById(R.id.btnCasilla12), findViewById(R.id.btnCasilla13), findViewById(R.id.btnCasilla14), findViewById(R.id.btnCasilla15)
        )

        // se calcula la fila y columna de cada botón, igual que en Tres en Raya
        for (i in botones.indices) {
            val fila = i / columnas
            val columna = i % columnas
            botones[i].setOnClickListener {
                click(fila, columna, botones[i])
            }
        }
    }

    // momazo de no hace nada en texto xddddd
    private fun click(fila: Int, columna: Int, boton: Button) {
        Log.d("Click", "Casilla ($fila, $columna) = ${boton.text}")
    }
}
