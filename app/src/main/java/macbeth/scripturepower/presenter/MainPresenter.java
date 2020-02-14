package macbeth.scripturepower.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import macbeth.scripturepower.model.Config;
import macbeth.scripturepower.model.Library;
import macbeth.scripturepower.view.MainActivity;

public class MainPresenter {



    private Library library;
    private Config config;
    private List<MainPresenter.Listener> registeredUsers;
    private boolean libraryReady;
    private Context context;

    public MainPresenter(MainActivity mainActivity) {
        library = new Library(mainActivity);
        context = (Context) mainActivity;
        registeredUsers = new ArrayList<MainPresenter.Listener>();
        libraryReady = false;

    }

    public void initialize() {
        config = Config.readConfig(context);
        DownloadLibraryTask task = new DownloadLibraryTask(this);
        task.execute();

    }

    public void registerUsers(MainPresenter.Listener dataUser) {
        registeredUsers.add(dataUser);
        if (libraryReady) {
            dataUser.notifyDataReady(library, config);
        }
    }

    public void notifyUsersDataReady() {
        libraryReady = true;
        for (MainPresenter.Listener dataUser : registeredUsers) {
            dataUser.notifyDataReady(library, config);
        }
    }

    public void notifyUsersConfigChanged() {
        for (MainPresenter.Listener user : registeredUsers) {
            user.notifyConfigChanged();
        }
    }

    public Library getLibrary() {
        return library;
    }

    public Config getConfig() {
        return config;
    }

    public Context getContext() {
        return context;
    }

    public interface Listener {
        public void notifyDataReady(Library library, Config config);
        public void notifyConfigChanged();
    }


}
