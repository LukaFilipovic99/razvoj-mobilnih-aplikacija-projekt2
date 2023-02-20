package filipovic.football_club_crud_app.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
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

    private static final int TAKING_PICTURE = 1;

    private static final String JPG_SUFFIX = ".jpg";

    public void setUpdateMode(Boolean updateMode) {
        this.updateMode = updateMode;
    }

    private Boolean updateMode;

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
    Button btnSave;

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

        if (Objects.nonNull(updateMode) && updateMode == Boolean.TRUE) {
            updateMode();
        } else {
            createMode();
        }

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

        footballClubViewModel.getFootballClub().setFounded(new Date());
        tvDate.setText(new SimpleDateFormat("dd.MM.yyyy")
                .format(footballClubViewModel.getFootballClub().getFounded()));

        btnSave.setOnClickListener(view -> updateOrSaveFootballClub());
    }


    private void updateMode() {
        if (Objects.nonNull(footballClubViewModel.getFootballClub().getLogoUrl())) {
            btnTakePicture.setText(R.string.btn_change_image);
        }

        FootballClub club = footballClubViewModel.getFootballClub();

        etName.setText(club.getName());
        etCoach.setText(club.getCoach());
        etStadium.setText(club.getStadium());
        tvDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(club.getFounded()));
        tvLeague.setText(club.getLeague().getName());

        if (Objects.nonNull(club.getLogoUrl())) {
            Picasso.get().load(club.getLogoUrl()).fit().into(ivLogo);
        }

        btnSave.setOnClickListener(view -> updateOrSaveFootballClub());
    }

    @OnClick(R.id.btnSave)
    public void updateOrSaveFootballClub() {
        footballClubViewModel.getFootballClub().setName(etName.getText().toString());
        footballClubViewModel.getFootballClub().setCoach(etCoach.getText().toString());
        footballClubViewModel.getFootballClub().setStadium(etStadium.getText().toString());

        if (Objects.nonNull(updateMode) && updateMode == Boolean.TRUE) {
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
    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Objects.isNull(takePictureIntent.resolveActivity(getActivity().getPackageManager()))) {
            return;
        }

        File picture = null;

        try {
            picture = createPictureFile();
        } catch (Exception e) {
            return;
        }

        if (Objects.isNull(picture)) {
            return;
        }

        footballClubViewModel.getFootballClub().setLogoUrl("file:" + picture.getAbsolutePath());

        Uri pictureUri = FileProvider.getUriForFile(getActivity(),
                getString(R.string.file_provider_authority),
                picture);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(takePictureIntent, TAKING_PICTURE);
    }

    private File createPictureFile() throws IOException {
        String pictureName = footballClubViewModel.getFootballClub().getName() + "_logo" +
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File pictureFile = File.createTempFile(pictureName, JPG_SUFFIX, directory);

        return pictureFile;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKING_PICTURE && resultCode == Activity.RESULT_OK) {
            Picasso.get().load(footballClubViewModel.getFootballClub().getLogoUrl())
                    .fit()
                    .into(ivLogo);
        }
    }
}

