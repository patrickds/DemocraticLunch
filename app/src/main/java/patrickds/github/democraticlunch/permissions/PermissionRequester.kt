package patrickds.github.democraticlunch.permissions

import android.app.Activity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import javax.inject.Inject

open class PermissionRequester
@Inject constructor(_activity: Activity) {

    private val _rxPermissions: RxPermissions

    init {
        _rxPermissions = RxPermissions(_activity)
        _rxPermissions.setLogging(false)
    }

    open fun request(permission: ePermission): Observable<Boolean> {
        return _rxPermissions.request(permission.toAndroidPermission())
    }
}