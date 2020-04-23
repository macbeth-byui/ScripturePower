package macbeth.scripturepower.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.Config;
import macbeth.scripturepower.model.Library;
import macbeth.scripturepower.presenter.MainPresenter;

public class SettingsFragment extends Fragment implements MainPresenter.Listener {

    private Spinner fontSizeSpinner;
    private View rootView;

    // TODO: Show file size
    // TODO: Create a waiting spinner and turn off when data is ready
    // TODO: Color and Font

    /**
     * Initialize the member data (except for View items)
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = null;
    }

    /**
     * When the fragment is first created, we need to setup the layout (the rootView),
     * initialize view data and register our fragment with the MainPresenter.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_settings, container, false);

            fontSizeSpinner = rootView.findViewById(R.id.spinner_font);
            ((MainActivity) getActivity()).registerFragment(this);  // Ready to receive library and config
        }

        return rootView;
    }

    /**
     * Once the config has been loaded, then we can display and respond to changes from the config.
     * When changes are made to the fields in the config, the config object is updated, the config
     * is saved, and all the users are notified.
     * @param config
     */
    @Override
    public void notifyDataReady(Library library, Config config) {
        // TODO: Create a custom adapter to make this easier using getPosition by value
        fontSizeSpinner.setSelection((config.getFontSize()-12)/2); // starts at 12 and increments by 2
        fontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                config.setFontSize(Integer.parseInt((String) adapterView.getItemAtPosition(i)));
                config.saveConfig(getContext());
                ((MainActivity) getActivity()).getPresenter().notifyUsersConfigChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * This will get called when we change the config but since we are the ones who changed it, we don't
     * need to do anything.
     * @param
     */
    @Override
    public void notifyConfigChanged() {
    }


}
