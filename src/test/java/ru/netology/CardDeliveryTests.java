package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTests {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void happyPathTest1() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + info.getDate()));

        $("[data-test-id=city] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate2());
        $("[data-test-id=name] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        ;
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=replan-notification] .notification__title").shouldHave(Condition.exactText("Необходимо подтверждение"));

        $("[data-test-id=replan-notification] .button").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + info.getDate2()));

        printTestData(info.getCity(), info.getDate(), info.getDate2(), info.getLastName(), info.getFirstName(), info.getPhone());

    }

    @Test
    void negativePathTestForInputFieldCityNotAnAdministrativeCenter() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue("Кузнецк");
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void negativePathTestForInputFieldCityEnglishLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue("Penza");
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void negativePathTestForInputFieldCityNoData() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativePathTestForDataFieldDeliveryLessThanThreeDays() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    void negativePathTestForDataFieldWrongDate() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue("34.45.6789");
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));

    }

    @Test
    void negativePathTestForDataFieldNOData() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));

    }

    @Test
    void negativePathTestForNameFieldEnglishLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control ").setValue("Dolgov David");
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void negativePathTestForNameFieldNoData() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativePathTestForPhoneFieldWrongNumber() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control ").setValue("+7999");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + info.getDate()));

    }

    @Test
    void negativePathTestForPhoneFieldLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control ").setValue("qwerty");
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + info.getDate()));

    }

    @Test
    void negativePathTestForPhoneFieldNoData() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativePathTestForCheckbox() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(info.getDate());
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

    private void printTestData(String city, String date, String date2, String firstName, String lastName, String phone) {
        System.out.println("====================================");
        System.out.println(city + "\n" + date + "\n" + date2 + "\n" + firstName + " " + lastName + "\n" + phone);
        System.out.println("====================================");
    }

}
