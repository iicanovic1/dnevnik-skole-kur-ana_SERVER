package ba.unsa.etf.security

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

fun getHashWithSalt(stringToHash: String, saltLength: Int = 32): String {
    val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
    val saltAsHex = Hex.encodeHex(salt)
    val hash = DigestUtils.shaHex("$saltAsHex$stringToHash")
    return "$saltAsHex:$hash"
}

fun checkHashForPassword(password: String, hashWitSalt: String) : Boolean {
    val  hashAndSalt = hashWitSalt.split(":")
    val salt = hashAndSalt[0]
    val hash = hashAndSalt[1]
    val passwordHash = DigestUtils.shaHex("$salt$password")
    return  hash == passwordHash
}