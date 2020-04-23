package macbeth.scripturepower.view;

import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.Config;
import macbeth.scripturepower.model.SearchResult;

public class SearchVerseAdapter extends RecyclerView.Adapter<SearchVerseAdapter.ViewHolder> {

    private SearchResult results;
    private Config config;

    public SearchVerseAdapter(SearchResult results, Config config) {
        this.results = results;
        this.config = config;
    }

    @NonNull
    @Override
    public SearchVerseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rv_verse, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVerseAdapter.ViewHolder viewHolder, int i) {
        new VerseMarker(viewHolder).execute(results.getData().get(i).getVerse().getText(), results.getSearchTerm(),
                results.getData().get(i).getVerse().getReference());
    }

    @Override
    public int getItemCount() {
        return results.getData().size();
    }

    private class VerseMarker extends AsyncTask<String,Void,String> {

        private ViewHolder viewHolder;

        public VerseMarker(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            viewHolder.getTvText().setTextSize(config.getFontSize());
            viewHolder.getTvText().setText(Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
        }

        @Override
        protected String doInBackground(String... strings) {
            String verseText = strings[0];
            String searchTerm = strings[1];
            String verseRef = strings[2];
            if (searchTerm.equals("") == false) {
                int pos = 0;
                do {
                    pos = verseText.toUpperCase().indexOf(searchTerm.toUpperCase(), pos);
                    if (pos != -1) {
                        String before = "";
                        String middle = "";
                        String after = "";
                        int prevLength = verseText.length();
                        if (pos > 0) {
                            before = verseText.substring(0, pos);
                        }
                        middle = verseText.substring(pos, pos + searchTerm.length());
                        if (pos + searchTerm.length() < verseText.length()) {
                            after = verseText.substring(pos + searchTerm.length(), verseText.length());
                        }
                        verseText = before + "<font color=\"red\">" + middle + "</font>" + after;
                        pos += (verseText.length() - prevLength);
                    }
                }
                while (pos != -1);
            }
            String text = "<b>"+ verseRef+"</b>: "+verseText;

            return text;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_verse);
        }

        public TextView getTvText() {
            return tvText;
        }

    }
}