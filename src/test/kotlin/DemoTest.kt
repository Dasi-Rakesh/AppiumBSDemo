import io.appium.java_client.android.AndroidDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.remote.DesiredCapabilities
import java.lang.Thread.sleep
import java.net.URL

class DemoTest {
    private val remoteAddress: String = "https://hub-cloud.browserstack.com/wd/hub"

    @Test
    fun sampleTest() {
        var driver: AndroidDriver? = null
        try {
            driver = createDriver()
            println("Launched App")
            sleep(5000)

            // Perform test steps here...

            // Set session status as passed if test steps are successful
            updateSessionStatus(driver, "passed", "Test completed successfully")
        } catch (e: Exception) {
            // Set session status as failed if an exception occurs
            updateSessionStatus(driver, "failed", e.message ?: "An error occurred")
            throw e
        } finally {
            driver?.quit()
        }
    }

    private fun createDriver(): AndroidDriver {
        val capabilities = getCapabilities()
        return AndroidDriver(URL(remoteAddress), capabilities)
    }

    private fun getCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()

        // Appium capabilities
        capabilities.setCapability("platformName", "android")
        capabilities.setCapability("appium:platformVersion", "13.0")
        capabilities.setCapability("appium:deviceName", "Google Pixel 7 Pro")
        capabilities.setCapability("appium:app", "bs://5ec13c79a30aa813873e6dd2af8d6742a069cf4e")


        // BrowserStack options
        val bstackOptions = HashMap<String, Any>()
        bstackOptions["userName"] = "USERNAME"
        bstackOptions["accessKey"] = "ACCESS-KEY"
        bstackOptions["projectName"] = "KHealthDemoProjectRun"
        bstackOptions["buildName"] = "KHealthDemoRun"
        bstackOptions["sessionName"] = "SampleTest"
        capabilities.setCapability("bstack:options", bstackOptions)

        return capabilities
    }

    private fun updateSessionStatus(driver: AndroidDriver?, status: String, reason: String) {
        if (driver is JavascriptExecutor) {
            (driver as JavascriptExecutor).executeScript(
                "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"$status\", \"reason\": \"$reason\"}}"
            )
        }
    }
}
