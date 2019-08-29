package ir.maxivity.tasbih.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;

import co.ronash.pushe.d.e;
import ir.maxivity.tasbih.NasimApplication;
import ir.maxivity.tasbih.R;
import ir.maxivity.tasbih.dataAccess.AzanDB;
import ir.maxivity.tasbih.dataAccess.DataFileAccess;

public class AzanDialogFragment extends DialogFragment {

    CheckBox shiaa, sonni, sobh, zohr, asr, maqrib, ishaa, eqame;
    private AzanDB azans = null;
    private DataFileAccess db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.azan_dialog_fragment, container, false);
        initViews(root);
        Button submit = root.findViewById(R.id.submit_azan);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
                dismiss();
            }
        });

        return root;
    }

    private void initViews(View root) {

        db = new DataFileAccess(this.getContext());

        shiaa = root.findViewById(R.id.shiaa);
        sonni = root.findViewById(R.id.soni);
        sobh = root.findViewById(R.id.sobh_azan_check);
        zohr = root.findViewById(R.id.zohr_azan_check);
        asr = root.findViewById(R.id.asr_azan_checked);
        maqrib = root.findViewById(R.id.maqrib_azan_checked);
        ishaa = root.findViewById(R.id.ishaa_azan_checked);
        eqame = root.findViewById(R.id.eqame_check);

        try {
            azans = (AzanDB) db.readObject(NasimApplication.AZAN_DATAFILE);
            if (azans == null){
                azans = new AzanDB();
                db.writeObject(azans,NasimApplication.AZAN_DATAFILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        shiaa.setChecked(azans.isShia());
        sonni.setChecked(azans.isSuni());
        sobh.setChecked(azans.isFajr());
        zohr.setChecked(azans.isZohr());
        asr.setChecked(azans.isAsr());
        maqrib.setChecked(azans.isMaghrib());
        ishaa.setChecked(azans.isIsha());
        eqame.setChecked(azans.isEghanme());

        shiaa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (sonni.isChecked()) {
                        sonni.setChecked(false);
                    }
                }
            }
        });

        sonni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (shiaa.isChecked()) {
                        shiaa.setChecked(false);
                    }
                }
            }
        });

    }

    private void onSave(){
        this.azans.setAsr(asr.isChecked());
        this.azans.setZohr(zohr.isChecked());
        this.azans.setFajr(sobh.isChecked());
        this.azans.setIsha(ishaa.isChecked());
        this.azans.setMaghrib(maqrib.isChecked());
        this.azans.setEghanme(eqame.isChecked());
        try {
            boolean b = db.writeObject(this.azans,NasimApplication.AZAN_DATAFILE);
            if(b){
                Toast.makeText(this.getContext(),R.string.saved_success,Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this.getContext(),R.string.saved_error,Toast.LENGTH_LONG).show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialogFragment = super.onCreateDialog(savedInstanceState);
        dialogFragment.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogFragment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialogFragment;
    }
}
