package macbeth.scripturepower.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.Config;
import macbeth.scripturepower.model.Verse;

public class BrowseVerseAdapter extends RecyclerView.Adapter<BrowseVerseAdapter.ViewHolder> {

    private List<Verse> data;
    private Config config;

    public BrowseVerseAdapter(List<Verse> data, Config config) {
        this.data = data;
        this.config = config;
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
        viewHolder.getTvText().setTextSize(config.getFontSize());
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