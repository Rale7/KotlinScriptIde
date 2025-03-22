package test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import com.kotlin.script.ide.koin.appModule
import org.junit.Rule
import org.koin.compose.LocalKoinApplication
import org.koin.compose.LocalKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinComponent
import org.koin.mp.KoinPlatformTools
import org.koin.test.KoinTestRule

open class BaseComposeTest : KoinComponent {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(appModule)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(KoinInternalApi::class)
    protected fun setContent(content: @Composable () -> Unit) {

        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalKoinScope provides KoinPlatformTools.defaultContext().get().scopeRegistry.rootScope,
                LocalKoinApplication provides KoinPlatformTools.defaultContext().get()
            ) {
                content()
            }
        }

    }

}