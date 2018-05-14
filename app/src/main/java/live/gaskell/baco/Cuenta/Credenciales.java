package live.gaskell.baco.Cuenta;

import android.content.Context;


public class Credenciales implements UserInterface {
    private String
            Email,
            Password,
            Token,
            UserId,
            ClienteId;

    public Credenciales() {

    }

    public Credenciales(boolean Activate) {

    }

    public void getCredenciales(Context context) {
        this.Password = AccountInformationManager.getAccountData(UserInterface.PASSWORD, context);
        this.Email = AccountInformationManager.getAccountData(UserInterface.EMAIL, context);
        this.Token = AccountInformationManager.getAccountData(UserInterface.TOKEN, context);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email, Context context) {
        Email = email;
        AccountInformationManager.setAccountData(UserInterface.EMAIL, email, context);

    }

    public String getPassword() {
        return Password;

    }

    public void setPassword(String contraseña, Context context) {
        Password = contraseña;
        AccountInformationManager.setAccountData(UserInterface.PASSWORD, contraseña, context);

    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token, Context context) {
        Token = token;
        AccountInformationManager.setAccountData(UserInterface.TOKEN, token, context);
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId, Context context) {
        UserId = userId;
        AccountInformationManager.setAccountData(UserInterface.USERID, userId, context);

    }

    public String getClienteId() {
        return ClienteId;
    }

    public void setClienteId(String clienteId, Context context) {
        ClienteId = clienteId;
        AccountInformationManager.setAccountData(UserInterface.CLIENTEID, clienteId, context);

    }
}
