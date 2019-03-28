package audiolibros.example.com.audiolibros.command;

import audiolibros.example.com.audiolibros.MainActivity;

public class OpenDetailClickAction implements ClickAction {

    private final MainActivity mainActivity;

    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(int position) {
        mainActivity.mostrarDetalle(position);
    }

}
