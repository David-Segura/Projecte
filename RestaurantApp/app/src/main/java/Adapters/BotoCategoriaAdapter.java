package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.ComandaActivity;
import com.example.restaurantapp.R;

import java.util.List;

import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMCategoria;
import GestioRestaurant.NMLineaComanda;

public class BotoCategoriaAdapter extends RecyclerView.Adapter<BotoCategoriaAdapter.ViewHolder> implements View.OnClickListener {

    private List<NMCategoria> mCate;
    private Context mCon;
    private ComandaActivity mActivity;

    public BotoCategoriaAdapter(List<NMCategoria> m, ComandaActivity activity, Context con){
        mCate = m;
        mCon = con;
        mActivity = activity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.fitxa_boto_categoria;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new BotoCategoriaAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NMCategoria lc = mCate.get(position);
        Log.d("XXX", "Linea: " + lc.getNom());
        holder.btnBotoCategoria.setText(lc.getNom()+"");






    }

    @Override
    public int getItemCount() {
        return mCate.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button btnBotoCategoria;



        int filaSeleccionada;
        public ViewHolder(@NonNull View fila) {
            super(fila);
            btnBotoCategoria = itemView.findViewById(R.id.btnBotoCategoria);


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
