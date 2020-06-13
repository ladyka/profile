package by.ladyka.profile.exceptions;

public class InvalidRegTokenException extends ApplicationException {

    public InvalidRegTokenException() {
        super("Токен не валидный! Ошибка. Возможно Вы уже активироовали учетную запись, попробуйте авторизоваться. Если не получается - обратитесь в службу поддержки пользователей!");
    }
}
