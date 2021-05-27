package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.R;
import com.example.restaurantapp.TaulesActivity;

import java.util.List;

import Model.Plat;
import Model.Taula;

public class TaulesAdapter extends RecyclerView.Adapter<TaulesAdapter.ViewHolder>{

    private List<Taula> mTaules;
    private Context mCon;
    private TaulesActivity mActivity;

    public TaulesAdapter(List<Taula> m, TaulesActivity activity, Context con){
        mTaules = m;
        mCon = con;
        mActivity = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.fitxa_plat;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TaulesAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Taula t = mTaules.get(position);
        Log.d("Taula", "" + t.getNumero());
        holder.txvNumTaula.setText(t.getNumero()+"");
        holder.txvNomCambrer.setText(t.getComanda().getCambrer().getNom());
        holder.imvFrame.setImageResource(R.drawable.capsa_border);

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNumTaula = itemView.findViewById(R.id.txvNumTaula);
            txvNomCambrer = itemView.findViewById(R.id.txvNomCambrer);
            imvFrame = itemView.findViewById(R.id.imvFrame);
        }
    }
}
