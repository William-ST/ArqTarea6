package audiolibros.example.com.audiolibros.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import audiolibros.example.com.audiolibros.R;

/**
 * Created by everis on 8/02/19.
 */
public class PreferenciasFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
