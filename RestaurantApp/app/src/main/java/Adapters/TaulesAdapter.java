package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.R;
import com.example.restaurantapp.TaulesActivity;

import java.util.List;

import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMTaula;

public class TaulesAdapter extends RecyclerView.Adapter<TaulesAdapter.ViewHolder>{

    private List<NMTaula> mTaules;
    private Context mCon;
    private TaulesActivity mActivity;
    private  NMCambrer mCambrer;

    public TaulesAdapter(List<NMTaula> m, TaulesActivity activity, Context con, NMCambrer cambrer){
        mTaules = m;
        mCon = con;
        mActivity = activity;
        mCambrer = cambrer;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.fitxa_taula;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TaulesAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NMTaula t = mTaules.get(position);
        Log.d("Taula", "" + t.getNumero());
        holder.txvNumTaula.setText(t.getNumero()+"");
        holder.txvNomCambrer.setText(t.getNMComanda().getNMCambrer().getNom());
        holder.imvFrame.setImageResource(R.drawable.capsa_border);


        Integer totalComandes = t.getNMComanda().getTotalLinies();
        if(totalComandes == null)
            totalComandes=0;
        Integer liniesAcabades = t.getNMComanda().getLiniesAcabades();
        if(liniesAcabades == null)
            liniesAcabades = 0;
        int liniesPendents = t.getNMComanda().getLiniesPendents();
        Log.d("XXX", "Comanda "+t.getNMComanda().toString()+ totalComandes +" "+ liniesAcabades +" "+ liniesPendents);




        holder.skbProgresCuina.setMax(totalComandes);
        holder.skbProgresCuina.setProgress(liniesAcabades);

        Log.d("XXX","t.cambrer: " + t.getNMComanda().getNMCambrer().toString());
        Log.d("XXX","cambrer: " + mCambrer.toString());
        if(t.getNMComanda().getNMCambrer().getCodi() == mCambrer.getCodi()){
            Log.d("XXX","taula del cambrer");
            holder.layout.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public int getItemCount() {
        return mTaules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNumTaula;
        public TextView txvNomCambrer;
        public SeekBar skbProgresCuina;
        public ImageView imvFrame;
        int filaSeleccionada;
        public ConstraintLayout layout;
        public ViewHolder(@NonNull View fila) {
            super(fila);
            txvNumTaula = itemView.findViewById(R.id.txvNumTaula);
            txvNomCambrer = itemView.findViewById(R.id.txvNomCambrer);
            imvFrame = itemView.findViewById(R.id.imvFrame);
            layout = itemView.findViewById(R.id.layout);
            skbProgresCuina = fila.findViewById(R.id.skbProgresCuina);
            skbProgresCuina.setEnabled(false);
            fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filaSeleccionada = getAdapterPosition();

                    mActivity.onTaulaSelected(mTaules,filaSeleccionada);

                }
            });

            fila.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    filaSeleccionada = getAdapterPosition();



                    mActivity.onLongTaulaSelected("6",mTaules,filaSeleccionada);
                    return true;
                }
            });
        }
    }
}
