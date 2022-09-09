package test;

import com.codeborne.selenide.Configuration;
import config.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;
import sql.SqlQuery;

public class TestApp {
    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        Configuration.browserSize = "1000x800";
        //Configuration.holdBrowserOpen = true;
    }

    @Test
    @DisplayName("display notification text for login")
    public void displayTextLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword("", Constants.WEB_PASSWORD);
        loginPage.notificationText();
    }

    @Test
    @DisplayName("display notification text for password")
    public void displayTextPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword(Constants.WEB_LOGIN, "");
        loginPage.notificationText();
    }

    @Test
    @DisplayName("success for login vasya")
    public void loginSuccess() {
        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword(Constants.WEB_LOGIN, Constants.WEB_PASSWORD);

        VerificationPage verificationPage = new VerificationPage();
        SqlQuery sqlQuery = new SqlQuery();
        verificationPage.setCode(sqlQuery.getValidCode());

        DashboardPage dashboardPage = new DashboardPage();
    }

    @Test
    @DisplayName("unsuccessfully for no registered user")
    public void loginFailedNoRegistered() {
        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword("abra", "gadabra");
        loginPage.failed();
    }

    @Test
    @DisplayName("success login random user")
    public void successLoginRandomUser() {
        SqlQuery sqlQuery = new SqlQuery();
        sqlQuery.addUser();
        sqlQuery.addUser();

        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword(sqlQuery.getRandomLoginUser(), Constants.WEB_PASSWORD);

        VerificationPage verificationPage = new VerificationPage();
        verificationPage.setCode(sqlQuery.getValidCode());

        DashboardPage dashboardPage = new DashboardPage();
    }

    @Test
    @DisplayName("login vasya input three password")
    public void inputThreePassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword(Constants.WEB_LOGIN, "password1");
        loginPage.loginPasswordValueDelete();
        loginPage.setLoginPassword(Constants.WEB_LOGIN, "password2");
        loginPage.loginPasswordValueDelete();
        loginPage.setLoginPassword(Constants.WEB_LOGIN, "password3");
        loginPage.loginPasswordValueDelete();
        loginPage.setLoginPassword(Constants.WEB_LOGIN, Constants.WEB_PASSWORD);

        VerificationPage verificationPage = new VerificationPage();
        SqlQuery sqlQuery = new SqlQuery();
        verificationPage.setCode(sqlQuery.getValidCode());

        DashboardPage dashboardPage = new DashboardPage();
    }

    @Test
    @DisplayName("user random input three error code")
    public void inputErrorCode() {
        SqlQuery sqlQuery = new SqlQuery();
        sqlQuery.addUser();
        sqlQuery.addUser();

        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword(sqlQuery.getRandomLoginUser(), Constants.WEB_PASSWORD);

        VerificationPage verificationPage = new VerificationPage();
        verificationPage.setCode(Constants.ERROR_WEB_CODE);
        verificationPage.failed();
        verificationPage.codeValueDelete();
        verificationPage.setCode(Constants.ERROR_WEB_CODE);
        verificationPage.failed();
        verificationPage.codeValueDelete();
        verificationPage.setCode(Constants.ERROR_WEB_CODE);
        verificationPage.failed();
        verificationPage.codeValueDelete();
        verificationPage.setCode(sqlQuery.getValidCode());

        DashboardPage dashboardPage = new DashboardPage();
    }

    @Test
    @DisplayName("login unsuccessfully for random user")
    public void loginFailedForRandomUser() {
        SqlQuery sqlQuery = new SqlQuery();
        sqlQuery.addUser();
        sqlQuery.addUser();

        LoginPage loginPage = new LoginPage();
        loginPage.setLoginPassword(sqlQuery.getRandomLoginUser(), "password");
        loginPage.failed();
    }

/*    @Test
    @DisplayName("TRUNCATE TABLE")
    public void truncateTable() {
        SqlQuery sqlQuery = new SqlQuery();
        sqlQuery.truncateTable();
    }*/
}
