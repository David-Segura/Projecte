package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.ComandaActivity;
import com.example.restaurantapp.R;

import java.util.Arrays;
import java.util.List;

import GestioRestaurant.NMPlat;
import GestioRestaurant.NMTaula;

public class PlatsAdapter extends RecyclerView.Adapter<PlatsAdapter.ViewHolder> implements View.OnClickListener {

    private List<NMPlat> mPlats;
    private Context mCon;
    private ComandaActivity mActivity;

    public PlatsAdapter(List<NMPlat> m, ComandaActivity activity, Context con){
        mPlats = m;
        mCon = con;
        mActivity = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.fitxa_plat_carta;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new PlatsAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NMPlat p = mPlats.get(position);
        Log.d("Plat", "" + p.getNom());
        holder.txvPreu.setText(p.getPreu()+"€");
        holder.txvNomPlat.setText(p.getNom());
        if(p.getFoto() != null) {
            Log.d("XXX", Arrays.toString(p.getFoto()) + "");
            Log.d("XXX", p.getFoto().length + "");
            Bitmap bmp = BitmapFactory.decodeByteArray(p.getFoto(), 0, p.getFoto().length);
            holder.imvFoto.setImageBitmap(bmp);
        }
        if(!p.getDisponible()){
            holder.btnAfegir.setVisibility(View.INVISIBLE);
            holder.btnTreure.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return mPlats.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txvPreu;
        public TextView txvNomPlat;
        public Button btnAfegir;
        public Button btnTreure;
        public ImageView imvFoto;

        int filaSeleccionada;
        public ViewHolder(@NonNull View fila) {
            super(fila);
            txvPreu = itemView.findViewById(R.id.txvPreu);
            txvNomPlat = itemView.findViewById(R.id.txvNomPlat);
            imvFoto = itemView.findViewById(R.id.imvFoto);
            btnAfegir = itemView.findViewById(R.id.btnAfegir);
            btnTreure = itemView.findViewById(R.id.btnTreure);
            /*fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filaSeleccionada = getAdapterPosition();

                    //mActivity.onTaulaSelected(mTaules,filaSeleccionada);

                }
            });*/

            btnAfegir.setOnClickListener(this);
            btnTreure.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnAfegir){
                filaSeleccionada = getAdapterPosition();
                Log.d("XXX","Botó afegir a la posició "+ filaSeleccionada);
                NMPlat p = mPlats.get(filaSeleccionada);
                Log.d("XXX","Nom plat seleccionat: "+p.getNom());
                mActivity.onPlatSelected(p);
            }else if(v.getId() == R.id.btnTreure){
                filaSeleccionada = getAdapterPosition();
                Log.d("XXX","Botó treure a la posició "+ filaSeleccionada);
                NMPlat p = mPlats.get(filaSeleccionada);
                Log.d("XXX","Nom plat seleccionat: "+p.getNom());
                mActivity.onPlatDesseleccionat(p);
            }
        }
    }
}
