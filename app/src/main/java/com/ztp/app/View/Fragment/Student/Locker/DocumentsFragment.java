package com.ztp.app.View.Fragment.Student.Locker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.ztp.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentsFragment extends Fragment {

    Context context;
    ImageView add;
    ListView listView;
    List<Map<String,String>> dataList = new ArrayList<>();

    public DocumentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        add = view.findViewById(R.id.add);
        listView = view.findViewById(R.id.listView);

        DocumentAdapter documentAdapter = new DocumentAdapter(context,getData());
        listView.setAdapter(documentAdapter);

        return view;
    }

    private List<Map<String,String>> getData()
    {
        Map<String,String> map1 = new HashMap<>();
        map1.put("title","CSO-OCT-17.pdf");
        map1.put("description","Hours log for october");
        dataList.add(map1);

        Map<String,String> map2 = new HashMap<>();
        map2.put("title","CSO-EVENT-17.jpg");
        map2.put("description","CSO outreach event");
        dataList.add(map2);

        return dataList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();

        dataList = new ArrayList<>();
    }
}
