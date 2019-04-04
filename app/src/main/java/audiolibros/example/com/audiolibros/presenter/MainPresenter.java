package audiolibros.example.com.audiolibros.presenter;

import audiolibros.example.com.audiolibros.LibroStorage;
import audiolibros.example.com.audiolibros.usescase.GetLastBook;
import audiolibros.example.com.audiolibros.usescase.SaveLastBook;
import audiolibros.example.com.audiolibros.usescase.ValidExistLasBook;

public class MainPresenter {

    private final SaveLastBook saveLastBook;
    private final GetLastBook getLastBook;
    private final ValidExistLasBook validExistLasBook;
    private final View view;

    public MainPresenter(SaveLastBook saveLastBook, GetLastBook getLastBook, ValidExistLasBook validExistLasBook, MainPresenter.View view) {
        this.saveLastBook = saveLastBook;
        this.getLastBook = getLastBook;
        this.validExistLasBook = validExistLasBook;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (validExistLasBook.execute()) {
            view.mostrarFragmentDetalle(getLastBook.execute());
        } else {
            view.mostrarNoUltimaVisita();
        }
    }

    public void openDetalle(int id) {
        saveLastBook.execute(id);
        view.mostrarFragmentDetalle(id);
    }

    public interface View {
        void mostrarFragmentDetalle(int lastBook);
        void mostrarNoUltimaVisita();
    }
}
