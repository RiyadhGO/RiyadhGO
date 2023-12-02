package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sa.edu.yamamh.riyadhgo.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AcknowledgementFragment extends Fragment {

    private TextView acknowledgementText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_acknowledgement, container, false);
        this.acknowledgementText = root.findViewById(R.id.text_acknowledgement);
        this.acknowledgementText.setText(Html.fromHtml(this.acknowledgementText.getText().toString(), Html.FROM_HTML_MODE_COMPACT));
        return root;
    }
}