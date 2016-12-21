package patrickds.github.democraticlunch

import dagger.Component
import patrickds.github.democraticlunch.injection.ApplicationScope
import patrickds.github.democraticlunch.injection.GoogleWebServiceModule
import patrickds.github.democraticlunch.network.IGoogleWebService

@ApplicationScope
@Component(modules = arrayOf(GoogleWebServiceModule::class))
interface DemocraticLunchApplicationComponent {
    val googleWebService: IGoogleWebService
}