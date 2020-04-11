import com.zhan.mvp.common.Intent
import com.zhan.mvp.common.Preference

/**
 * @author: bruce
 * @project: KtArmor-master
 * @package:
 * @description:
 * @date: 2020/4/11
 * @time:  11:10
 */
object Settings {
    var password:String by Preference("1","password")
    var username:String by Preference("2","lt")

}