package live.gaskell.baco.Cuenta;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import live.gaskell.baco.ApolloClient.Apollo_Client;
import live.gaskell.baco.BaseActivity;
import live.gaskell.baco.CrearClienteMutation;
import live.gaskell.baco.CrearUsuarioMutation;
import live.gaskell.baco.IniciarSesionMutation;
import live.gaskell.baco.MainActivity;
import live.gaskell.baco.SetClienteOnUserMutation;

import static android.content.ContentValues.TAG;

public class AccountsManager extends AccountInformationManager {
    private static FirebaseUser user;


    //Constructor del manejador de cuentas, instancia firebase
    public AccountsManager() {
    }

    private void signInFirebaseAnonimus(final Activity activity, FirebaseAuth mAuth) {
        mAuth.signInAnonymously()
                .addOnCompleteListener((Executor) activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(activity, "Ocurrio un error, 1", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private static void signUpFirebaseEmail(Credenciales credenciales, Activity activity, final FirebaseAuth mAuth) {

            mAuth.createUserWithEmailAndPassword(credenciales.getEmail(), credenciales.getPassword())
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.

                            }

                            // ...
                        }
                    });
        }

    private static void signInFirebaseEmail(Credenciales credenciales, final Activity activity, final FirebaseAuth mAuth) {
        mAuth.signInWithEmailAndPassword(credenciales.getEmail(), credenciales.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    //Revisar
    private static void signOutFirebase(FirebaseAuth mAuth) {
        mAuth.signOut();

    }

    private static void signInGraphql(final Credenciales credenciales, final Activity activity) {
        Apollo_Client.getApolloClient().mutate(
                IniciarSesionMutation.builder()
                        .usuario(credenciales.getEmail())
                        .contrasena(credenciales.getPassword())
                        .build()
        ).enqueue(new ApolloCall.Callback<IniciarSesionMutation.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<IniciarSesionMutation.Data> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        credenciales.setToken(response.data().signinUser().token().toString(), activity);
                        Intent i = new Intent(activity, MainActivity.class);
                        activity.startActivity(i);
                        activity.finish();
                        BaseActivity.hideProgressDialog();
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Ocurrio un error, 2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private static void signOutGraphQl(Context context) {
        AccountInformationManager.clearDataUser(context);
    }

    public static void signInUser(Credenciales credenciales, Activity activity, FirebaseAuth mAuth) {
        BaseActivity.showProgressDialog("Iniciando...", activity);
        signInFirebaseEmail(credenciales, activity, mAuth);
        //signInFirebaseAnonimus(activity, mAuth);
        signInGraphql(credenciales, activity);
    }

    public static void signOutUser(Context context, FirebaseAuth mAuth) {
        signOutFirebase(mAuth);
        signOutGraphQl(context);
    }

    //Crear Cuenta en Graphql
    public static void signUpGraphql(final Credenciales credenciales, final Activity activity, final Usuario usuario, final View view, final FirebaseAuth firebase) {
        BaseActivity.showProgressDialog("Creando cuenta", activity);

        //Crear Usuario en Firebase
        signUpFirebaseEmail(credenciales, activity, firebase);

        Apollo_Client.getApolloClient().mutate(
                CrearUsuarioMutation.builder()
                        .email(credenciales.getEmail())
                        .password(credenciales.getPassword())
                        .build()
        ).enqueue(new ApolloCall.Callback<CrearUsuarioMutation.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<CrearUsuarioMutation.Data> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        credenciales.setUserId(response.data().createUser().id(), activity.getApplicationContext());
                        //Cuando cree el usuario comienza a guardar los datos personales
                        AccountsManager.setDatosGenerales(usuario, activity, view, firebase, credenciales);
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });

    }

    //Guardar datos de usuario
    public static void setDatosGenerales(Usuario usuario, final Activity activity, final View view, final FirebaseAuth firebase, final Credenciales credenciales) {
        BaseActivity.showProgressDialog("Enviando datos", activity);
        Apollo_Client.getApolloClient().mutate(
                CrearClienteMutation.builder()
                        .usuario(usuario.getUsuario())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .cedula(usuario.getCedula())
                        .telefono(usuario.getTelefono())
                        .direccion(usuario.getDireccion())
                        .fecha(usuario.getFecha_de_nacimiento())
                        .genero(usuario.getSexo())
                        .build()
        ).enqueue(new ApolloCall.Callback<CrearClienteMutation.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<CrearClienteMutation.Data> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        credenciales.setClienteId(response.data().createCliente().id(), activity.getApplicationContext());

                        AccountsManager.vincularCuenta(
                                activity,
                                AccountInformationManager.getAccountData(UserInterface.CLIENTEID, activity),
                                AccountInformationManager.getAccountData(UserInterface.USERID, activity),
                                credenciales,
                                firebase);
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });
    }

    public static void vincularCuenta(final Activity activity, String clienteID, String userId, final Credenciales credenciales, final FirebaseAuth firebase) {
        BaseActivity.showProgressDialog("Vinculando datos", activity);
        Log.i("DATA_CLIENTEID: ", clienteID);
        Log.i("DATA_USERID", userId);
        Apollo_Client.getApolloClient().mutate(
                SetClienteOnUserMutation.builder()
                        .clienteID(clienteID)
                        .userID(userId)
                        .build()
        ).enqueue(new ApolloCall.Callback<SetClienteOnUserMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<SetClienteOnUserMutation.Data> response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        signInUser(credenciales, activity, firebase);

                    }
                });
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }
}