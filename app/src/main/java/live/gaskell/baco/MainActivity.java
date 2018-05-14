package live.gaskell.baco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import live.gaskell.baco.Cuenta.AccountsManager;

public class MainActivity extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    private AccountHeader accountHeader = null;
    private Drawer drawer = null;

    private Fragment cartaFragment;
    private Fragment fragment;

    private ToolbarManager toolbarManager;

    private FirebaseAuth mAuth;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = accountHeader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        toolbarManager = new ToolbarManager(toolbar, this);
        toolbarManager.setTitulo("Carta");
        setDrawer(savedInstanceState);
    }

    final IProfile profile = new ProfileDrawerItem()
            .withName("Carlos Gaskell")
            .withEmail("carlosgaskell@outlook.com")
            .withIcon(generarLogo("CG", "carlosgaskell@outlook.com"));

    private TextDrawable generarLogo(String img, String correo) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(correo);
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .width(60)
                .height(60)
                .endConfig()
                .round();
        return builder.build(img, color);
    }

    private String createLogoString(String nombre, String apellido) {
        return (nombre.substring(0, 1) + apellido.substring(0, 1));
    }

    private void setDrawer(Bundle savedInstanceState) {


        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile,
                        new ProfileSettingDrawerItem()
                                .withName(R.string.AGREGAR_CUENTA)
                                .withIdentifier(R.string.AGREGAR_CUENTA)
                                .withIcon(
                                        new IconicsDrawable(
                                                this, GoogleMaterial.Icon.gmd_add)
                                                .actionBar()
                                                .paddingDp(5)),
                        new ProfileSettingDrawerItem()
                                .withName("Administrar Cuenta")
                                .withIdentifier(R.string.AGREGAR_CUENTA)
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(
                        new AccountHeader.OnAccountHeaderListener() {
                            @Override
                            public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                                return false;
                            }
                        })
                .withSavedInstance(savedInstanceState)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withActionBarDrawerToggleAnimated(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.CARTA)
                                .withSelectable(true)
                                .withIdentifier(R.string.CARTA)
                                .withIcon(FontAwesome.Icon.faw_address_card),
                        new PrimaryDrawerItem()
                                .withName(R.string.CARTELERA)
                                .withSelectable(true)
                                .withIdentifier(R.string.CARTELERA)
                                .withIcon(FontAwesome.Icon.faw_utensils),
                        new PrimaryDrawerItem()
                                .withName(R.string.CUENTA)
                                .withSelectable(true)
                                .withIdentifier(R.string.CUENTA),
                        new PrimaryDrawerItem()
                                .withName(R.string.METODOS_DE_PAGO)
                                .withSelectable(true)
                                .withIdentifier(R.string.METODOS_DE_PAGO),
                        new PrimaryDrawerItem()
                                .withName(R.string.HISTORIAL)
                                .withSelectable(true)
                                .withIdentifier(R.string.HISTORIAL)
                )
                .addStickyDrawerItems(new SecondaryDrawerItem()
                                .withName(R.string.CONFIGURACIONES)
                                .withIcon(FontAwesome.Icon.faw_cog)
                                .withIdentifier(R.string.CONFIGURACIONES),
                        new SecondaryDrawerItem()
                                .withName(R.string.CERRAR_SESION)
                                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                                .withIdentifier(R.string.CERRAR_SESION),
                        new SecondaryDrawerItem()
                                .withName(R.string.ACERCA_DE)
                                .withIcon(FontAwesome.Icon.faw_child)
                                .withIdentifier(R.string.ACERCA_DE))
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();
    }

    private void setFragment(int toolbarTitulo) {
        toolbarManager.setTitulo(getResources().getString(toolbarTitulo));
        getSupportFragmentManager()
                .beginTransaction()
                //.addToBackStack(null)
                .replace(R.id.frame, fragment) //agragar un tag a fragment
                .commit();
    }

    @Override
    public void onBackPressed() {

        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else if (cartaFragment.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())) {
            super.onBackPressed();
        } else if (!cartaFragment.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())) {
            fragment = cartaFragment;

            setFragment(R.string.CARTA);
            drawer.setSelection(R.string.CARTA);
        }
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case R.string.CARTA:
                break;
            case R.string.CERRAR_SESION:
                AccountsManager.signOutUser(getApplicationContext(), mAuth);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return false;
    }
}
