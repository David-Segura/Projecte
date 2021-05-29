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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.ComandaActivity;
import com.example.restaurantapp.R;

import java.util.Arrays;
import java.util.List;

import GestioRestaurant.NMLineaComanda;
import GestioRestaurant.NMPlat;

public class LineaComandaAdapter extends RecyclerView.Adapter<LineaComandaAdapter.ViewHolder> implements View.OnClickListener {

    private List<NMLineaComanda> mLinea;
    private Context mCon;
    private ComandaActivity mActivity;

    public LineaComandaAdapter(List<NMLineaComanda> m, ComandaActivity activity, Context con){
        mLinea = m;
        mCon = con;
        mActivity = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.fitxa_linea_comanda;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new LineaComandaAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NMLineaComanda lc = mLinea.get(position);
        Log.d("XXX", "Linea: " + lc.getNum());
        holder.txvQtat.setText(lc.getQuantitat()+"");
        holder.txvNomPlatComanda.setText(lc.getItem().getNom());
        holder.txvPreuPlatComanda.setText(lc.getItem().getPreu()+"");
        holder.txvPreuTotalLinea.setText(lc.getItem().getPreu() * lc.getQuantitat()+"");




    }

    @Override
    public int getItemCount() {
        return mLinea.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txvQtat;
        public TextView txvNomPlatComanda;
        public TextView txvPreuPlatComanda;
        public TextView txvPreuTotalLinea;


        int filaSeleccionada;
        public ViewHolder(@NonNull View fila) {
            super(fila);
            txvQtat = itemView.findViewById(R.id.txvQtat);
            txvNomPlatComanda = itemView.findViewById(R.id.txvNomPlatComanda);
            txvPreuPlatComanda = itemView.findViewById(R.id.txvPreuPlatComanda);
            txvPreuTotalLinea = itemView.findViewById(R.id.txvPreuTotalLinea);

            /*fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filaSeleccionada = getAdapterPosition();

                    //mActivity.onTaulaSelected(mTaules,filaSeleccionada);

                }
            });*/


        }


    }
}
