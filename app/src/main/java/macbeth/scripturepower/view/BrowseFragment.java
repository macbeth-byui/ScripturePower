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
import macbeth.scripturepower.presenter.BrowsePresenter;
import macbeth.scripturepower.presenter.MainPresenter;

// TODO: Auto Book Mark
// TODO: SHow Favorites with highlighting


public class BrowseFragment extends Fragment implements MainPresenter.Listener {
    private View rootView;


    private RecyclerView rvVolume;
    private RecyclerView rvBook;
    private RecyclerView rvChapter;
    private RecyclerView rvVerse;
    private BrowsePresenter browsePresenter;
    private MainPresenter mainPresenter;
    private GestureDetector gd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = ((MainActivity) getActivity()).getPresenter();
        browsePresenter = new BrowsePresenter(mainPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_browse, container, false);
        rvVolume = rootView.findViewById(R.id.rv_volume);
        rvBook = rootView.findViewById(R.id.rv_book);
        rvChapter = rootView.findViewById(R.id.rv_chapter);
        rvVerse = rootView.findViewById(R.id.rv_verses);
        rvVolume.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvBook.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvChapter.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvVerse.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        // TODO: Create a waiting spinner and turn off when data is ready

        mainPresenter.registerUsers(this); // Must be done after everything else is done
        return rootView;
    }

    @Override
    public void notifyDataReady() {
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

        rvVerse.setAdapter(new BrowseVerseAdapter(browsePresenter.getValidVerses(),mainPresenter.getConfig().getFontSize()));
    }

    @Override
    public void notifyConfigChanged() {
        if (rvVerse.getAdapter() != null) {
            ((BrowseVerseAdapter) (rvVerse.getAdapter())).setFontSize(mainPresenter.getConfig().getFontSize());
            rvVerse.getAdapter().notifyDataSetChanged();
        }
    }

    private void updateAdapters() {
        rvVolume.getAdapter().notifyDataSetChanged();
        rvBook.getAdapter().notifyDataSetChanged();
        rvChapter.getAdapter().notifyDataSetChanged();
        rvVerse.getAdapter().notifyDataSetChanged();

    }

}
