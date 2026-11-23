package com.example.appAsistencias.Modelo

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.biometric.BiometricManager
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class clsHuellaModelo(private val context: Context) {
    private val prefs = context.getSharedPreferences("huella", Context.MODE_PRIVATE)
    private val alias = "llave_huella"

    // ¿El teléfono tiene una huella registrada y lista para usar?
    fun puedeUsarHuella(): Boolean {
        val estado = BiometricManager.from(context)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        return estado == BiometricManager.BIOMETRIC_SUCCESS
    }

    // ¿Ya se activó la huella en esta app?
    fun estaActivada(): Boolean {
        return prefs.contains("clave")
    }

    // Llave guardada en el Keystore: solo se puede usar después de validar la huella
    private fun obtenerLlave(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        if (keyStore.containsAlias(alias)) {
            return keyStore.getKey(alias, null) as SecretKey
        }
        val generador = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        generador.init(
            KeyGenParameterSpec.Builder(
                alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationRequired(true)       // pide huella cada vez
                .setInvalidatedByBiometricEnrollment(true) // si agregan otra huella, se invalida
                .build()
        )
        return generador.generateKey()
    }

    // Cipher listo para cifrar (se activa con la huella)
    fun cipherParaCifrar(): Cipher {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, obtenerLlave())
        return cipher
    }

    // Cipher listo para descifrar (usa el IV que se guardó al cifrar)
    fun cipherParaDescifrar(): Cipher {
        val iv = Base64.decode(prefs.getString("iv", ""), Base64.NO_WRAP)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, obtenerLlave(), GCMParameterSpec(128, iv))
        return cipher
    }

    // Guarda el correo y la contraseña cifrada
    fun guardar(correo: String, clave: String, cipher: Cipher) {
        val cifrada = cipher.doFinal(clave.toByteArray())
        prefs.edit()
            .putString("correo", correo)
            .putString("clave", Base64.encodeToString(cifrada, Base64.NO_WRAP))
            .putString("iv", Base64.encodeToString(cipher.iv, Base64.NO_WRAP))
            .apply()
    }

    fun obtenerCorreo(): String {
        return prefs.getString("correo", "") ?: ""
    }

    fun descifrarClave(cipher: Cipher): String {
        val cifrada = Base64.decode(prefs.getString("clave", ""), Base64.NO_WRAP)
        return String(cipher.doFinal(cifrada))
    }

    // Borra los datos guardados y la llave del Keystore
    fun desactivar() {
        prefs.edit().clear().apply()
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        keyStore.deleteEntry(alias)
    }
}
