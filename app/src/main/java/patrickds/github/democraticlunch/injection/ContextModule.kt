package patrickds.github.democraticlunch.injection

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val _context: Context) {

    @Provides
    @ApplicationScope
    fun context(): Context{
        return _context;
    }
}