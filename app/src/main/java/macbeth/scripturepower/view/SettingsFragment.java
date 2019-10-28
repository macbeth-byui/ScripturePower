package macbeth.scripturepower.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import macbeth.scripturepower.R;
import macbeth.scripturepower.presenter.BrowsePresenter;
import macbeth.scripturepower.presenter.MainPresenter;

public class SettingsFragment extends Fragment  {

    private Spinner fontSizeSpinner;
    private Gson gson;
    private SharedPreferences sp;
    private MainPresenter mainPresenter;
    private View rootView;

    // TODO: Show file size

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = ((MainActivity) getActivity()).getPresenter();
        //browsePresenter = new BrowsePresenter(mainPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        fontSizeSpinner = rootView.findViewById(R.id.spinner_font);

        fontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mainPresenter.getConfig().setFontSize(Integer.parseInt((String)adapterView.getItemAtPosition(i)));
                mainPresenter.getConfig().saveConfig(getContext());
                mainPresenter.notifyUsersConfigChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }


    private void setFontSize(String fontSize) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("FontSize", Integer.parseInt(fontSize));
        editor.commit();
        mainPresenter.notifyUsersConfigChanged();
    }

}
