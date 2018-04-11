package live.gaskell.baco.Registro.RegistroFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import live.gaskell.baco.R;

public class FragmentRegistroPoliticasFacturacion extends Fragment {
    private View rootView;
    private Unbinder unbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_registro_politicas_facturacion, container, false);
        unbinder = ButterKnife.bind(rootView);

        return rootView;
    }
}
