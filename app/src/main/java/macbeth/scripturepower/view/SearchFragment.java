package macbeth.scripturepower.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.Config;
import macbeth.scripturepower.model.Library;
import macbeth.scripturepower.model.SearchRecord;
import macbeth.scripturepower.model.Verse;
import macbeth.scripturepower.presenter.MainPresenter;
import macbeth.scripturepower.presenter.SearchPresenter;

// TODO: Click to view in Browse view (move to top)
// TODO: Create a waiting spinner and turn off when data is ready

public class SearchFragment extends Fragment implements MainPresenter.Listener {
    private View rootView;
    private SearchView etSearch;
    private RecyclerView rvResults;
    private TextView tvResultsCount;
    private SearchPresenter searchPresenter;
    private Spinner spFilter;
    private GestureDetector gd;
    // TODO: Save the Adapters to simplify the code below

    /**
     * Create the SearchPresenter.  The library will not be connected to the SearchPresenter until
     * the MainPresenter notifies this fragment that it is ready.  RootView is initialized to null
     * so that the onCreateView will initialize properly.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPresenter = new SearchPresenter();
        rootView = null;
    }

    /**
     * Initialize the view of the Fragment (RootView).  If the fragment goes out of scope and comes
     * back, this function will be called again.  However, the code should not be repeated.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) { // If null, then this is the first loading the fragment

            // Inflate the layout file
            rootView = inflater.inflate(R.layout.fragment_search, container, false);

            // Create layout objects for later use
            etSearch = rootView.findViewById(R.id.tv_search_term);
            spFilter = rootView.findViewById(R.id.sp_search_filter);
            tvResultsCount = rootView.findViewById(R.id.tv_result_count);
            rvResults = rootView.findViewById(R.id.rv_favorite_results);

            // Initialize layout object values
            tvResultsCount.setText("0 Scripture(s) Found");
            rvResults.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

            // Register with the MainPresenter via the MainActivity to be notified of library
            // and config data

            ((MainActivity) getActivity()).registerFragment(this);
        }

        return rootView;
    }

    /**
     * Notification that the library and config are ready for use.  The SearchPresenter will be
     * notified of the library.  The RecyclerView will be connected to the Library via an
     * Adapter.  The Filter spinner will be updated based on the library.  Listeners for interaction
     * with the user are setup.
     * @param library
     * @param config
     */
    @Override
    public void notifyDataReady(Library library, Config config) {
        searchPresenter.setLibrary(library);

        // Setup Adapter for List of Verses.  The adapter receives the config so that it can
        // display information per the config.
        rvResults.setAdapter(new SearchVerseAdapter(searchPresenter.getSearchResults(), config));

        // Setup Adapter for Filter spinner
        List<String> filterOptions = new ArrayList<String>();
        filterOptions.add("All Volumes");
        filterOptions.addAll(searchPresenter.getVolumeTitles());
        spFilter.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, filterOptions));

        // Listner for changes in the Spinner
        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * If the filter spinner changes, then update the search results.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateSearchResults();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Listener for changes in the Search Text
        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                updateSearchResults();
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                updateSearchResults();
                return false;
            }
        });

        // The GestureDetector works with the OnItemTouchListener which is only available for
        // the entire RecylerView.
        gd = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View child = rvResults.findChildViewUnder(e.getX(),e.getY());
                    int position = rvResults.getChildAdapterPosition(child);
                    SearchRecord record = searchPresenter.getSearchResults().getData().get(position);
                    ((MainActivity)getActivity()).loadBrowseScripture(record);
                }
                return super.onDoubleTapEvent(e);
            }
        });
        rvResults.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    /**
     * If the config changes, then update the Adapter so the new config can be used.
     */
    @Override
    public void notifyConfigChanged() {
        rvResults.getAdapter().notifyDataSetChanged();
    }

    /**
     * Request the Search Presenter to udpate the search results.  The results are updated on the
     * screen.
     */
    private void updateSearchResults() {
        String volumeFilter = (String) spFilter.getSelectedItem();
        if (volumeFilter.equals("All Volumes")) {
            volumeFilter = null;
        }
        searchPresenter.searchTerm(etSearch.getQuery().toString(), volumeFilter);
        rvResults.getAdapter().notifyDataSetChanged();
        tvResultsCount.setText(String.valueOf(searchPresenter.getSearchResults().getData().size()) + " Scripture(s) Found");

    }

}