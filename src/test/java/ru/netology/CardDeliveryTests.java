package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTests {

    String date = GeneratorDate.generateDate("dd.MM.yyyy");
    String noCity = NegativeDataGenerator.noCity("ru");
    String noDate = NegativeDataGenerator.noDate("dd.MM.yyyy");
    String randomDate = NegativeDataGenerator.randomDate();
    String nameAnotherLocation = NegativeDataGenerator.nameAnotherLocation("ru");
    String noNumber = NegativeDataGenerator.noNumber();
    String randomWord = NegativeDataGenerator.randomWord("ru");

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
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date));

        $("[data-test-id=city] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        ;
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=replan-notification] .notification__title").shouldHave(Condition.exactText("Необходимо подтверждение"));

        $("[data-test-id=replan-notification] .button").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date));

    }

    @Test
    void negativePathTestForInputFieldCityNotAnAdministrativeCenter() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(noCity);
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void negativePathTestForInputFieldCityEnglishLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(noCity);
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date);
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
        $("[data-test-id=date] .input__control").setValue(date);
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
        $("[data-test-id=date] .input__control").setValue(noDate);
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
        $("[data-test-id=date] .input__control").setValue(randomDate);
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
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control ").setValue(nameAnotherLocation);
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
        $("[data-test-id=date] .input__control").setValue(date);
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
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control ").setValue(noNumber);
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date));

    }

    @Test
    void negativePathTestForPhoneFieldLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control ").setValue(randomWord);
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date));

    }

    @Test
    void negativePathTestForPhoneFieldNoData() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date);
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
        $("[data-test-id=date] .input__control").setValue(date);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

}
