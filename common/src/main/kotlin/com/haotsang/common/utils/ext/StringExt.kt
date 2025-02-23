package com.haotsang.common.utils.ext


/**
 * 返回字符串最后一个.后面的内容
 */
val String.extension: String
    get() = this.substringAfterLast('.', "")

/**
 * 返回字符串.之前的内容
 */
val String.nameWithoutExtension: String
    get() = this.substringBeforeLast(".")

/**
 * 判断是否是中文字符
 */
fun Char.isChinese(): Boolean {
    val unicodeBlock = Character.UnicodeBlock.of(this)
    if (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
        || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
    ) // 中日韩象形文字
    {
        return true
    }
    return false
}
