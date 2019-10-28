package macbeth.scripturepower.presenter;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class DownloadLibraryTask extends AsyncTask<Void, Void, Void> {

    private WeakReference<MainPresenter> presenter;
    private static final String VOL_OT = "Old Testament";
    private static final String VOL_NT = "New Testament";
    private static final String VOL_BOM = "Book of Mormon";
    private static final String VOL_DC = "D&C";
    private static final String VOL_POGP = "Pearl of Great Price";
    private static final String BOM_URL = "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/book-of-mormon.json";
    private static final String OT_URL = "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/old-testament.json";
    private static final String NT_URL = "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/new-testament.json";
    private static final String POGP_URL = "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/pearl-of-great-price.json";
    private static final String DC_URL = "https://raw.githubusercontent.com/bcbooks/scriptures-json/master/doctrine-and-covenants.json";

    public DownloadLibraryTask(MainPresenter presenter) {
        this.presenter = new WeakReference<MainPresenter>(presenter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        presenter.get().getLibrary().loadVolume(VOL_BOM, BOM_URL, true);
        presenter.get().getLibrary().loadVolume(VOL_OT, OT_URL, true);
        presenter.get().getLibrary().loadVolume(VOL_NT, NT_URL, true);
        presenter.get().getLibrary().loadVolume(VOL_DC, DC_URL, false);
        presenter.get().getLibrary().loadVolume(VOL_POGP, POGP_URL, true);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        presenter.get().notifyUsersDataReady();
    }
}
