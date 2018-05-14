package live.gaskell.baco.Registro.RegistroFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import live.gaskell.baco.R;

import static android.content.ContentValues.TAG;

public class FragmentRegistroDatosPersonales extends Fragment implements View.OnClickListener {
    //Borrar para test
    private String fecha = "15/01/1998";

    private View rootView;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_registro_datos_personales, container, false);
        rootView.findViewById(R.id.imageButtonFecha).setOnClickListener(this);
        setSpinnerGenero();
        setImageButtonFecha();

        return rootView;
    }

    private void setSpinnerGenero() {
        Spinner spinnerGenero = rootView.findViewById(R.id.spinnerGenero);
        String[] spinnerValue = {
                "Masculino",
                "Femenino",
                "Otros"
        };
        spinnerAdapter adapter = new spinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);
        adapter.addAll(spinnerValue);
        adapter.add("Genero:");
        spinnerGenero.setAdapter(adapter);
        spinnerGenero.setSelection(adapter.getCount());
    }

    private DatePickerDialog dialog;

    private void setImageButtonFecha() {
        final TextView textViewFecha = rootView.findViewById(R.id.textViewFecha);
        //Borrar para test
        textViewFecha.setText(fecha);
        Calendar CALENDARIO = Calendar.getInstance();
        int DIA = CALENDARIO.get(Calendar.DAY_OF_MONTH);
        int MES = CALENDARIO.get(Calendar.MONTH);
        int ANO = CALENDARIO.get(Calendar.YEAR);

        dialog = new DatePickerDialog(
                rootView.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                        mes = mes + 1;
                        Log.d(TAG, "onDateSet: dd/mm/yyy: " + dia + "/" + mes + "/" + año);
                        String date = dia + "/" + mes + "/" + año;
                        textViewFecha.setText(date);
                        textViewFecha.setError(null);
                    }
                },
                ANO, MES, DIA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonFecha:
                dialog.show();
                break;
        }
    }


    public class spinnerAdapter extends ArrayAdapter<String> {
        int textViewId = 0;

        public spinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            // TODO Auto-generated constructor stub

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            int count = super.getCount();

            return count > 0 ? count - 1 : count;

        }
    }
}
