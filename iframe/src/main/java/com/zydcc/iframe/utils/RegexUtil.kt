package com.zydcc.iframe.utils

import com.zydcc.iframe.exception.ZFrameException
import com.zydcc.iframe.utils.func.Func1
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.Collection


/**
 * =======================================
 * 正则工具类
 * 常用正则表达式代码提供 验证数字：^[0-9]*$ <br></br>
 * 验证n位的数字：^\d{n}$ <br></br>
 * 验证至少n位数字：^\d{n,}$ <br></br>
 * 验证m-n位的数字：^\d{m,n}$ <br></br>
 * 验证零和非零开头的数字：^(0|[1-9][0-9]*)$ <br></br>
 * 验证有两位小数的正实数：^[0-9]+(.[0-9]{2})?$ <br></br>
 * 验证有1-3位小数的正实数：^[0-9]+(.[0-9]{1,3})?$ <br></br>
 * 验证非零的正整数：^\+?[1-9][0-9]*$ <br></br>
 * 验证非零的负整数：^\-[1-9][0-9]*$ <br></br>
 * 验证非负整数（正整数 + 0） ^\d+$ <br></br>
 * 验证非正整数（负整数 + 0） ^((-\d+)|(0+))$ <br></br>
 * 验证长度为3的字符：^.{3}$ <br></br>
 * 验证由26个英文字母组成的字符串：^[A-Za-z]+$ <br></br>
 * 验证由26个大写英文字母组成的字符串：^[A-Z]+$ <br></br>
 * 验证由26个小写英文字母组成的字符串：^[a-z]+$ <br></br>
 * 验证由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$ <br></br>
 * 验证由数字、26个英文字母或者下划线组成的字符串：^\w+$ <br></br>
 * 验证用户密码:^[a-zA-Z]\w{5,17}$ 正确格式为：以字母开头，长度在6-18之间，只能包含字符、数字和下划线。 <br></br>
 * 验证是否含有 ^%&',;=?$\" 等字符：[^%&',;=?$\x22]+ <br></br>
 * 验证汉字：^[\u4E00-\u9FA5\uF900-\uFA2D]+$ <br></br>
 * 验证Email地址：^\w+[-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$ <br></br>
 * 验证InternetURL：^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
 * ；^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$ <br></br>
 * 验证手机号码：^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$：--正确格式为：XXXX-XXXXXXX，XXXX-
 * XXXXXXXX，XXX-XXXXXXX，XXX-XXXXXXXX，XXXXXXX，XXXXXXXX。 <br></br>
 * 验证身份证号（15位或18位数字）：^\d{15}|\d{}18$ <br></br>
 * 验证一年的12个月：^(0?[1-9]|1[0-2])$ 正确格式为：“01”-“09”和“1”“12” <br></br>
 * 验证一个月的31天：^((0?[1-9])|((1|2)[0-9])|30|31)$ 正确格式为：01、09和1、31。 <br></br>
 * 整数：^-?\d+$ <br></br>
 * 非负浮点数（正浮点数 + 0）：^\d+(\.\d+)?$ <br></br>
 * 正浮点数
 * ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9
 * ][0-9]*))$ <br></br>
 * 非正浮点数（负浮点数 + 0） ^((-\d+(\.\d+)?)|(0+(\.0+)?))$ <br></br>
 * 负浮点数
 * ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1
 * -9][0-9]*)))$ <br></br>
 * 浮点数 ^(-?\d+)(\.\d+)?$ <br></br>
 * Create by ningsikai 2020/6/4:4:45 PM
 * ========================================
 */
object RegexUtil {

    /**
     * 分组
     */
    val GROUP_VAR = Pattern.compile("\\$(\\d+)")

    /**
     * 正则中需要被转义的关键字
     */
    val RE_KEYS: Set<Char> = HashSet(
        listOf(
            '$',
            '(',
            ')',
            '*',
            '+',
            '.',
            '[',
            ']',
            '?',
            '\\',
            '^',
            '{',
            '}',
            '|'
        )
    )

    /**
     * Pattern池
     */
    private val POOL =
        HashMap<RegexWithFlag, Pattern?>()

    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return 星号替换的手机号
     */
    fun phoneNoHide(phone: String): String {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2")
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return 星号替换的银行卡号
     */
    fun cardIdHide(cardId: String): String {
        return cardId.replace("\\d{15}(\\d{3})".toRegex(), "**** **** **** **** $1")
    }

    /**
     * 身份证号，中间10位星号替换
     *
     * @param id 身份证号
     * @return 星号替换的身份证号
     */
    fun idHide(id: String): String {
        return id.replace("(\\d{4})\\d{10}(\\d{4})".toRegex(), "$1** **** ****$2")
    }

    /**
     * 是否为车牌号（沪A88888）
     *
     * @param vehicleNo 车牌号
     * @return 是否为车牌号
     */
    fun isVehicleNo(vehicleNo: String?): Boolean {
        val pattern =
            Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$")
        return pattern.matcher(vehicleNo).find()
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    fun isIdCard(idCard: String?): Boolean {
        val regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}"
        return Pattern.matches(regex, idCard)
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *
     * 移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     * 、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *
     * 联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *
     * 电信的号段：133、153、180（未启用）、189
     *
     * 虚拟运营商好短：17
     * @return 验证成功返回true，验证失败返回false
     */
    fun isMobile(mobile: String?): Boolean {
        val regex = "(\\+\\d+)?1[345789]\\d{9}$"
        return Pattern.matches(regex, mobile)
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *
     * **国家（地区） 代码 ：**标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     * 数字之后是空格分隔的国家（地区）代码。
     *
     * **区号（城市代码）：**这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。
     *
     * **电话号码：**这包含从 0 到 9 的一个或多个数字
     * @return 验证成功返回true，验证失败返回false
     */
    fun isPhone(phone: String?): Boolean {
        val regex = "(\\+\\d+)?(\\d{3,4}-?)?\\d{7,8}$"
        return Pattern.matches(regex, phone)
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    fun isEmail(email: String?): Boolean {
        val regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?"
        return Pattern.matches(regex, email)
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    fun isDigit(digit: String?): Boolean {
        val regex = "-?[1-9]\\d+"
        return Pattern.matches(regex, digit)
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    fun isDecimals(decimals: String?): Boolean {
        val regex = "-?[1-9]\\d+(\\.\\d+)?"
        return Pattern.matches(regex, decimals)
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    fun isBlankSpace(blankSpace: String?): Boolean {
        val regex = "\\s+"
        return Pattern.matches(regex, blankSpace)
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isChinese(chinese: String?): Boolean {
        val regex = "^[\u4E00-\u9FA5]+$"
        return Pattern.matches(regex, chinese)
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    fun isBirthday(birthday: String?): Boolean {
        val regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}"
        return Pattern.matches(regex, birthday)
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    fun isURL(url: String?): Boolean {
        val regex =
            "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?"
        return Pattern.matches(regex, url)
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    fun isPostcode(postcode: String?): Boolean {
        val regex = "[1-9]\\d{5}"
        return Pattern.matches(regex, postcode)
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    fun isIpAddress(ipAddress: String?): Boolean {
        val regex = "\\d+\\.\\d+\\.\\d+\\.\\d+"
        return Pattern.matches(regex, ipAddress)
    }

    /**
     * 匹配是否为APK包
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isApk(str: String?): Boolean {
        val regex = "^(.*)\\.(apk)$"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为视频文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isVideo(str: String?): Boolean {
        val regex =
            "^(.*)\\.(mpeg-4|h.264|h.265|rmvb|xvid|vp6|h.263|mpeg-1|mpeg-2|avi|" + "mov|mkv|flv|3gp|3g2|asf|wmv|mp4|m4v|tp|ts|mtp|m2t)$"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为音频文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isAudio(str: String?): Boolean {
        val regex = "^(.*)\\.(aac|vorbis|flac|mp3|mp2|wma)$"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为文本文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isTxt(str: String?): Boolean {
        val regex = "^(.*)\\.(txt|xml|html)$"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为压缩文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isZip(str: String?): Boolean {
        val regex = "^(.*)\\.(zip|rar|7z)$"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为Word文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isWord(str: String?): Boolean {
        val regex = "^(.*)\\.(doc|docx)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为PPT文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isPPT(str: String?): Boolean {
        val regex = "^(.*)\\.(ppt|pptx)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为excel文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isExcel(str: String?): Boolean {
        val regex = "^(.*)\\.(xls|xlsx)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为vcf文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isVcf(str: String?): Boolean {
        val regex = "^(.*)\\.(vcf)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为PDF文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isPdf(str: String?): Boolean {
        val regex = "^(.*)\\.(pdf)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为Sql文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isSql(str: String?): Boolean {
        val regex = "^(.*)\\.(sql|db)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为Img文件
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isImg(str: String?): Boolean {
        val regex = "^(.*)\\.(jpg|bmp|png|gif|jpeg|psd)"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否为QQ
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isQQ(str: String?): Boolean {
        val regex = "[1-9][0-9]{4,}"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否首尾含有空白字符
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isStartOrEndContainNone(str: String?): Boolean {
        val regex = "^\\s*|\\s*"
        return Pattern.matches(regex, str)
    }

    /**
     * 匹配是否空白行
     *
     * @param str 匹配字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun isBlankLine(str: String?): Boolean {
        val regex = "\\n\\s*\\r"
        return Pattern.matches(regex, str)
    }


    /**
     * 转义字符，将正则的关键字转义
     *
     * @param c 字符
     * @return 转义后的文本
     */
    fun escape(c: Char): String {
        val builder = StringBuilder()
        if (RE_KEYS.contains(c)) {
            builder.append('\\')
        }
        builder.append(c)
        return builder.toString()
    }

    /**
     * 转义字符串，将正则的关键字转义
     *
     * @param content 文本
     * @return 转义后的文本
     */
    fun escape(content: CharSequence): String {
        if (StringUtil.isBlank(content)) {
            return StringUtil.str(content)
        }
        val builder = StringBuilder()
        val len = content.length
        var current: Char
        for (i in 0 until len) {
            current = content[i]
            if (RE_KEYS.contains(current)) {
                builder.append('\\')
            }
            builder.append(current)
        }
        return builder.toString()
    }
    /**
     * 先从Pattern池中查找正则对应的[Pattern]，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @param flags 正则标识位集合 [Pattern]
     * @return [Pattern]
     */
    /**
     * 先从Pattern池中查找正则对应的[Pattern]，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @return [Pattern]
     */
    @JvmOverloads
    operator fun get(regex: String?, flags: Int = 0): Pattern? {
        val regexWithFlag = RegexWithFlag(regex, flags)
        var pattern =
            POOL[regexWithFlag]
        if (null == pattern) {
            pattern = Pattern.compile(regex, flags)
            POOL[regexWithFlag] = pattern
        }
        return pattern
    }

    /**
     * 移除缓存
     *
     * @param regex 正则
     * @param flags 标识
     * @return 移除的{@link Pattern}，可能为`null`
     */
    fun remove(regex: String?, flags: Int): Pattern? {
        return POOL.remove(RegexWithFlag(regex, flags))
    }

    /**
     * 清空缓存池
     */
    fun clear() {
        POOL.clear()
    }

    /**
     * 正则表达式和正则标识位的包装
     *
     * @author Looly
     */
    private class RegexWithFlag
    /**
     * 构造
     *
     * @param regex 正则
     * @param flag  标识
     */(private val regex: String?, private val flag: Int) {
        override fun hashCode(): Int {
            val prime = 31
            var result = 1
            result = prime * result + flag
            result = prime * result + (regex?.hashCode() ?: 0)
            return result
        }

        override fun equals(obj: Any?): Boolean {
            if (this === obj) {
                return true
            }
            if (obj == null) {
                return false
            }
            if (javaClass != obj.javaClass) {
                return false
            }
            val other = obj as RegexWithFlag
            if (flag != other.flag) {
                return false
            }
            return if (regex == null) {
                other.regex == null
            } else {
                regex == other.regex
            }
        }

    }
}