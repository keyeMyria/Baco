package live.gaskell.baco.Registro;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import live.gaskell.baco.BaseActivity;
import live.gaskell.baco.Cuenta.AccountsManager;
import live.gaskell.baco.Cuenta.Credenciales;
import live.gaskell.baco.Cuenta.Usuario;
import live.gaskell.baco.R;
import live.gaskell.baco.Registro.RegistroFragments.FragmentRegistroCredenciales;
import live.gaskell.baco.Registro.RegistroFragments.FragmentRegistroDatosPersonales;
import live.gaskell.baco.Registro.RegistroFragments.FragmentRegistroPoliticasFacturacion;
import live.gaskell.baco.Utils;

public class RegistroActivity extends BaseActivity {
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
    //Validar usuario y correo existente en la base de datos
    private Usuario usuario = new Usuario();
    private Credenciales credenciales = new Credenciales();
    private FirebaseAuth firebaseAuth;

    private ToolbarManager toolbarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        unbinder = ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        setFragmentManager();

        toolbarManager = new ToolbarManager(toolbar, this);
        toolbarManager.setTitulo("Registro");
        toolbarManager.setSubTitulo("Credenciales");
        toolbarManager.setDisplayHomeAsUpEnabled();
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
                        if (validarCredenciales()) {
                            nextFragment();
                        }
                        break;
                    case 1:
                        if (validarDatosPersonales()) {
                            nextFragment();
                        }
                        break;
                    case 2:
                        if (validarPoliticasFacturacion()) {
                            AccountsManager.signUpGraphql(credenciales, this, usuario, view, firebaseAuth);
                            Snackbar.make(view, "Cuenta creada", Snackbar.LENGTH_INDEFINITE);
                        }
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

    /*
     * Validadores de datos
     * */
    private boolean validarDatosPersonales() {
        boolean result = true;
        TextInputEditText
                editTextNombre = findViewById(R.id.editTextNombre),
                editTextApellido = findViewById(R.id.editTextApellidos);
        TextView textViewFecha = findViewById(R.id.textViewFecha);
        Spinner genero = findViewById(R.id.spinnerGenero);

        //Test
        editTextNombre.setText("Carlos");
        editTextApellido.setText("Gaskell Bravo");
        textViewFecha.setText("15/01/1998");
        usuario.setSexo("Masculino");

        //Cambiar genero
        usuario.setNombre(editTextNombre.getText().toString());
        usuario.setApellido(editTextApellido.getText().toString());
        usuario.setFecha_de_nacimiento(textViewFecha.getText().toString());
        usuario.setSexo(genero.getSelectedItem().toString());

        if (usuario.getNombre().isEmpty()) {
            editTextNombre.setError("Necesario.");
            result = false;
        }

        if (usuario.getApellido().isEmpty()) {
            editTextApellido.setError("Necesario.");
            result = false;
        }

        if (usuario.getFecha_de_nacimiento().equals("00/00/0000")) {
            textViewFecha.setError("Necesario");
            result = false;
        }
        /* revisar genero
        if (usuario.getSexo().equals("Genero:")) {
            Toast.makeText(this, "Genero necesario", Toast.LENGTH_SHORT).show();
            result = false;
        }*/

        return result;
    }

    private boolean validarCredenciales() {
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
        credenciales.setEmail(editTextCorreo.getText().toString(), getApplicationContext());
        credenciales.setPassword(editTextContrasena.getText().toString(), getApplicationContext());
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

        if (credenciales.getPassword().isEmpty()) {
            editTextContrasena.setError("Necesario.");
            result = false;
        } else if (credenciales.getPassword().length() < 6) {
            editTextContrasena.setError("Longitud no valida");
            result = false;
        }

        if (verificarContraseña.isEmpty()) {
            editTextVerificarContrasena.setError("Necesario.");
            result = false;
        } else if (verificarContraseña.length() < 6) {
            editTextContrasena.setError("Longitud no valida");
            result = false;
        }

        if (!Objects.equals(credenciales.getPassword(), verificarContraseña)) {
            editTextContrasena.setError("No coincide");
            editTextVerificarContrasena.setError("No coincide");
            result = false;
        }
        return result;
    }

    private boolean validarPoliticasFacturacion() {
        boolean result = true;
        TextInputEditText
                editTextCedula = findViewById(R.id.editTextCedula),
                editTextTelefono = findViewById(R.id.editTextTelefono),
                editTextDireccion = findViewById(R.id.editTextDireccion);
        //Test
        editTextCedula.setText("0950371096");
        editTextTelefono.setText("0982315257");
        editTextDireccion.setText("Atarazana Mz. L1 Villa 7");

        usuario.setCedula(editTextCedula.getText().toString());
        usuario.setTelefono(editTextTelefono.getText().toString());
        usuario.setDireccion(editTextDireccion.getText().toString());

        if (usuario.getCedula().isEmpty()) {
            editTextCedula.setError("Necesario.");
            result = false;
        } else if (usuario.getCedula().length() < 10) {
            editTextCedula.setError("No valido");
            result = false;
        }

        if (usuario.getTelefono().isEmpty()) {
            editTextTelefono.setError("Necesario");
            result = false;
        } else if (usuario.getTelefono().length() < 10) {
            editTextTelefono.setError("No valido");
            result = false;
        }

        if (usuario.getDireccion().isEmpty()) {
            editTextTelefono.setError("No valido");
            result = false;
        } else if (usuario.getDireccion().length() < 8) {
            editTextTelefono.setError("Datos incompletos");
            result = false;
        }
        return result;
    }

}
