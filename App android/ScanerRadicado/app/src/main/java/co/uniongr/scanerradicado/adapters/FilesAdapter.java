package co.uniongr.scanerradicado.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import co.uniongr.scanerradicado.R;
import co.uniongr.scanerradicado.dto.Radicados;

public class FilesAdapter extends ArrayAdapter<Radicados> {
    Context context;
    int resource;
    List<Radicados> albumsList;
    public FilesAdapter(Context context, int resource, List<Radicados> albumsList)
    {
        super(context,resource,albumsList);
        this.context = context;
        this.resource = resource;
        this.albumsList = albumsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource,null,false);
        TextView aname = view.findViewById(R.id.albumnamelbl);
        ImageView aimage = view.findViewById(R.id.albumcoverimageview);
        Radicados album = albumsList.get(position);
        aname.setText(album.getArchivo());

        //https://stackoverflow.com/questions/31460795/how-do-i-access-an-android-studio-r-dimen-value
        float size_f = context.getResources().getDimension(R.dimen.size_icon) / 2;
        int size_file = (int) size_f;

        if(album.getExtension().toLowerCase(Locale.ROOT).contains("pdf")){
            Picasso.with(context)
                    .load(R.drawable.pdf)
                    .resize(size_file, size_file)
                    .centerCrop()
                    .into(aimage);
        }else{
            if(album.getExtension().toLowerCase(Locale.ROOT).contains("xls")){
                Picasso.with(context)
                        .load(R.drawable.excel)
                        .resize(size_file, size_file)
                        .centerCrop()
                        .into(aimage);
            } else{
                Picasso.with(context)
                        .load(R.drawable.none)
                        .resize(size_file, size_file)
                        .centerCrop()
                        .into(aimage);
            }
        }

        return view;
    }

}
