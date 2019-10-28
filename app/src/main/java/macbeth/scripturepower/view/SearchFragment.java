package macbeth.scripturepower.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import macbeth.scripturepower.R;
import macbeth.scripturepower.presenter.MainPresenter;
import macbeth.scripturepower.presenter.SearchPresenter;

// TODO: Show how many matches
// TODO: Highlight term in results
// TODO: Say "No Matches" if no matches
// TODO: Click to view in Browse view (move to top)

public class SearchFragment extends Fragment implements MainPresenter.Listener {
    private View rootView;
    private EditText etSearch;
    private RecyclerView rvResults;
    private SearchPresenter searchPresenter;
    private MainPresenter mainPresenter;
    private Spinner spFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = ((MainActivity) getActivity()).getPresenter();
        searchPresenter = new SearchPresenter(mainPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        etSearch = rootView.findViewById(R.id.tv_search_term);
        spFilter = rootView.findViewById(R.id.sp_search_filter);


        rvResults = rootView.findViewById(R.id.rv_favorite_results);
        rvResults.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String volumeFilter = (String) spFilter.getSelectedItem();
                if (volumeFilter.equals("All Volumes")) {
                    volumeFilter = null;
                }
                searchPresenter.searchTerm(charSequence.toString(), volumeFilter);
                rvResults.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mainPresenter.registerUsers(this); // Must be done after everything else is done
        return rootView;
    }

    @Override
    public void notifyDataReady() {
        rvResults.setAdapter(new SearchVerseAdapter(searchPresenter.getSearchResults(), mainPresenter.getConfig().getFontSize()));

        List<String> filterOptions = new ArrayList<String>();
        filterOptions.add("All Volumes");
        filterOptions.addAll(searchPresenter.getVolumeTitles());
        spFilter.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, filterOptions));
        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String volumeFilter = (String) spFilter.getSelectedItem();
                if (volumeFilter.equals("All Volumes")) {
                    volumeFilter = null;
                }
                searchPresenter.searchTerm(etSearch.getText().toString(), volumeFilter);
                rvResults.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void notifyConfigChanged() {
        if (rvResults.getAdapter() != null) {
            ((SearchVerseAdapter) (rvResults.getAdapter())).setFontSize(mainPresenter.getConfig().getFontSize());
            rvResults.getAdapter().notifyDataSetChanged();
        }
    }

}