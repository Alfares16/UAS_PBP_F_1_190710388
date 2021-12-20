package com.uas.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uas.hospital.api.ApiClient;
import com.uas.hospital.api.ApiInterface;
import com.uas.hospital.models.Pasien;
import com.uas.hospital.models.PasienResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditActivity extends AppCompatActivity {
    private static final String[] RUANGAN_LIST = new String[]{"PisangRoom", "PepayaRoom", "DurianRoom",
            "ApelRoom", "AnggurRoom", "SawoRoom"};
    private static final String[] JENIS_KELAMIN_LIST = new String[]{"Pria",
            "Wanita"};
    private ApiInterface apiService;
    private EditText etName, etUmur;
    private AutoCompleteTextView edRuangan, edJenisKelamin;
    private LinearLayout layoutLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        etName = findViewById(R.id.et_nama);
        etUmur = findViewById(R.id.et_umur);
        edRuangan = findViewById(R.id.ed_ruangan);
        edJenisKelamin = findViewById(R.id.ed_jenis_kelamin);
        layoutLoading = findViewById(R.id.layout_loading);
        ArrayAdapter<String> adapterFakultas =
                new ArrayAdapter<>(this, R.layout.item_list, RUANGAN_LIST);
        edRuangan.setAdapter(adapterFakultas);
        ArrayAdapter<String> adapterJenisKelamin =
                new ArrayAdapter<>(this, R.layout.item_list, JENIS_KELAMIN_LIST);
        edJenisKelamin.setAdapter(adapterJenisKelamin);
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);
        long id = getIntent().getLongExtra("id", -1);
        if (id == -1) {
            tvTitle.setText(R.string.tambah_pasien);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPasien();
                }
            });
        } else {
            tvTitle.setText(R.string.edit_pasien);
            getPasienById(id);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatePasien(id);
                }
            });
        }
    }
    private void getPasienById(long id) {
        setLoading(true);
        Call<PasienResponse> call = apiService.getPasienById(id);
        call.enqueue(new Callback<PasienResponse>() {
            @Override
            public void onResponse(Call<PasienResponse> call,
                                   Response<PasienResponse> response) {
                if (response.isSuccessful()) {
                    Pasien pasien = response.body().getPasienList().get(0);
                    etName.setText(pasien.getNama());
                    etUmur.setText(pasien.getUmur());
                    edRuangan.setText(pasien.getRuangan(), false);
                    edJenisKelamin.setText(pasien.getJenisKelamin(), false);
                } else {
                    try {
                        JSONObject jObjError = new
                                JSONObject(response.errorBody().string());
                        Toast.makeText(AddEditActivity.this,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AddEditActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                setLoading(false);
            }
            @Override
            public void onFailure(Call<PasienResponse> call, Throwable t) {
                Toast.makeText(AddEditActivity.this,
                        "Network error", Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        });
    }
    private void createPasien() {
        setLoading(true);
        Pasien pasien = new Pasien(
                etName.getText().toString(),
                etUmur.getText().toString(),
                edJenisKelamin.getText().toString(),
                edRuangan.getText().toString());
        Call<PasienResponse> call = apiService.createPasien(pasien);
        call.enqueue(new Callback<PasienResponse>() {
            @Override
            public void onResponse(Call<PasienResponse> call,
                                   Response<PasienResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEditActivity.this,
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new
                                JSONObject(response.errorBody().string());
                        Toast.makeText(AddEditActivity.this,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AddEditActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                setLoading(false);
            }
            @Override
            public void onFailure(Call<PasienResponse> call, Throwable t) {
                Toast.makeText(AddEditActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        });
    }
    private void updatePasien(long id) {
        setLoading(true);
        Pasien pasien = new Pasien(
                etName.getText().toString(),
                etUmur.getText().toString(),
                edJenisKelamin.getText().toString(),
                edRuangan.getText().toString());
        Call<PasienResponse> call = apiService.updatePasien(id, pasien);
        call.enqueue(new Callback<PasienResponse>() {
            @Override
            public void onResponse(Call<PasienResponse> call,
                                   Response<PasienResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEditActivity.this,
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new
                                JSONObject(response.errorBody().string());
                        Toast.makeText(AddEditActivity.this,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AddEditActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                setLoading(false);
            }
            @Override
            public void onFailure(Call<PasienResponse> call, Throwable t) {
                Toast.makeText(AddEditActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        });
    }

    // Fungsi untuk menampilkan layout loading
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }
}