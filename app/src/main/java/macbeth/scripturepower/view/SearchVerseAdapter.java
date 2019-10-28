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

public class SearchVerseAdapter extends RecyclerView.Adapter<SearchVerseAdapter.ViewHolder> {

    private List<Verse> data;
    private int fontSize;

    public SearchVerseAdapter(List<Verse> data, int fontSize) {
        this.data = data;
        this.fontSize = fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
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
        viewHolder.getTvText().setTextSize(fontSize);
        viewHolder.getTvText().setText(data.get(i).getReference()+": "+data.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
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