package filipovic.football_club_crud_app.view;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import filipovic.football_club_crud_app.R;
import filipovic.football_club_crud_app.model.FootballClub;
import filipovic.football_club_crud_app.model.League;
import filipovic.football_club_crud_app.view_model.FootballClubViewModel;

public class CUDFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final int GALLERY_REQUEST_CODE = 1000;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.spinnerLeague)
    Spinner spinnerLeague;

    @BindView(R.id.tvDateFounded)
    TextView tvDate;

    @BindView(R.id.tvLeague)
    TextView tvLeague;

    @BindView(R.id.ivLogo)
    ImageView ivLogo;

    @BindView(R.id.etCoach)
    EditText etCoach;

    @BindView(R.id.etStadium)
    EditText etStadium;

    @BindView(R.id.btnChangeDate)
    Button btnChangeDate;

    @BindView(R.id.btnPicture)
    Button btnTakePicture;

    @BindView(R.id.btnSave)
    Button btnUpdate;

    @BindView(R.id.btnBack)
    Button btnBack;

    @BindView(R.id.btnDelete)
    Button btnDelete;

    private FootballClubViewModel footballClubViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);
        initComponents();

        if (footballClubViewModel.getFootballClub().getId() != 0) {
            updateMode();
        }

        createMode();

        return view;
    }

    private void initComponents() {
        footballClubViewModel = ((MainActivity) getActivity()).getFootballClubViewModel();

        Picasso.get().load(R.drawable.upload).fit().into(ivLogo);

        String leagues[] = {League.FA_PREMIER_LEAGUE.getName(), League.LIGUE_1.getName(),
                League.SERIE_A.getName(), League.LA_LIGA.getName(), League.BUNDESLIGA.getName()};

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, leagues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeague.setAdapter(adapter);
        spinnerLeague.setOnItemSelectedListener(this);
    }

    private void createMode() {
        btnDelete.setVisibility(View.GONE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOrSaveFootballClub();
            }
        });
    }


    private void updateMode() {
        FootballClub club = footballClubViewModel.getFootballClub();

        etName.setText(club.getName());
        etCoach.setText(club.getCoach());
        etStadium.setText(club.getStadium());
        tvDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(club.getFounded()));
        tvLeague.setText(club.getLeague().getName());

        if (Objects.nonNull(club.getLogoUrl())) {
            Picasso.get().load(club.getLogoUrl()).fit().into(ivLogo);
        }
    }

    @OnClick(R.id.btnSave)
    public void updateOrSaveFootballClub() {
        footballClubViewModel.getFootballClub().setName(etName.getText().toString());
        footballClubViewModel.getFootballClub().setCoach(etCoach.getText().toString());
        footballClubViewModel.getFootballClub().setStadium(etStadium.getText().toString());

        if (footballClubViewModel.getFootballClub().getId() != 0) {
            footballClubViewModel.update();
        } else {
            footballClubViewModel.create();
        }

        backToTheReadFragment();
    }


    @OnClick(R.id.btnDelete)
    public void deleteFootballClub() {
        footballClubViewModel.delete();
        backToTheReadFragment();
    }

    @OnClick(R.id.btnBack)
    public void backToTheReadFragment() {
        ((MainActivity) getActivity()).read();
    }

    // Source: https://www.geeksforgeeks.org/datepicker-in-android/
    @OnClick(R.id.btnChangeDate)
    public void selectDate() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    footballClubViewModel.getFootballClub().setFounded(
                            // Deprecated
                            new Date(year1 - 1900, monthOfYear, dayOfMonth));

                    tvDate.setText(new SimpleDateFormat("dd.MM.yyyy")
                            .format(footballClubViewModel.getFootballClub().getFounded()));
                },
                year, month, day);
        datePickerDialog.show();
    }

    @OnClick(R.id.btnPicture)
    public void uploadPicture() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                footballClubViewModel.getFootballClub().setLogoUrl(data.getData().toString());
                Picasso.get().load(footballClubViewModel.getFootballClub().getLogoUrl())
                        .fit()
                        .into(ivLogo);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                footballClubViewModel.getFootballClub().setLeague(League.FA_PREMIER_LEAGUE);
                tvLeague.setText(League.FA_PREMIER_LEAGUE.getName());
                break;
            case 1:
                footballClubViewModel.getFootballClub().setLeague(League.LIGUE_1);
                tvLeague.setText(League.LIGUE_1.getName());
                break;
            case 2:
                footballClubViewModel.getFootballClub().setLeague(League.SERIE_A);
                tvLeague.setText(League.SERIE_A.getName());
                break;
            case 3:
                footballClubViewModel.getFootballClub().setLeague(League.LA_LIGA);
                tvLeague.setText(League.LA_LIGA.getName());
                break;
            case 4:
                footballClubViewModel.getFootballClub().setLeague(League.BUNDESLIGA);
                tvLeague.setText(League.BUNDESLIGA.getName());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        footballClubViewModel.getFootballClub().setLeague(null);
        tvLeague.setText(null);
    }
}

