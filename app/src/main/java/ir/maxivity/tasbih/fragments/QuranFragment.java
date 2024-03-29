package ir.maxivity.tasbih.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import ir.maxivity.tasbih.QuranAdyehTextActivity;
import ir.maxivity.tasbih.R;
import ir.maxivity.tasbih.adapters.QuranAdyehAdapter;

public class QuranFragment extends Fragment {

    private JSONArray qurans;
    private View root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.quran_fragment_layout, container, false);
        initViews();
        return root;
    }

    private void initViews() {
        RecyclerView recyclerView = root.findViewById(R.id.quran_recycler);
        EditText search = root.findViewById(R.id.search_surah_input);

        final QuranAdyehAdapter adapter = new QuranAdyehAdapter(getContext(), qurans);
        recyclerView.setAdapter(adapter);

        adapter.setOnSurahClickListener(new QuranAdyehAdapter.OnSurahClick() {
            @Override
            public void onClick(int id, String name) {
                Intent intent = new Intent(getActivity(), QuranAdyehTextActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("QURAN", true);
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            qurans = new JSONArray(readJsonFromAssets());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String readJsonFromAssets() {
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("surah_list.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
