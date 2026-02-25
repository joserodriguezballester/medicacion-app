package com.example.prueba3.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.prueba3.ui.theme.WhatsAppGreen
import com.example.prueba3.viewmodel.MedicinaViewModel

@Composable
fun WhatsAppFab(
    viewModel: MedicinaViewModel,
    context: Context,
    generarMensaje:()-> String,
    modifier: Modifier = Modifier
) {

    FloatingActionButton(
        onClick = {
            val mensaje = viewModel.mensajeCompartir
     //       println("Mensaje a compartir: $mensaje")
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mensaje)
                type = "text/plain"
            }
// Verifica si WhatsApp está instalado
            val pm = context.packageManager
            val whatsappPackages = listOf("com.whatsapp", "com.whatsapp.w4b")
            val installedWhatsAppPackage = whatsappPackages.find { pkg ->
                try {
                    pm.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES)
                    true
                } catch (e: PackageManager.NameNotFoundException) {
                    false
                }
            }
            if (installedWhatsAppPackage != null) {
                intent.setPackage(installedWhatsAppPackage)
        //        println("WhatsApp detectado: $installedWhatsAppPackage")
         //       Toast.makeText(context, "WhatsApp detectado: $installedWhatsAppPackage", Toast.LENGTH_SHORT).show()
            } else {
          //      println("No se encontró WhatsApp")
           //     Toast.makeText(context, "No se encontró WhatsApp", Toast.LENGTH_SHORT).show()
            }
            try {
                context.startActivity(Intent.createChooser(intent, "Compartir con"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No se encontró una app para compartir", Toast.LENGTH_SHORT).show()
            }

                      },
        modifier = modifier,
        containerColor = WhatsAppGreen
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Compartir por WhatsApp"
        )
    }
}
