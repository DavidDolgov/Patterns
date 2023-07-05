package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTests {

    GeneratorDate date = new GeneratorDate();
    NegativeDataGenerator negative = new NegativeDataGenerator();

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
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date.generateDate("dd.MM.yyyy")));

        $("[data-test-id=city] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        ;
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=replan-notification] .notification__title").shouldHave(Condition.exactText("Необходимо подтверждение"));

        $("[data-test-id=replan-notification] .button").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date.generateDate("dd.MM.yyyy")));

    }

    @Test
    void negativePathTestForInputFieldCityNotAnAdministrativeCenter() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(negative.noCity("ru"));
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void negativePathTestForInputFieldCityEnglishLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(negative.noCity("en"));
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
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
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
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
        $("[data-test-id=date] .input__control").setValue(negative.noDate("dd.MM.yyyy"));
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
        $("[data-test-id=date] .input__control").setValue(negative.randomDate());
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
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control ").setValue(negative.nameAnotherLocation("ru"));
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
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
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
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control ").setValue(negative.noNumber());
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date.generateDate("dd.MM.yyyy")));

    }

    @Test
    void negativePathTestForPhoneFieldLetters() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control ").setValue(negative.randomWord("ru"));
        $("[data-test-id=agreement]").click();
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=success-notification] .notification__title").should(Condition.appear);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + date.generateDate("dd.MM.yyyy")));

    }

    @Test
    void negativePathTestForPhoneFieldNoData() {

        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");

        $("[data-test-id=city] .input__control").setValue(info.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
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
        $("[data-test-id=date] .input__control").setValue(date.generateDate("dd.MM.yyyy"));
        $("[data-test-id=name] .input__control").setValue(info.getLastName() + " " + info.getFirstName());
        $("[data-test-id=phone] .input__control").setValue(info.getPhone());
        $x("//div//span[contains(text(),'Запланировать')]").click();

        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

}
