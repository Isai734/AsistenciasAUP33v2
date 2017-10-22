package com.gestionuap33.www.asistenciasaup33.ui.fragments;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Alumno;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.sync.SyncAdapter;
import com.gestionuap33.www.asistenciasaup33.ui.adapters.AlumnoAdapter;
import com.gestionuap33.www.asistenciasaup33.ui.adapters.AlumnoEditAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class AlumnoEditFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    public static final String ARG_ID_LISTA = "column-count";
    private static final String  TAG = "AlumnoFragment";
    // TODO: Customize parameters
    private int idLista = 0;
    private AlumnoFragment.OnListAlumnoListener mListener;
    private AlumnoEditAdapter alumnoAdapter;
    private  String selection;

    /**
     * Mandatory empty constructor for the fragmjava.lang.Stringent manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlumnoEditFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlumnoEditFragment newInstance(int columnCount) {
        AlumnoEditFragment fragment = new AlumnoEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_LISTA, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            selection = getArguments().getString("selection");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itemalumno_list, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_s_asistencia);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarAlerta();
            }
        });
        alumnoAdapter = new AlumnoEditAdapter(null, mListener);
        // Set the adapter

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_alumno);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(alumnoAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AlumnoFragment.OnListAlumnoListener) {
            mListener = (AlumnoFragment.OnListAlumnoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void guardarAsistencia() {
        HashMap<Long, String> hashMap = alumnoAdapter.mapAsistentes;
        try {
            for (Map.Entry<Long, String> entry : hashMap.entrySet()) {
                Uri uri = ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA, entry.getKey());
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasRegistroAsistencia.DETALLE, entry.getValue());
                values.put(ContratoAsistencia.ColumnasRegistroAsistencia.SYNC_DONE,"N");
                values.put(ContratoAsistencia.ColumnasRegistroAsistencia.ESTADO, SyncAdapter.ESTADO_SYNC);
                getActivity().getContentResolver().update(uri, values,null,null);

            }
            //SyncAdapter.sincronizarAhora(getContext());
            Toast.makeText(getContext(), "Actualizado correctamnete", Toast.LENGTH_LONG).show();
            getActivity().finish();

        } catch (Exception e) {
            Snackbar.make(getView(), "Error al gurdar el registro", Snackbar.LENGTH_LONG).show();

        }
   }

    public void lanzarAlerta() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Actualizar!");
        dialog.setMessage("Seguro que desea actuzalizar el registro de asistencia?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarAsistencia();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG,"Selection : "+selection);
        return new CursorLoader(getActivity(),
                ContratoAsistencia.CONTENT_URI_ALUMNO_VIEW,
                null, selection, null, ContratoAsistencia.ColumnasAlumno.NOMBRE + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        alumnoAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        alumnoAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
