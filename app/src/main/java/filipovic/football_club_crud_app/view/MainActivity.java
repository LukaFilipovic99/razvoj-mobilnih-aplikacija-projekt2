package filipovic.football_club_crud_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ListView;

import filipovic.football_club_crud_app.R;
import filipovic.football_club_crud_app.view_model.FootballClubViewModel;

public class MainActivity extends AppCompatActivity {

    private FootballClubViewModel footballClubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        footballClubViewModel = new ViewModelProvider(this).get(FootballClubViewModel.class);
        read();
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void read() {
        setFragment(new ReadFragment());
    }

    public void cud() {
        setFragment(new CUDFragment());
    }

    public FootballClubViewModel getFootballClubViewModel() {
        return footballClubViewModel;
    }
}