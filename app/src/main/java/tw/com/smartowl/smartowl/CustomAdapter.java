package tw.com.smartowl.smartowl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MING on 2017/11/2.
 */

public class CustomAdapter extends BaseAdapter{
    Context c;
    ArrayList<Product> product;
    LayoutInflater inflater;
    private RecyclerView.ViewHolder holder;


    public CustomAdapter(Context c, ArrayList<Product> product) {
        this.c = c;
        this.product = product;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return product.size();
    }

    @Override
    public Object getItem(int position) {
        return product.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.listview_model, parent, false);
        }

        TextView nametxt = (TextView) convertView.findViewById(R.id.lv_name);
        nametxt.setText(product.get(position).name);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        String url = product.get(position).jpg;
        Picasso.with(c).load(url).into(imageView);


        return convertView;
    }

    public int clear() {
        //this.product.clear();
        notifyDataSetChanged();
        return 0;
    }

    public int add(Product product) {
        //this.product.add(product);
        notifyDataSetChanged();
        return 0;
    }
}
