import io.appium.java_client.android.AndroidDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.remote.DesiredCapabilities
import java.lang.Thread.sleep
import java.net.URL

class DemoTest {
    private val remoteAddress: String = "https://hub-cloud.browserstack.com/wd/hub"
    private val bsUserName: String = System.getenv("USERNAME") ?: "username_undefined"
    private val bsAccessKey: String = System.getenv("ACCESS_KEY") ?: "access_key_undefined"
    private val appPath: String = System.getenv("APP_PATH") ?: "app_path_undefined"
    private val deviceName: String  = "Google Pixel 7 Pro"
    private val platform: String  = "android"
    private val platformVersion: String  = "13.0"

    @Test
    fun sampleTest() {
        lateinit var driver: AndroidDriver
        try {
            driver = createDriver()
            println("Launched App")
            sleep(5000)
        } finally {
            driver.quit()
        }
    }

    private fun createDriver(): AndroidDriver {
        val capabilities = getCapabilities()
        return AndroidDriver(URL(remoteAddress), capabilities)
    }

    private fun getCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        // Appium capabilities
        capabilities.setCapability("appium:deviceName", deviceName)
        capabilities.setCapability("appium:app", appPath)
        capabilities.setCapability("platformName", platform)
        capabilities.setCapability("appium:platformVersion", platformVersion)
        // BrowserStack capabilities
        val options = HashMap<String, Any>()
        options.put("userName", bsUserName)
        options.put("accessKey", bsAccessKey)
        options.put("projectName", "DemoProject")
        options.put("buildName", "Test Build")
        options.put("sessionName", "SampleTest")
        capabilities.setCapability("bstack:options", options)
        return capabilities
    }
}