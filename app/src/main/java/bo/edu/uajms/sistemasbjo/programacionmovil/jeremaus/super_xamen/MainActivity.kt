package bo.edu.uajms.sistemasbjo.programacionmovil.jeremaus.super_xamen

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private val filas = 4
    private val columnas = 4

    private lateinit var botones: Array<Button>
    private lateinit var txvMensaje: TextView
    private lateinit var btnReiniciar: Button
    private lateinit var btnDesordenar: Button
    private lateinit var btnVerificar: Button

    // matriz que guarda en todo momento lo que hay en cada casilla ("1".."15" o "" para el hueco)
    private val tablero = Array(filas) { Array(columnas) { "" } }

    private var filaVacia = 0
    private var columnaVacia = 0

    // guarda "1", "2", ..., "15", "" en el orden inicial, para poder reiniciar
    private lateinit var valoresIniciales: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ids = intArrayOf(
            R.id.btnCasilla0, R.id.btnCasilla1, R.id.btnCasilla2, R.id.btnCasilla3,
            R.id.btnCasilla4, R.id.btnCasilla5, R.id.btnCasilla6, R.id.btnCasilla7,
            R.id.btnCasilla8, R.id.btnCasilla9, R.id.btnCasilla10, R.id.btnCasilla11,
            R.id.btnCasilla12, R.id.btnCasilla13, R.id.btnCasilla14, R.id.btnCasilla15
        )
        botones = Array(16) { i -> findViewById(ids[i]) }

        txvMensaje = findViewById(R.id.TXVMensaje)
        btnReiniciar = findViewById(R.id.btnReiniciar)
        btnDesordenar = findViewById(R.id.btnDesordenar)
        btnVerificar = findViewById(R.id.btnVerificar)

        // copiamos lo que se ve en los botones a la matriz, para que arranque sincronizada
        for (fila in 0 until filas) {
            for (columna in 0 until columnas) {
                val texto = botones[fila * columnas + columna].text.toString()
                tablero[fila][columna] = texto
                if (texto == "") {
                    filaVacia = fila
                    columnaVacia = columna
                }
            }
        }

        // guardamos el orden inicial (1..15 y "") para poder volver a él con Reiniciar
        valoresIniciales = Array(16) { i -> botones[i].text.toString() }

        for (i in botones.indices) {
            val fila = i / columnas
            val columna = i % columnas
            botones[i].setOnClickListener {
                click(fila, columna)
            }
        }

        btnReiniciar.setOnClickListener { reiniciar() }
        btnDesordenar.setOnClickListener { desordenar() }
        btnVerificar.setOnClickListener { verificar() }
    }

    private fun click(fila: Int, columna: Int) {
        // revisamos si el hueco está arriba, abajo, izquierda o derecha de la casilla tocada
        val esVecino =
            (fila - 1 == filaVacia && columna == columnaVacia) ||
            (fila + 1 == filaVacia && columna == columnaVacia) ||
            (fila == filaVacia && columna - 1 == columnaVacia) ||
            (fila == filaVacia && columna + 1 == columnaVacia)

        if (!esVecino) return

        val botonTocado = botones[fila * columnas + columna]
        val botonVacio = botones[filaVacia * columnas + columnaVacia]

        // intercambiamos el texto de los botones
        botonVacio.text = botonTocado.text
        botonTocado.text = ""

        // el mismo intercambio, pero en la matriz
        tablero[filaVacia][columnaVacia] = tablero[fila][columna]
        tablero[fila][columna] = ""

        // ahora el hueco quedó donde estaba la casilla tocada
        filaVacia = fila
        columnaVacia = columna
    }

    // pone los botones y la matriz de vuelta en el orden inicial (1..15 y el hueco al final)
    private fun reiniciar() {
        for (i in botones.indices) {
            botones[i].text = valoresIniciales[i]
            tablero[i / columnas][i % columnas] = valoresIniciales[i]
            if (valoresIniciales[i] == "") {
                filaVacia = i / columnas
                columnaVacia = i % columnas
            }
        }
        txvMensaje.text = getString(R.string.msgReiniciado)
    }

    // reparte los mismos 16 valores en un orden aleatorio
    private fun desordenar() {
        val valoresMezclados = valoresIniciales.toList().shuffled()
        for (i in botones.indices) {
            botones[i].text = valoresMezclados[i]
            tablero[i / columnas][i % columnas] = valoresMezclados[i]
            if (valoresMezclados[i] == "") {
                filaVacia = i / columnas
                columnaVacia = i % columnas
            }
        }
        txvMensaje.text = getString(R.string.msgDesordenado)
    }

    // revisa si el tablero quedó en el mismo orden que al inicio (1..15 y el hueco al final)
    private fun verificar() {
        var ordenado = true
        for (i in botones.indices) {
            if (tablero[i / columnas][i % columnas] != valoresIniciales[i]) {
                ordenado = false
            }
        }
        txvMensaje.text = if (ordenado) getString(R.string.msgOrdenado) else getString(R.string.msgNoOrdenado)
    }
}
