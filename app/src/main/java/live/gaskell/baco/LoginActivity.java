package live.gaskell.baco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import live.gaskell.baco.Cuenta.AccountsManager;
import live.gaskell.baco.Cuenta.Credenciales;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Credenciales credenciales;
    private FirebaseAuth mAuth;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        credenciales = new Credenciales(getApplicationContext());

        findViewById(R.id.buttonIniciar).setOnClickListener(this);
        findViewById(R.id.buttonRegistrarse).setOnClickListener(this);
        editTextEmail = findViewById(R.id.editTextCorreo);
        editTextPassword = findViewById(R.id.editTextPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonIniciar:
                if (validadoresLogin.validarCredenciales(editTextEmail, editTextPassword)) {
                    credenciales.setEmail(editTextEmail.getText().toString(), getApplicationContext());
                    credenciales.setPassword(editTextPassword.getText().toString(), getApplicationContext());
                    AccountsManager.signInUser(credenciales, this, mAuth);
                }
                break;
            case R.id.buttonRegistrarse:

                break;
        }
    }

    private static class validadoresLogin {
        private static boolean validarCredenciales(EditText editTextEmail, EditText editTextPassword) {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            boolean r = true;
            if (email.isEmpty()) {
                editTextEmail.setError("Necesario");
                r = false;
            } else if (!isEmailValid(email)) {
                editTextEmail.setError("Correo no valido");
                r = false;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Necesario");
                r = false;

            } else if (password.length() < 6) {
                editTextPassword.setError("Formato incompatible");
                r = false;
            }

            return r;
        }

        //Validador de email
        private static boolean isEmailValid(String email) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
}
