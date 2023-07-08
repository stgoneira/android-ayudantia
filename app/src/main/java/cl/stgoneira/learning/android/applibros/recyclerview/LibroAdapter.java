package cl.stgoneira.learning.android.applibros.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cl.stgoneira.learning.android.applibros.R;
import cl.stgoneira.learning.android.applibros.modelo.Libro;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    private List<Libro> libros;
    private OnItemClickListener listener;

    public LibroAdapter(List<Libro> libros) {
        this.libros = libros;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
        notifyItemInserted( libros.size() - 1);
    }

    public void removerLibro(Libro libro) {
        libros.remove(libro);
        notifyDataSetChanged();
    }

    public void modificarLibro(Libro libro, int posicion) {
        libros.set(posicion, libro);
        notifyItemChanged(posicion);
    }

    public interface OnItemClickListener {
        void onEditarClick(Libro libro, int posicion);
        void onEliminarClick(Libro libro, int posicion);
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLibroId;
        private TextView tvLibroTitulo;
        private TextView tvLibroAutor;

        public LibroViewHolder(View view) {
            super(view);
            tvLibroId     = (TextView) view.findViewById(R.id.tvLibroId);
            tvLibroTitulo = (TextView) view.findViewById(R.id.tvLibroTitulo);
            tvLibroAutor  = (TextView) view.findViewById(R.id.tvLibroAutor);

            ImageButton btnEditarLibro      = (ImageButton) view.findViewById(R.id.btnEditarLibro);
            ImageButton btnEliminarLibro    = (ImageButton) view.findViewById(R.id.btnEliminarLibro);

            btnEliminarLibro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ) {
                        int posicion = getAdapterPosition();
                        if( posicion != RecyclerView.NO_POSITION) {
                            Libro libro = libros.get(posicion);
                            listener.onEliminarClick(libro, posicion);
                        }
                    }
                }
            });

            btnEditarLibro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ) {
                        int posicion = getAdapterPosition();
                        if( posicion != RecyclerView.NO_POSITION) {
                            Libro libro = libros.get(posicion);
                            listener.onEditarClick(libro, posicion);
                        }
                    }
                }
            });
        }

        public TextView getTvLibroId() {
            return tvLibroId;
        }

        public TextView getTvLibroTitulo() {
            return tvLibroTitulo;
        }

        public TextView getTvLibroAutor() {
            return tvLibroAutor;
        }
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.libro_row_item, parent, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder viewHolder, int position) {
        viewHolder.getTvLibroId().setText( libros.get(position).getId().toString() );
        viewHolder.getTvLibroTitulo().setText( libros.get(position).getTitulo() );
        viewHolder.getTvLibroAutor().setText( libros.get(position).getAutor() );
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

}
