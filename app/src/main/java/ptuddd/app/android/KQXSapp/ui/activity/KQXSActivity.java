package ptuddd.app.android.KQXSapp.ui.activity;

import android.app.DatePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ptuddd.app.android.KQXSapp.R;
import ptuddd.app.android.KQXSapp.rest.RestClient;
import ptuddd.app.android.KQXSapp.rest.dto.KQXS;
import ptuddd.app.android.KQXSapp.ui.fragment.RankFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KQXSActivity extends AppCompatActivity {
    EditText editTextDate;
    TextView tvProvinceTitle;
    LinearLayout titleLayout;
    LinearLayout layoutProvince ;
    ConstraintLayout layoutContent;

    Calendar myCalendar = Calendar.getInstance();
    HashMap<String, HashMap> dataFromServer = new HashMap<String, HashMap>();
    List<KQXS> listKQXSdependDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kqxs);
        callAPI();

        titleLayout = (LinearLayout)findViewById(R.id.title);
        editTextDate = (EditText)findViewById(R.id.editTextDate);
        layoutProvince= (LinearLayout) findViewById(R.id.layoutProvinces);
        layoutContent =  (ConstraintLayout) findViewById(R.id.content_frame);
        tvProvinceTitle = (TextView) findViewById(R.id.province);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                update();
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(KQXSActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    private void update() {

        String myFormat = "dd-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextDate.setText(sdf.format(myCalendar.getTime()));

        // clear old data
        if(layoutProvince.getChildCount() > 0)
            layoutProvince.removeAllViews();

        if(layoutContent.getChildCount() > 0)
            layoutContent.removeAllViews();

        titleLayout.setVisibility(View.GONE);

        listKQXSdependDate.clear();

        // search data
        for(Map.Entry<String, HashMap> entryProvince : dataFromServer.entrySet()) {
            String keyProvince = entryProvince.getKey();
            HashMap<String, HashMap> valueProvince = entryProvince.getValue();

            for(Map.Entry<String, HashMap> entryDate : valueProvince.entrySet()) {
                String keyDate = entryDate.getKey();
                HashMap<String, List<String>> valueDate = entryDate.getValue();

                if(keyDate.contentEquals(sdf.format(myCalendar.getTime())))
                {
                    addButton(keyProvince);
                    KQXS kqxs = new KQXS(keyProvince, keyDate,valueDate);
                    listKQXSdependDate.add(kqxs);

                }

            }
        }

    }

    private  void addButton(final String province){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutProvinces);

        Button kqxsProvince = new Button(this);
        kqxsProvince.setText(descriProvince(province));
        layout.addView(kqxsProvince);

        kqxsProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleLayout.setVisibility(View.VISIBLE);
                KQXS kqxs = null;
                for(int i = 0 ; i < listKQXSdependDate.size() ; i++)
                    if(province.contentEquals(listKQXSdependDate.get(i).getProvince())) {
                        kqxs = listKQXSdependDate.get(i);
                        tvProvinceTitle.setText(descriProvince(province));
                    }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = RankFragment.newInstance(kqxs);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

    }


    private void callAPI(){
        Call<HashMap> call = RestClient.getInstance().getUserService().kqxsmn();
        call.enqueue(new Callback<HashMap>() {
            @Override
            public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                if (response.isSuccess()) {
                    dataFromServer =  response.body();
                }
            }

            @Override
            public void onFailure(Call<HashMap> call, Throwable t) {

//                UiUtils.lToast(getContext(), R.string.error_response_server);
            }
        });
        }

    private String descriProvince(String standForString) {

        String province;
        switch (standForString) {
            case "AG":
                province = "An Giang";
                break;
            case "BD":
                province = "Bình Dương";
                break;
            case "BL":
                province = "Bạc Liêu";
                break;
            case "BP":
                province = "Bình Phước";
                break;
            case "BTH":
                province = "Bình Thuận";
                break;
            case "CM":
                province = "Cà Mau";
                break;
            case "HCM":
                province = "Hồ Chí Minh ";
                break;
            case "CT":
                province = "Cần Thơ";
                break;
            default:
                province = "Unknow";
                break;
        }
        return province;
    }
}
