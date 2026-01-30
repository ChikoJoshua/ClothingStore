package com.example.clothingstore.ui.pedido

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// IMPORTANTE: Asegúrate de que estos imports coincidan con tus archivos
import com.example.clothingstore.data.DataRepository
import com.example.clothingstore.data.Pedido
import com.example.clothingstore.ui.Screen
import java.text.SimpleDateFormat
import java.util.*

enum class MetodoEntrega { RETIRO, DESPACHO, NINGUNO }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoScreen(onVolverAlCatalogo: () -> Unit) {
    var metodoSeleccionado by remember { mutableStateOf(MetodoEntrega.NINGUNO) }
    var sucursalSeleccionada by remember { mutableStateOf("") }
    var mostrarAlertaExito by remember { mutableStateOf(false) }

    // Lista de de sucursales
    val sucursales = listOf("Sucursal Mall Costanera", "Sucursal Santiago Centro", "Sucursal Concepción", "Sucursal Chillán", "Sucursal Antonio Varas", "Sucursal Las Condes")

    // Alerta de compra exitosa
    if (mostrarAlertaExito) {
        AlertDialog(
            onDismissRequest = {  },
            confirmButton = {
                Button(onClick = {
                    mostrarAlertaExito = false
                    onVolverAlCatalogo()
                }) {
                    Text("Volver a la Tienda")
                }
            },
            title = { Text("¡Compra realizada con éxito!") },
            text = { Text("Tu pedido ha sido registrado y ya puedes verlo en tu historial.") }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Finalizar Pedido", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Seleccione cómo desea recibir su compra:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // OPCIÓN RETIRO
            OpcionCard(
                titulo = "Retiro en Sucursal",
                subtitulo = "Gratis - Disponible hoy",
                icono = Icons.Default.LocationOn,
                seleccionado = metodoSeleccionado == MetodoEntrega.RETIRO,
                onClick = { metodoSeleccionado = MetodoEntrega.RETIRO }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // OPCIÓN DESPACHO
            OpcionCard(
                titulo = "Despacho a Domicilio",
                subtitulo = "Llega en 24 a 48 horas",
                icono = Icons.Default.Home,
                seleccionado = metodoSeleccionado == MetodoEntrega.DESPACHO,
                onClick = { metodoSeleccionado = MetodoEntrega.DESPACHO }
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (metodoSeleccionado) {
                MetodoEntrega.RETIRO -> {
                    SeccionRetiro(
                        sucursales = sucursales,
                        seleccionada = sucursalSeleccionada,
                        onSelect = { sucursalSeleccionada = it },
                        onConfirmar = {
                            val nuevoPedido = Pedido(
                                id = "#${(1000..9999).random()}",
                                fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
                                total = DataRepository.getCartTotal(),
                                metodoEntrega = "Retiro: $sucursalSeleccionada",
                                estado = "Listo para retiro",
                                detalle = "${DataRepository.cartItems.size} productos"
                            )
                            DataRepository.registrarPedido(nuevoPedido)
                            mostrarAlertaExito = true
                        }
                    )
                }
                MetodoEntrega.DESPACHO -> {
                    SeccionDespacho(
                        onConfirmar = { direccion, comuna ->
                            val nuevoPedido = Pedido(
                                id = "#${(1000..9999).random()}",
                                fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
                                total = DataRepository.getCartTotal(),
                                metodoEntrega = "Despacho: $direccion, $comuna",
                                estado = "En despacho",
                                detalle = "${DataRepository.cartItems.size} productos"
                            )
                            DataRepository.registrarPedido(nuevoPedido)
                            mostrarAlertaExito = true
                        }
                    )
                }
                MetodoEntrega.NINGUNO -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Selecciona una opción arriba", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { onVolverAlCatalogo() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seguir comprando")
            }
        }
    }
}

@Composable
fun OpcionCard(
    titulo: String,
    subtitulo: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (seleccionado) ButtonDefaults.outlinedButtonBorder else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(titulo, fontWeight = FontWeight.Bold)
                Text(subtitulo, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SeccionRetiro(
    sucursales: List<String>,
    seleccionada: String,
    onSelect: (String) -> Unit,
    onConfirmar: () -> Unit
) {
    Column {
        Text("Seleccione la sucursal más cercana:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        sucursales.forEach { sucursal ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(sucursal) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = (seleccionada == sucursal), onClick = { onSelect(sucursal) })
                Text(sucursal)
            }
        }
        if (seleccionada.isNotEmpty()) {
            Button(
                onClick = { onConfirmar() },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Confirmar Retiro")
            }
        }
    }
}

@Composable
fun SeccionDespacho(onConfirmar: (String, String) -> Unit) {
    var direccion by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var obs by remember { mutableStateOf("") }

    Column {
        Text("Dirección de Envío", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Calle y número") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = comuna,
            onValueChange = { comuna = it },
            label = { Text("Comuna / Ciudad") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = obs,
            onValueChange = { obs = it },
            label = { Text("Indicaciones (Ej: Depto 402)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onConfirmar(direccion, comuna) },
            modifier = Modifier.fillMaxWidth(),
            enabled = direccion.isNotEmpty() && comuna.isNotEmpty()
        ) {
            Text("Ir a pagar")
        }
    }
}