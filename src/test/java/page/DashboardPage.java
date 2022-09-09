package page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    public DashboardPage() {
        $x("//h2[contains(text(),'Личный кабинет')]").should(Condition.visible);
    }
}
