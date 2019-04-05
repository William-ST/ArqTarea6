package audiolibros.example.com.audiolibros.fragments;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.io.IOException;

import audiolibros.example.com.audiolibros.BooksRespository;
import audiolibros.example.com.audiolibros.Libro;
import audiolibros.example.com.audiolibros.LibroMemoryStorage;
import audiolibros.example.com.audiolibros.MainActivity;
import audiolibros.example.com.audiolibros.R;
import audiolibros.example.com.audiolibros.VolleySingleton;
import audiolibros.example.com.audiolibros.presenter.DetallePresenter;
import audiolibros.example.com.audiolibros.usescase.GetBookById;

/**
 * Created by William_ST on 05/02/19.
 */

public class DetalleFragment extends Fragment implements View.OnTouchListener, MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl, DetallePresenter.DetalleView {

    public static String ARG_ID_LIBRO = "id_libro";
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private View view;
    private TextView tvTitle, tvAutor;
    private NetworkImageView networkImageView;
    private DetallePresenter detallePresenter;
    //private View vista;

    public DetallePresenter getPresenter() {
        return detallePresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetBookById getBookById = new GetBookById(new BooksRespository(new LibroMemoryStorage(getActivity())));
        detallePresenter = new DetallePresenter(this, getBookById);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        view = inflador.inflate(R.layout.fragment_detalle, contenedor, false);
        Bundle args = getArguments();
        int position = args.getInt(ARG_ID_LIBRO, 0);
        detallePresenter.ponInfoLibro(position);
        return view;
    }

    @Override
    public void onResume() {
        mostrarElementos();
        super.onResume();
    }

    private void initializeGUI() {
        //this.view = view;
        tvTitle = view.findViewById(R.id.titulo);
        tvAutor = view.findViewById(R.id.autor);
        networkImageView = view.findViewById(R.id.portada);
    }

    @Override
    public void renderInfoLibro(Libro libro) {
        if (libro != Libro.LIBRO_EMPTY) {
            initializeGUI();
            tvTitle.setText(libro.titulo);
            tvAutor.setText(libro.autor);
            networkImageView.setImageUrl(libro.urlImagen, VolleySingleton.getInstance(getActivity()).getLectorImagenes());
            view.setOnTouchListener(this);
        }
    }

    @Override
    public void startAudio(Uri uriAudio) {
        if (uriAudio == null) return;

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());
        try {
            mediaPlayer.setDataSource(getActivity(), uriAudio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir " + uriAudio.toString(), e);
        }
    }

    @Override
    public void stopAudio() {
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.d("Audiolibros", "Error en mediaPlayer.stop()");
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(R.id.fragment_detalle));
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Override
    public boolean onTouch(View vista, MotionEvent evento) {
        mediaController.show();
        return false;
    }

    @Override
    public void onStop() {
        stopAudio();
        super.onStop();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    private void mostrarElementos() {
        DetalleFragment detalleFragment = (DetalleFragment)
                getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment == null) {
            ((MainActivity) getActivity()).mostrarElementos(false);
        }
    }
}
