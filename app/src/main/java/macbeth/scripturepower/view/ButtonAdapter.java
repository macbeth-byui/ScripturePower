package macbeth.scripturepower.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import macbeth.scripturepower.R;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    private List<String> data;
    private ButtonAdapterListener listener;

    public ButtonAdapter(ButtonAdapterListener listener, List<String> data) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rv_button, viewGroup, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonAdapter.ViewHolder viewHolder, int i) {
        viewHolder.getTvTitle().setText(data.get(i));
        viewHolder.itemView.setOnClickListener(onClick_View -> {
            listener.onButtonAdapterClick(viewHolder.getTvTitle().getText().toString());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ButtonAdapterListener {
        void onButtonAdapterClick(String text);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private View viewBox;
        //private ButtonAdapter adapter;

        public ViewHolder(View itemView, ButtonAdapter adapter) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_button_name);
            //itemView.setOnClickListener(this);
            //this.adapter = adapter;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }


        @Override
        public void onClick(View view) {
            //listener.onButtonAdapterClick(adapter, tvTitle.getText().toString());
        }
    }
}
