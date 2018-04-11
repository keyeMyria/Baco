package live.gaskell.baco.Registro;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import live.gaskell.baco.Cuenta.Usuario;
import live.gaskell.baco.R;
import live.gaskell.baco.Registro.RegistroFragments.FragmentRegistroCredenciales;
import live.gaskell.baco.Registro.RegistroFragments.FragmentRegistroDatosPersonales;
import live.gaskell.baco.Registro.RegistroFragments.FragmentRegistroPoliticasFacturacion;
import live.gaskell.baco.Utils;

public class RegistroActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.buttonRetroceder)
    Button buttonRetroceder;
    @BindView(R.id.buttonContinuar)
    Button buttonContinuar;


    private Unbinder unbinder;
    private static FragmentManager fragmentManager;
    private static final Fragment fragmentsRegistro[] = {
            new FragmentRegistroCredenciales(),
            new FragmentRegistroDatosPersonales(),
            new FragmentRegistroPoliticasFacturacion()
    };

    private static final String TAGS[] = {
            "REGISTRO_CREDENCIALES",    //0
            "REGISTRO_DATOS_PERSONALES", //1
            "REGISTRO_POLITICAS_FACTURACION"  //2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        unbinder = ButterKnife.bind(this);
        setFragmentManager();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.buttonRetroceder, R.id.buttonContinuar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRetroceder:
                postFragment();
                break;
            case R.id.buttonContinuar:
                switch (positionManager) {
                    case 0:
                        if (validarDatosPersonales()) {
                            nextFragment();
                        }
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
                break;
        }
    }

    /*
     * Crear un objeto que se encargue de manterner los fragmentos
     * */
    private void nextFragment() {
        if (positionManager == 0) {
            buttonRetroceder.setVisibility(View.VISIBLE);
        }
        if (positionManager != fragmentsRegistro.length - 1) {
            setFragmentManager(positionManager + 1, TAGS[positionManager + 1]);
        }
    }

    private void postFragment() {
        if (positionManager != 0) {
            setFragmentManager(positionManager - 1, TAGS[positionManager - 1]);
        }
        if (positionManager == 0) {
            buttonRetroceder.setVisibility(View.GONE);
        } else {
            buttonRetroceder.setVisibility(View.VISIBLE);
        }
    }

    //Funciona como registro del fragmentManager
    private int positionManager = 0;

    //Instancia el fragment Manager
    private void setFragmentManager() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragmentsRegistro[0], TAGS[0])
                .commit();
        positionManager = 0;
    }

    //Se usa para espesificar un fragmento
    private void setFragmentManager(int position, String TAG) {
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragmentsRegistro[position], TAG)
                .commit();
        positionManager = position;
    }

    //Validar usuario y correo existente en la base de datos
    private Usuario usuario = new Usuario();

    /*
     * Validadores de datos
     * */
    private boolean validarDatosPersonales() {
        boolean result = true;
        TextInputEditText
                editTextUsuario = findViewById(R.id.editTextUsuario),
                editTextCorreo = findViewById(R.id.editTextCorreo),
                editTextContrasena = findViewById(R.id.editTextContrasena),
                editTextVerificarContrasena = findViewById(R.id.editTextVerificarContrasena);
        //Test
        editTextCorreo.setText("carlosgaskell@outlook.com");
        editTextUsuario.setText("CarlosGaskell");
        editTextContrasena.setText("CAgaskell98");
        editTextVerificarContrasena.setText("CAgaskell98");

        usuario.setUsuario(editTextUsuario.getText().toString());
        usuario.setCorreo(editTextCorreo.getText().toString());
        usuario.setContraseña(editTextContrasena.getText().toString());
        String verificarContraseña = editTextVerificarContrasena.getText().toString();

        if (usuario.getUsuario().isEmpty()) {
            editTextUsuario.setError("Necesario.");
            result = false;
        } else if (usuario.getUsuario().length() < 6) {
            editTextUsuario.setError("Demariado corto");
            result = false;
        }

        if (usuario.getCorreo().isEmpty()) {
            editTextCorreo.setError("Necesario.");
            result = false;
        } else if (!Utils.isEmailValid(usuario.getCorreo())) {
            editTextCorreo.setError("Correo no valido.");
            result = false;
        }

        if (usuario.getContraseña().isEmpty()) {
            editTextContrasena.setError("Necesario.");
            result = false;
        } else if (usuario.getContraseña().length() < 6) {
            editTextContrasena.setError("Longitud no valida");
            result = false;
        }

        if (verificarContraseña.isEmpty()) {
            editTextVerificarContrasena.setError("Necesario.");
            result = false;
        } else if (usuario.getContraseña().length() < 6) {
            editTextContrasena.setError("Longitud no valida");
            result = false;
        }

        if (!Objects.equals(usuario.getContraseña(), verificarContraseña)) {
            editTextContrasena.setError("No coincide");
            editTextVerificarContrasena.setError("No coincide");
            result = false;
        }
        return result;
    }

    private boolean validarCredenciales() {
        boolean result = true;

        return result;
    }

    private boolean validarPoliticasFacturacion() {
        boolean result = true;

        return result;
    }
}
