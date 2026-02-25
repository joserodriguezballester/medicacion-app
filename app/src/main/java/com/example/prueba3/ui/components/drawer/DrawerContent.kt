package com.example.prueba3.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(onItemSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(46.dp)
        ) {
            Text("Menu Principal", style = MaterialTheme.typography.titleLarge)
           // Text("", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Recorremos con índice
        DrawerOptions.items.forEachIndexed { index, item ->
            DrawerItem(
                text = item.label,
                icon = item.icon,
                onClick = { onItemSelected(item.route) }
            )
            // Insertamos Divider justo antes del cuarto ítem (índice 3)
            if (index == 3) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}