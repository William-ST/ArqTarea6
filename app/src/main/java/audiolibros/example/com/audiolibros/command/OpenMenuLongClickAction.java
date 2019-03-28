package audiolibros.example.com.audiolibros.command;

import android.view.View;

import audiolibros.example.com.audiolibros.MainActivity;
import audiolibros.example.com.audiolibros.fragments.SelectorFragment;

public class OpenMenuLongClickAction implements LongClickAction {

    private final SelectorFragment selectorFragment;

    public OpenMenuLongClickAction(SelectorFragment selectorFragment) {
        this.selectorFragment = selectorFragment;
    }

    @Override
    public void execute(View v) {
        selectorFragment.showMenuItemOptions(v);
    }

}
