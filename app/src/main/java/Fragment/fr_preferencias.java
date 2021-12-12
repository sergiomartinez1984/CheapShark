package Fragment;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.cheapshark.R;

public class fr_preferencias extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.cambiar_modo,rootKey);
    }
}
