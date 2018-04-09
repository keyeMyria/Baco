package live.gaskell.baco.Cuenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
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
import live.gaskell.baco.IniciarSesionMutation;
import live.gaskell.baco.MainActivity;

import static android.content.ContentValues.TAG;

public class AccountsManager extends AccountInformationManager {
    private static FirebaseUser user;

    //Constructor del manejador de cuentas, instancia firebase
    public AccountsManager(Context context) {
        super(context);
    }

    private static void signInFirebaseAnonimus(final Activity activity, final FirebaseAuth mAuth) {
        user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener((Executor) activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(activity, "Ocurrio un error, 1", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

        }
    }

    //Revisar
    private static void signOutFirebase() {
        user.delete();
        //mAuth.signOut();
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
        signInFirebaseAnonimus(activity, mAuth);
        signInGraphql(credenciales, activity);
    }

    public static void signOutUser(Context context) {
        signOutFirebase();
        signOutGraphQl(context);
    }

}
