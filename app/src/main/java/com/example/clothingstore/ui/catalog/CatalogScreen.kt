package com.example.clothingstore.ui.catalog

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clothingstore.data.DataRepository
import com.example.clothingstore.model.Product
import androidx.compose.material.icons.filled.ShoppingCart

@Composable
fun CatalogScreen(
    onProductClick: (Product) -> Unit
) {
    val context = LocalContext.current
    val products = DataRepository.productsList

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Catálogo de Temporada",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // items del producto
            items(products) { product ->
                ProductCard(product = product, onAddToCart = {
                    DataRepository.addToCart(product)
                    Toast.makeText(context, "Agregado: ${product.name}", Toast.LENGTH_SHORT).show()
                })
            }

            // botón de Siguiente Página
            item(span = { GridItemSpan(2) }) {
                Button(
                    onClick = {
                        Toast.makeText(context, "Cargando más productos...", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth()
                ) {
                    Text("Siguiente Página")
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(80.dp).padding(8.dp),
                tint = Color.Gray
            )

            Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
            Text(text = "$ ${product.price.toInt()}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("Agregar", fontSize = 12.sp)
            }
        }
    }
}