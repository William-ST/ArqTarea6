package audiolibros.example.com.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

public class LibroSharedPreferenceStorage implements LibroStorage {

    public static final String PREF_AUDIOLIBROS = "audiolibros.example.com.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";

    private static LibroSharedPreferenceStorage instance;
    private final Context context;

    public static LibroStorage getInstance(Context context) {
        if(instance == null) {
            instance = new LibroSharedPreferenceStorage(context);
        }
        return instance;
    }

    private LibroSharedPreferenceStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasLastBook() {
        return getPreference().contains(KEY_ULTIMO_LIBRO);
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOLIBROS, Context.MODE_PRIVATE);
    }

    private void setLibroOpenDetail(Libro libro) {

    }

    @Override
    public int getLastBook() {
        return getPreference().getInt(KEY_ULTIMO_LIBRO, -1);
    }
}
