package com.avtomatorgovli.core.data.security

import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordHasher @Inject constructor() {
    fun hash(value: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(value.toByteArray())
            .joinToString(separator = "") { byte ->
                "%02x".format(byte)
            }
    }
}
