package live.gaskell.baco.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import live.gaskell.baco.Cuenta.UserInterface;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements UserInterface {

    @VisibleForTesting
    public static ProgressDialog mProgressDialog;


    public static void showProgressDialog(String mensaje, Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(mensaje);
            mProgressDialog.setIndeterminate(true);
        } else {
            mProgressDialog.setMessage(mensaje);
        }
        if (mProgressDialog.isShowing()) {
            hideProgressDialog();
        } else {
            mProgressDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public class ToolbarManager {

        private Toolbar toolbar;
        private String Titulo, subTitulo;
        private Activity activity;

        public ToolbarManager() {
        }

        public ToolbarManager(Toolbar toolbar, Activity activity) {
            this.toolbar = toolbar;
            this.activity = activity;
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        }

        public void setDisplayHomeAsUpEnabled() {
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                Log.e("funciona", "verdadero");
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            }

        }


        public Activity getActivity() {
            return activity;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public Toolbar getToolbar() {
            return toolbar;
        }

        public void setToolbar(Toolbar toolbar) {
            this.toolbar = toolbar;
        }

        public String getSubTitulo() {
            return subTitulo;
        }

        public void setSubTitulo(String subTitulo) {
            this.subTitulo = subTitulo;
            toolbar.setSubtitle(subTitulo);
        }

        public String getTitulo() {
            return Titulo;
        }

        public void setTitulo(String titulo) {
            this.Titulo = titulo;
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(titulo);
        }
    }


}