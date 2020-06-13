package by.ladyka.profile.exceptions;

public class UserExistException extends ApplicationException {
    public UserExistException(FIELD f) {
        super("Пользователь с данным " + f.getText() + " уже существует. Пожалуйста выберите другое!");
    }

    public enum FIELD {
        USERNAME("именем пользователя"),
        EMAIL("почтовым адресом");

        private final String text;

        FIELD(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
