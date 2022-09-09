package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class VerificationPage {
    private static final SelenideElement code = $("[data-test-id='code'] input");
    private static final SelenideElement buttonVerification = $("[data-test-id='action-verify']");

    public VerificationPage() {
        $x("//span[@class='button__text']").shouldBe(Condition.text("Продолжить"));
        $("[data-test-id='code']").shouldBe(Condition.visible);
    }

    public void setCode(String codeValue) {
        code.setValue(codeValue);
        buttonVerification.click();
    }

    public void codeValueDelete() {
        code.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }

    public void failed() {
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.text("Ошибка! " + "Неверно указан код! Попробуйте ещё раз."), Condition.visible);
    }

    public void countFiled() {
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.text("Ошибка! " + "Превышено количество попыток ввода кода!"), Condition.visible);
    }
}
