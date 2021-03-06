package patrickds.github.democraticlunch.google.places

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IGoogleWebService {

    @GET("/maps/api/place/details/json?")
    fun getById(
            @Query("key") key: String,
            @Query("placeid") id: String): Observable<PlaceResult>

    @GET("/maps/api/place/search/json?")
    fun getNearbyPlaces(
            @Query("key") key: String,
            @Query("location") location: String,
            @Query("radius") radius: Int,
            @Query("type") type: ePlaceType): Observable<PlacesResult>
}