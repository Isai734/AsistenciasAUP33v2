package com.gestionuap33.www.asistenciasaup33.ui.adapters;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Lista;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.ListaAsistenciasFragment.OnListTagListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Lista} and makes a call to the
 * specified {@link OnListTagListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private static final String TAG = ListaAdapter.class.getSimpleName();
    private Cursor mCursor;
    private final OnListTagListener mListener;

    public ListaAdapter(Cursor items, OnListTagListener listener) {
        mCursor = items;
        mListener = listener;
    }

    @Nullable
    public Cursor swapCursor(Cursor cursor) {
        Log.i(TAG, "swapCursor");
        if (this.mCursor == cursor) {
            return null;
        }
        this.mCursor = cursor;
        if (cursor != null) {
            //fillHolders();
            this.notifyDataSetChanged();
        }
        return cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        Lista lista = cursorToLista(mCursor);
        holder.mItem = lista;
        holder.mSemestre.setText(lista.getHorarioDetalleEntrada()+" - "+lista.getHorarioDetalleSalida());
        holder.mGrupo.setText(lista.getListaGrupo());
        holder.mTurno.setText(lista.getUnidadDeAprendizajeNombre());
        holder.mBtnPasarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListTagListener(holder.mItem);
                }
            }
        });
    }

    private Lista cursorToLista(Cursor cursor) {
        return new Lista(
                cursor.getInt(cursor.getColumnIndex(ContratoAsistencia.ColumnasLista._ID)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasLista.GRUPO)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasLista.PERIODO)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasLista.SEMESTRE)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasHorario.ENTRADA)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasHorario.SALIDA)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasLista.UNIDAD_NOMBRE)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasLista.TURNO)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasHorario.DIA))
        );


    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSemestre;
        public final TextView mGrupo;
        public final TextView mTurno;
        public final Button mBtnPasarLista;

        public Lista mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSemestre = (TextView) view.findViewById(R.id.txv_semestre);
            mGrupo = (TextView) view.findViewById(R.id.txv_grupo);
            mTurno = (TextView) view.findViewById(R.id.txv_turno);
            mBtnPasarLista = (Button) view.findViewById(R.id.btn_pasar_lista);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mGrupo.getText() + "'";
        }
    }
}
