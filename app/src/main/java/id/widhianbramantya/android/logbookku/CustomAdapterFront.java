package id.widhianbramantya.android.logbookku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterFront extends RecyclerView.Adapter<CustomAdapterFront.ViewHolder> {

    public Context context;
    public List<MyDataFront> my_data;

    public CustomAdapterFront(Context context, List<MyDataFront> data_list) {
        this.context = context;
        this.my_data = data_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_book,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(my_data.get(position).getId());
        holder.title.setText(my_data.get(position).getTitle());
        holder.date.setText(my_data.get(position).getDate() + " > " + my_data.get(position).getType() + " > " + my_data.get(position).getRoom());
        holder.activity.setText(my_data.get(position).getActivity());
        holder.n_activity.setText(my_data.get(position).getNext_activity());
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView id, title, date, activity, n_activity;

        public ViewHolder(View itemView){
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.log_id);
            title = (TextView) itemView.findViewById(R.id.log_title);
            date = (TextView) itemView.findViewById(R.id.log_date);
            activity = (TextView) itemView.findViewById(R.id.log_activity);
            n_activity = (TextView) itemView.findViewById(R.id.log_next_activity);
        }
    }

}
