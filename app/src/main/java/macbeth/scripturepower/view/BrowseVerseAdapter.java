package macbeth.scripturepower.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.Verse;

public class BrowseVerseAdapter extends RecyclerView.Adapter<BrowseVerseAdapter.ViewHolder> {

    private List<Verse> data;
    int fontSize;

    public BrowseVerseAdapter(List<Verse> data, int fontSize) {
        this.data = data;
        this.fontSize = fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @NonNull
    @Override
    public BrowseVerseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rv_verse, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseVerseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.getTvText().setTextSize(fontSize);
        viewHolder.getTvText().setText(data.get(i).getVerse()+". "+data.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVerse;

        public ViewHolder(View itemView) {
            super(itemView);
            tvVerse = itemView.findViewById(R.id.tv_verse);
        }

        public TextView getTvText() {
            return tvVerse;
        }

    }
}