package macbeth.scripturepower.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.Config;
import macbeth.scripturepower.model.Library;
import macbeth.scripturepower.model.SearchRecord;
import macbeth.scripturepower.presenter.BrowsePresenter;
import macbeth.scripturepower.presenter.MainPresenter;

// TODO: Auto Book Mark
// TODO: Highlight selected volume, book, and chapter
// TODO: Create a waiting spinner and turn off when data is ready

public class BrowseFragment extends Fragment implements MainPresenter.Listener {
    private View rootView;


    private RecyclerView rvVolume;
    private RecyclerView rvBook;
    private RecyclerView rvChapter;
    private RecyclerView rvVerse;
    private BrowsePresenter browsePresenter;
    // TODO: Save the Adapters to simplify the code below

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        browsePresenter = new BrowsePresenter();
        rootView = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_browse, container, false);

            rvVolume = rootView.findViewById(R.id.rv_volume);
            rvBook = rootView.findViewById(R.id.rv_book);
            rvChapter = rootView.findViewById(R.id.rv_chapter);
            rvVerse = rootView.findViewById(R.id.rv_verses);
            rvVolume.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvBook.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvChapter.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvVerse.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
            ((MainActivity) getActivity()).registerFragment(this); // Ready to receive library and config
        }

        return rootView;
    }

    @Override
    public void notifyDataReady(Library library, Config config) {
        browsePresenter.setLibrary(library);
        // The ButtonAdapterListener was created in the ButtonAdapter to communicate
        // button selections back using the OnClickListener in the ViewHolder.
        rvVolume.setAdapter(new ButtonAdapter(new ButtonAdapter.ButtonAdapterListener() {
            @Override
            public void onButtonAdapterClick(String text) {
                browsePresenter.selectVolume(text);
                updateAdapters();
                rvBook.scrollToPosition(0);
            }
        }, browsePresenter.getValidVolumes()));

        rvBook.setAdapter(new ButtonAdapter(new ButtonAdapter.ButtonAdapterListener() {
            @Override
            public void onButtonAdapterClick(String text) {
                browsePresenter.selectBook(text);
                updateAdapters();
                rvChapter.scrollToPosition(0);
            }
        }, browsePresenter.getValidBooks()));

        rvChapter.setAdapter(new ButtonAdapter(new ButtonAdapter.ButtonAdapterListener() {
            @Override
            public void onButtonAdapterClick(String text) {
                browsePresenter.selectChapter(text);
                updateAdapters();
                rvVerse.scrollToPosition(0);
            }
        }, browsePresenter.getValidChapters()));

        rvVerse.setAdapter(new BrowseVerseAdapter(browsePresenter.getValidVerses(),config));
    }

    @Override
    public void notifyConfigChanged() {
        rvVerse.getAdapter().notifyDataSetChanged();
    }

    private void updateAdapters() {
        rvVolume.getAdapter().notifyDataSetChanged();
        rvBook.getAdapter().notifyDataSetChanged();
        rvChapter.getAdapter().notifyDataSetChanged();
        rvVerse.getAdapter().notifyDataSetChanged();

    }

    public void jumpToScripture(SearchRecord record) {
        browsePresenter.selectVolume(record.getVolume().getTitle());
        browsePresenter.selectBook(record.getBook().getTitle());
        browsePresenter.selectChapter(String.valueOf(record.getChapter().getChapter()));
        updateAdapters();
        rvVerse.scrollToPosition(record.getVerse().getVerse()-1);


    }

}
