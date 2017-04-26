package ptuddd.app.android.KQXSapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import ptuddd.app.android.KQXSapp.R;
import ptuddd.app.android.KQXSapp.rest.dto.KQXS;

/**
 * Created by quangduy on 24/04/2017.
 */

public class RankFragment extends Fragment {
    ListView lv;
    private List<String>lists = new ArrayList<>();
    private ListScoreAdapter listScoreAdapter;
    private String NAME[] = {"2312341","34234234", "123213","231","1232"};
    private List<String>list = new ArrayList<>();

    private static final String KQXS_KEY = "KQXS";
    private KQXS kqxs;


    public static RankFragment newInstance(KQXS kqxs) {
        RankFragment fragment = new RankFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KQXS_KEY, kqxs);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_rank,container,false);
        kqxs = (KQXS) getArguments().getSerializable(
                KQXS_KEY);
        for(int i = 0; i<kqxs.getPrizes().size();i++){
            List<String> prize;
            if(i==8)
                prize = kqxs.getPrizes().get("DB");
            else
                prize = kqxs.getPrizes().get(Integer.toString(i+1));

            String winningNumbers = prize.get(0);
            if(prize.size() > 1){
                for(int j = 1; j < prize.size() ; j++)
                    winningNumbers = winningNumbers.concat(" - ").concat(prize.get(j));
            }

            lists.add(winningNumbers);
        }
        lv = (ListView)view.findViewById(R.id.lvScore);
        listScoreAdapter = new ListScoreAdapter(getContext(),lists);
        lv.setAdapter(listScoreAdapter);
        return view;
    }

    public class ListScoreAdapter extends ArrayAdapter{
        private LayoutInflater inflater;
        private Context context;
        private List<String>lists;

        public ListScoreAdapter( Context context, List<String>lists){
            super(context,0,lists);
            this.context = context;
            this.lists = lists;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ItemHolder itemHolder = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_list,parent,false);
                itemHolder = new ItemHolder(convertView);
                convertView.setTag(itemHolder);
            }
            else{
                itemHolder = (ItemHolder)convertView.getTag();
            }
            for (int i =0; i<lists.size();i++){
                itemHolder.prizeOrderNumber = (TextView)convertView.findViewById(R.id.prizeOrderNumber);
                itemHolder.winningNumbers = (TextView)convertView.findViewById(R.id.winningNumbers);
                if(position == i){
                    itemHolder.prizeOrderNumber.setText(convertNumberToStringPrize(i));
                    itemHolder.winningNumbers.setText(lists.get(i));
                }
            }

            return convertView;
        }


    }
    public class ItemHolder{
        TextView prizeOrderNumber;
        TextView winningNumbers;

        public ItemHolder(View v){
            ButterKnife.bind(this,v);
        }
    }

    private String convertNumberToStringPrize(int i) {

        String prizeString;
        switch (i) {
            case 0:
                prizeString = "Nhất";
                break;
            case 1:
                prizeString = "Nhì";
                break;
            case 2:
                prizeString = "Ba";
                break;
            case 3:
                prizeString = "Tư";
                break;
            case 4:
                prizeString = "Năm";
                break;
            case 5:
                prizeString = "Sáu";
                break;
            case 6:
                prizeString = "Bảy";
                break;
            case 7:
                prizeString = "Tám";
                break;
            default:
                prizeString = "Đặc biệt";
                break;
        }
        return prizeString;
    }

}
