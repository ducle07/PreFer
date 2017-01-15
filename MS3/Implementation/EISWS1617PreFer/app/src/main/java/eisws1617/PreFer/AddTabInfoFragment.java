package eisws1617.PreFer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddTabInfoFragment extends Fragment {

    public interface OnDataPass {
        void onDataPass(JSONObject data);
    }

    OnDataPass dataPass;
    EditText editText = (EditText) findViewById(R.id.editText_name);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPass = (AddTabInfoFragment.OnDataPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDataPass");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_tab_info, container, false);
        return rootView;
    }

    public void passData(JSONObject data) {
        dataPass.onDataPass(data);
    }

    public JSONObject json() {
        JSONObject name = new JSONObject();
        try {
            name.put("name", editText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
}