package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import config.Constants;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id='login'] input");
    private final SelenideElement password = $("[data-test-id='password'] input");
    private final SelenideElement buttonLogin = $("[data-test-id='action-login']");

    public LoginPage() {
        open(Constants.WEB_URL);
        login.shouldBe(Condition.visible);
        password.shouldBe(Condition.visible);
        $x("//span[@class='button__text']").shouldBe(Condition.text("Продолжить"));
    }

    public void setLoginPassword(String loginValue, String passwordValue) {
        login.setValue(loginValue);
        password.setValue(passwordValue);
        buttonLogin.click();
    }

    public void notificationText() {
        $("[class='input__sub']")
                .shouldBe(Condition.text("Поле обязательно для заполнения"), Condition.visible);
    }

    public void failed() {
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.text("Ошибка! " + "Неверно указан логин или пароль"), Condition.visible);
    }

    public void loginPasswordValueDelete() {
        login.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        password.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }
}
