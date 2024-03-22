package top.myrest.myflow.developer

import java.io.File
import cn.hutool.core.codec.Base32
import cn.hutool.core.codec.Base64
import cn.hutool.core.io.FileUtil
import cn.hutool.core.text.UnicodeUtil
import cn.hutool.core.util.HexUtil
import cn.hutool.crypto.SecureUtil
import top.myrest.myflow.action.BaseDigestActionHandler
import top.myrest.myflow.action.asSuggestionResult
import top.myrest.myflow.action.basicCopyResult

class Base32DigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "base32", logo = "./logos/base32.png", result = Base32.encode(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "base32", logo = "./logos/base32.png", result = Base32.encode(FileUtil.readBytes(file)))
}

class Base32DecodeActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "base32.decode", logo = "./logos/base32.png", result = Base32.decodeStr(content).asSuggestionResult())
}

class Base64DigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "base64", logo = "./logos/base64.png", result = Base64.encode(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "base64", logo = "./logos/base64.png", result = Base64.encode(file))
}

class Base64UrlDigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "base64url", logo = "./logos/base64.png", result = Base64.encodeUrlSafe(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "base64url", logo = "./logos/base64.png", result = Base64.encodeUrlSafe(file))
}

class Base64DecodeActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "base64.decode", logo = "./logos/base64.png", result = Base64.decodeStr(content).asSuggestionResult())
}

class String2UnicodeActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "string2unicode", logo = "./logos/unicode.png", result = UnicodeUtil.toUnicode(content))
}

class Unicode2StringActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "unicode2string", logo = "./logos/unicode.png", result = UnicodeUtil.toString(content).asSuggestionResult())
}

class HexDigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "hex", logo = "./logos/hex.png", result = HexUtil.encodeHexStr(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "hex", logo = "./logos/hex.png", result = HexUtil.encodeHexStr(FileUtil.readBytes(file)))
}

class HexDecodeActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "hex.decode", logo = "./logos/hex.png", result = HexUtil.decodeHexStr(content).asSuggestionResult())
}

class LowerCaseActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "lowercase", logo = "./logos/lowercase.png", result = content.lowercase().asSuggestionResult())
}

class UpperCaseActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "uppercase", logo = "./logos/uppercase.png", result = content.uppercase().asSuggestionResult())
}

class Sha1DigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "sha1", logo = "./logos/sha1.png", result = SecureUtil.sha1(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "sha1", logo = "./logos/sha1.png", result = SecureUtil.sha1(file))
}

class Sha256DigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "sha256", logo = "./logos/sha256.png", result = SecureUtil.sha256(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "sha256", logo = "./logos/sha256.png", result = SecureUtil.sha256(file))
}

class Md5DigestActionHandler : BaseDigestActionHandler() {
    override fun queryDigestAction(content: String) = basicCopyResult(actionId = "md5", logo = "./logos/md5.png", result = SecureUtil.md5(content))
    override fun queryFileDigestAction(file: File) = basicCopyResult(actionId = "md5", logo = "./logos/md5.png", result = SecureUtil.md5(file))
}
