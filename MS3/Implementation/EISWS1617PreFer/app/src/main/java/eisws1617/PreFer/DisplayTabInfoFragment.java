package eisws1617.PreFer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Thuy Trang Nguyen on 14.01.2017.
 */

public class DisplayTabInfoFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.fragment_display_tab_info, container, false);
        return rootView;
    }
}