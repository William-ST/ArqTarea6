package audiolibros.example.com.audiolibros;

public interface LibroStorage {
    boolean hasLastBook();
    int getLastBook();
    void saveLastBook(int id);
}
