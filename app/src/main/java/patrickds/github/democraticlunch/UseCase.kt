package patrickds.github.democraticlunch

import io.reactivex.Observable

abstract class UseCase {

    abstract fun <REQUEST : RequestValues, RESPONSE : ResponseValue>
            execute(requestValues: REQUEST): Observable<RESPONSE>

    abstract class RequestValues
    abstract class ResponseValue
}