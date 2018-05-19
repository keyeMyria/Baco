package live.gaskell.baco.MainActivityFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import live.gaskell.baco.R;

public class FragmentMainActivityMetodosDePago extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(rootView);
        //
        return rootView;
    }
}