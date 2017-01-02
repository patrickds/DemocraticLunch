package patrickds.github.democraticlunch.google.playservices

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import javax.inject.Inject

open class GooglePlayServices
@Inject constructor(context: Context) {

    val apiClient: GoogleApiClient
    private val _connection: Observable<eConnectionResult>

    init {
        apiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build()

        _connection = Observable.create<eConnectionResult> { emitter ->

            apiClient.registerConnectionCallbacks(
                    object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(bundle: Bundle?) {
                            emitter.onNext(eConnectionResult.SUCCESS)
                            emitter.onComplete()
                        }

                        override fun onConnectionSuspended(p0: Int) {
                            emitter.onError(PlayServicesConnectionSuspendedException())
                        }
                    })

            apiClient.registerConnectionFailedListener { result ->
                emitter.onError(PlayServicesConnectionFailedException(result.errorMessage, result.errorCode))
            }

            apiClient.connect()
        }
    }

    open fun connect() = _connection
}