package com.gestionuap33.www.asistenciasaup33.ui.adapters;

import android.database.Cursor;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Alumno;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.AlumnoFragment.OnListAlumnoListener;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alumno} and makes a call to the
 * specified {@link OnListAlumnoListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AlumnoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = AlumnoAdapter.class.getSimpleName();
    private Cursor mCursor;
    private final static int VIEW_TYPE_ITEM = 1;
    private final static int VIEW_TYPE_HEADER = 2;
    private static final String  AS="asistencia";
    private static final String RE="retardo";
    private static final String FA="falta";
    private final OnListAlumnoListener mListener;
    private HashMap<Integer, String> initials;
    private int mPosition = 0;
    LinkedList<String> itemsList = new LinkedList<>();
    public HashMap<Long, String> mapAsistentes;

    public AlumnoAdapter(Cursor items, OnListAlumnoListener listener) {
        initials = new HashMap<>();
        mapAsistentes = new HashMap<>();
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
            setValues();
            fillHeaders();
            this.notifyDataSetChanged();
        }
        return cursor;
    }

    public void setValues() {
        while (mCursor.moveToNext()) {
            long _id = mCursor.getLong(mCursor.getColumnIndex(ContratoAsistencia.ColumnasAlumno._ID));
            if (!mapAsistentes.containsKey(_id))
                mapAsistentes.put(_id, FA);
            Log.d(TAG, "Set values _ID : " + _id);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_alumno, parent, false);
                return new ItemAlumnoVHolder(view);
            case VIEW_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_item, parent, false);
                return new ItemHeaderViewHolder(view);
        }
        return null;
    }

    private void fillHeaders() {
        initials.clear();
        mCursor.moveToPosition(-1);
        while (mCursor.moveToNext()) {
            Alumno alumno = cursorToAlumno(mCursor);
            String inicial = alumno.getAlumnoNombre().substring(0, 1).toUpperCase();
            if (!initials.containsValue(inicial))
                initials.put(mPosition++, inicial);
            Log.i(TAG,"Initials : "+inicial);
            initials.put(mPosition++, mCursor.getPosition() + "");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (initials.get(position).matches("[A-Z]"))
            return VIEW_TYPE_HEADER;
        else
            return VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "Position : " + position);
        if (holder instanceof ItemAlumnoVHolder) {
            mCursor.moveToPosition(Integer.valueOf(initials.get(position)));
            Alumno alumno = cursorToAlumno(mCursor);
            final String _id = mCursor.getString(mCursor.getColumnIndex(ContratoAsistencia.ColumnasAlumno._ID));
            final ItemAlumnoVHolder mHolder = (ItemAlumnoVHolder) holder;
            mHolder.mItem = alumno;
            Log.i(TAG, "Is ItemViewHolder -- Id_litsa : " + alumno.getListaId());
            mHolder.mNombre.setText(alumno.getAlumnoNombre());
            mHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.OnListAlumnoListener(mHolder.mItem);
                    }
                }
            });
            //Seteamos radioButton
            switch (mapAsistentes.get(Long.decode(_id))) {
                case AS:
                    ((ItemAlumnoVHolder) holder).rbAsistio.setChecked(true);
                    Log.i(TAG,"Se cumple A");
                    break;
                case FA:
                    ((ItemAlumnoVHolder) holder).rbFalto.setChecked(true);
                    Log.i(TAG,"Se cumple F");
                    break;
                case RE:
                    ((ItemAlumnoVHolder) holder).rbRetardo.setChecked(true);
                    break;
            }

            //preparamos el pase de lista
            ((ItemAlumnoVHolder) holder).rbAsistio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ItemAlumnoVHolder) holder).rbAsistio.isChecked()) {
                        mapAsistentes.remove(Long.decode(_id));
                        String r=mapAsistentes.put(Long.decode(_id), AS);
                        //Log.i(TAG,)
                        Log.d(TAG, "add Asistio : " + _id+" R: "+r);
                    }
                }
            });
            ((ItemAlumnoVHolder) holder).rbFalto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ItemAlumnoVHolder) holder).rbFalto.isChecked()) {
                        mapAsistentes.remove(Long.decode(_id));
                        mapAsistentes.put(Long.decode(_id), FA);
                        Log.d(TAG, "add Falto : " + _id);
                    }
                }
            });
            ((ItemAlumnoVHolder) holder).rbRetardo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ItemAlumnoVHolder) holder).rbRetardo.isChecked()) {
                        mapAsistentes.remove(Long.decode(_id));
                        mapAsistentes.put(Long.decode(_id), RE);
                        Log.d(TAG, "add Retardo: " + _id);
                    }
                }
            });
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getRandomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(position + "", color);
            //seteamos la imagen de nuestro item actual
            mHolder.mInicialView.setImageDrawable(drawable);
        }

        if (holder instanceof ItemHeaderViewHolder) {
            Log.i(TAG, "Is ItemHeaderViewHolder");
            final ItemHeaderViewHolder mHolder = (ItemHeaderViewHolder) holder;
            mHolder.mTitleView.setText(initials.get(position));
        }
    }

    private Alumno cursorToAlumno(Cursor cursor) {
        return new Alumno(
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasAlumno._ID)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasAlumno.NOMBRE)),
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasAlumno.CURSO)),
                cursor.getInt(cursor.getColumnIndex(ContratoAsistencia.ColumnasAlumno.ID_LISTA))
        );
    }

    @Override
    public int getItemCount() {
        return initials.size();
    }

    public class ItemAlumnoVHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mInicialView;
        public final TextView mNombre;
        public final RadioButton rbAsistio;
        public final RadioButton rbRetardo;
        public final RadioButton rbFalto;
        public Alumno mItem;

        public ItemAlumnoVHolder(View view) {
            super(view);
            mView = view;
            mInicialView = (ImageView) view.findViewById(R.id.imv_lista_alumno);
            mNombre = (TextView) view.findViewById(R.id.txv_nombre_alumno);
            rbAsistio = (RadioButton) view.findViewById(R.id.rdb_asistio);
            rbRetardo = (RadioButton) view.findViewById(R.id.rdb_retardo);
            rbFalto = (RadioButton) view.findViewById(R.id.rdb_falto);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombre.getText() + "'";
        }
    }

    public class ItemHeaderViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;

        public ItemHeaderViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title_header);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
