package qase.autotests.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import qase.autotests.config.ProjectConfig;
import qase.autotests.helpers.AllureAttachments;
import qase.autotests.helpers.DriverSettings;
import qase.autotests.helpers.DriverUtils;


public class TestBase extends AllureAttachments {
    public static ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    @BeforeAll
    static void beforeAll() {
        DriverSettings.configure();
        Configuration.baseUrl = config.browser();

    }

    @BeforeEach
    public void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    public void afterEach() {
        String sessionId = DriverUtils.getSessionId();

        AllureAttachments.addScreenshotAs("Last screenshot");
        AllureAttachments.addPageSource();
        AllureAttachments.addBrowserConsoleLogs();
        Selenide.closeWebDriver();

        if (cloud.autotests.config.Project.isVideoOn()) {
            AllureAttachments.addVideo(sessionId);
        }
    }
}
