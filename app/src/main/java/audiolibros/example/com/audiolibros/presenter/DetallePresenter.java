package audiolibros.example.com.audiolibros.presenter;

import android.net.Uri;

import audiolibros.example.com.audiolibros.Libro;
import audiolibros.example.com.audiolibros.usescase.GetBookById;

public class DetallePresenter {

    private final DetalleView view;
    private final GetBookById getBookById;

    public DetallePresenter(DetalleView view, GetBookById getBookById) {
        this.view = view;
        this.getBookById = getBookById;
    }

    public void ponInfoLibro(int position) {
        Libro book = getBookById.execute(position);
        if (book != Libro.LIBRO_EMPTY) {
            view.renderInfoLibro(book);
            view.startAudio(Uri.parse(book.urlAudio));
        }
    }

    public interface DetalleView {
        void renderInfoLibro(Libro libro);
        void startAudio(Uri audio);
        void stopAudio();
    }

}
