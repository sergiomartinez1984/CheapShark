package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheapshark.R;
import com.example.cheapshark.modelo.Videojuegos;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder>implements View.OnClickListener{

    List<Videojuegos> listVideojuegos;
    private View.OnClickListener listenerClick;
    private View.OnLongClickListener listenerLong;


    public RecyclerViewAdapter(List<Videojuegos> listVideojuegos) {
        this.listVideojuegos = listVideojuegos;
    }

    // Este método se encarga de crear la estructura de componentes de cada celda de la lista a partir
    // del layout creado (en este caso item_list_personajes.xml)
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //La clase LayoutInflater se usa para crear instancias del contenido de los archivos XML
        // de diseño en sus correspondientes objetos de vista.
        //En otras palabras, toma un archivo XML como entrada y construye los objetos View a partir de él.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_videojuegos, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        view.setOnClickListener(this);

        return recyclerHolder;
    }

    // Esta vista se encarga de enlazar la información con cada celda. Este método se
    // llama una vez se ha creado la vista de cada celda de la lista.
    // lo único que hay que hacer es extraer la información del elemento en la lista
    // y asígnarselo a cada elemento gráfico de la celda.

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Videojuegos videojuego = listVideojuegos.get(position);
        holder.tvTitulo.setText(videojuego.getTitle());

        Glide.with(holder.itemView)
                .load(videojuego.getFoto())
                .placeholder(R.drawable.construccion)
                .error(R.drawable.retro1)
                .into(holder.ivFoto);

    }

    @Override
    public int getItemCount() {
        return listVideojuegos.size();
    }

    @Override
    public void onClick(View view) {
        if(listenerClick != null) {
            listenerClick.onClick(view);
        }
    }
    public void setOnClickListener(View.OnClickListener listenerClick){
        this.listenerClick = listenerClick;
    }



    public boolean onLongClick(View.OnLongClickListener view) {
        return true;
    }
    // Primero se crea la clase que hereda de ViewHolder.
    // Esta clase tiene como finalidad recrear los elementos de la vista del layout
    // de cada elemento de la lista acorde al modelo de datos creado (en este caso la clase Personaje)

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvTitulo;


        // El constructor recibe como parámetro un tipo vista(itemView)
        // que contiene la celda ya creada a partir del layout correspondiente.

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.idImagen);
            tvTitulo = itemView.findViewById(R.id.titulo);



        }
    }
}
