package live.gaskell.baco.Cuenta;

import android.content.Context;


public class Credenciales implements UserInterface {
    private String
            Email,
            Password,
            Token;

    public Credenciales(Context context) {
        this.Password = AccountInformationManager.getAccountData(UserInterface.PASSWORD, context);
        this.Email = AccountInformationManager.getAccountData(UserInterface.EMAIL, context);
        this.Token = AccountInformationManager.getAccountData(UserInterface.TOKEN, context);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email, Context context) {
        Email = email;
        AccountInformationManager.setAccountData(UserInterface.EMAIL, Email, context);

    }

    public String getPassword() {
        return Password;

    }

    public void setPassword(String contraseña, Context context) {
        Password = contraseña;
        AccountInformationManager.setAccountData(UserInterface.PASSWORD, Password, context);

    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token, Context context) {
        Token = token;
        AccountInformationManager.setAccountData(UserInterface.TOKEN, Token, context);
    }
}
