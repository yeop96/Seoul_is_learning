package com.example.clubactivity.Club;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.clubactivity.AppManager;
import com.example.clubactivity.Constants;
import com.example.clubactivity.Network.NetworkTask;
import com.example.clubactivity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class  ClubFragment extends Fragment {
    private View view;
    FloatingActionButton fab;
    SwipeMenuListView myClub_Listview;
    ListView wholeClub_ListView;
    ChatViewAdapter myClub_adapter;
    ChatViewAdapter searchClub_adapter;
    ChatViewAdapter wholeClub_adapter;
    private EditText searchBar;
    NetworkTask networkTask;
    SharedPreferences preferences;

    public ClubFragment(){

        
        String url = "http://106.10.35.170/ImportClubList.php";
        networkTask = new NetworkTask(this.getContext(), url, 7);
        networkTask.execute();

        String email = AppManager.getInstance().getEmail();
        //Log.e("email", email);
        url = "http://106.10.35.170/ImportMyClubList.php";
        String data = "email=" + email;
        networkTask = new NetworkTask(this.getContext(), url, data, 8);
        networkTask.execute();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.club, null);

        final TabHost tabHost1 = (TabHost) view.findViewById(R.id.tapHost_chatlist) ;
        tabHost1.setup() ;

        String url = "http://106.10.35.170/ImportClubList.php";
        networkTask = new NetworkTask(this.getContext(), url, 7);
        networkTask.execute();

        String email = AppManager.getInstance().getEmail();
        Log.e("email", email);
        url = "http://106.10.35.170/ImportMyClubList.php";
        String data = "email=" + email;
        networkTask = new NetworkTask(this.getContext(), url, data, 8);
        networkTask.execute();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1_chatlist) ;
        ts1.setIndicator("전체 동호회") ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2_chatlist) ;
        ts2.setIndicator("나의 동호회") ;
        tabHost1.addTab(ts2) ;

        // Adapter 생성
        myClub_adapter = new ChatViewAdapter() ;
        searchClub_adapter = new ChatViewAdapter();
        wholeClub_adapter = new ChatViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        myClub_Listview = (SwipeMenuListView) view.findViewById(R.id.myclub_listview);
        myClub_Listview.setAdapter(myClub_adapter);
        wholeClub_ListView = (ListView) view.findViewById(R.id.wholeclub_listview);
        wholeClub_ListView.setAdapter(wholeClub_adapter);


        wholeClub_adapter.setChatViewItemList(AppManager.getInstance().getWholeClub_Adapter());
        myClub_adapter.setChatViewItemList(AppManager.getInstance().getMyClub_Adapter());
        Log.e("ClubFragment", "업뎃");

        //리스트뷰 스와이프로 지우기
        SetListViewCreator(myClub_Listview);

        wholeClub_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ClubEnterActivity.class);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable)((ChatViewItem)wholeClub_adapter.getItem(i)).getIcon()).getBitmap();
                Bitmap dstBitmap = Bitmap.createScaledBitmap(bitmap, Constants.IMAGE_SIZE, bitmap.getHeight()/(bitmap.getWidth()/Constants.IMAGE_SIZE), true);

                dstBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();

                //Uri clubImageUri = getImageUri(getActivity(), ((BitmapDrawable)((ChatViewItem)wholeClub_adapter.getItem(i)).getIcon()).getBitmap());
                //intent.putExtra("imageUri", clubImageUri);
                intent.putExtra("item", (ChatViewItem)wholeClub_adapter.getItem(i));

                intent.putExtra("clubImage",bytes);
                intent.putExtra("clubName", ((ChatViewItem)wholeClub_adapter.getItem(i)).getTitle());
                intent.putExtra("clubDescription", ((ChatViewItem)wholeClub_adapter.getItem(i)).getDesc());
                intent.putExtra("clubNowMember", ((ChatViewItem)wholeClub_adapter.getItem(i)).getNowMemberNum());
                intent.putExtra("clubMaxMember", ((ChatViewItem)wholeClub_adapter.getItem(i)).getMaxMemberNum());
                intent.putExtra("clubIndex", ((ChatViewItem)wholeClub_adapter.getItem(i)).getItemIndex());
                //Log.e( Integer.toString(((ChatViewItem)wholeClub_adapter.getItem(i)).getMaxMemberNum()) , Integer.toString(((ChatViewItem)wholeClub_adapter.getItem(i)).getMaxMemberNum()));
                startActivityForResult(intent, Constants.REQUEST_CLUB_INTRO_ENTER);
            }
        });

        //아이템 클릭시 채팅방 들어가기
        myClub_Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity() , ((ChatViewItem)myClub_adapter.getItem(i)).getTitle(),Toast.LENGTH_LONG).show();

                /*
                String url = "http://106.10.35.170/ImportMessageList.php";
                String data = getData(AppManager.getInstance().getEmail(), ((ChatViewItem) myClub_adapter.getItem(i)).getItemIndex());
                NetworkTask networkTask = new NetworkTask(getContext(), url, data, Constants.IMPORT_MESSAGELIST, ((ChatViewItem) myClub_adapter.getItem(i)).getTitle(), getActivity());
                networkTask.execute();
                */

                Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                intent.putExtra("clubName", ((ChatViewItem)myClub_adapter.getItem(i)).getTitle());
                intent.putExtra("clubIndex", myClub_adapter.getItemRoomIndex(i));
                startActivityForResult(intent, Constants.REQUEST_MY_CLUB_ENTER);

            }
        });

        //동호회 개설 버튼
        fab = (FloatingActionButton) view.findViewById(R.id.create_club_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddClubActivity.class);
                startActivityForResult(intent, 1); // 요청한 곳을 구분하기 위한 숫자, 의미없음
            }
        });


        searchBar = (EditText) view.findViewById(R.id.search);
        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable edit) {
                String text = searchBar.getText().toString().toLowerCase(Locale.getDefault());
                //내가 소속되지 않은 동호회 리스트 검색
                wholeClub_ListView.setAdapter(searchClub_adapter);
                searchClub_adapter.removeAllItem();

                if (searchBar.length() == 0) {
                    wholeClub_ListView.setAdapter(wholeClub_adapter);
                    // 문자 입력이 없을때는 모든 데이터를 보여준다.
                    //for (ChatViewItem mItem : wholeClub_adapter.chatViewItemList) {
                    //    searchClub_adapter.addItem(mItem);
                    //}
                }
                // 문자 입력을 할때..
                else {
                    // 리스트의 모든 데이터를 검색한다.
                    for (int i = 0; i < wholeClub_adapter.chatViewItemList.size(); i++) {
                        // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                        if (((ChatViewItem)wholeClub_adapter.getItem(i)).getTitle().toLowerCase().contains(text) ||
                                ((ChatViewItem)wholeClub_adapter.getItem(i)).getDesc().toLowerCase().contains(text)) {
                            // 검색된 데이터를 리스트에 추가한다.
                            searchClub_adapter.addItem((ChatViewItem)wholeClub_adapter.getItem(i));
                        }
                    }
                }
                // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
                searchClub_adapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

            }
        });

        //검색 텍스트 모두 지우기
        ImageButton remove_allText_btn = (ImageButton) view.findViewById(R.id.remove_allText_button);
        remove_allText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setText("");
            }
        });


        myClub_Listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long arg3) {

                DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ChatViewItem wholeClubItem = ((ChatViewItem)wholeClub_adapter.getItem(position));
                        myClub_adapter.removeItem(position);

                        if( wholeClubItem.getNowMemberNum() <= 1 )
                            wholeClub_adapter.removeItem(position);
                        else
                            wholeClubItem.setNowMemberNum(wholeClubItem.getNowMemberNum()-1);

                        myClub_adapter.notifyDataSetChanged();
                        wholeClub_adapter.notifyDataSetChanged();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(getActivity())
                        .setTitle("채팅 목록을 삭제하시겠습니까?")
                        .setPositiveButton("예", positiveListener)
                        .setNegativeButton("취소", cancelListener).show();

                return true;
            }

        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            Toast.makeText(getActivity() , "액티비티종료",Toast.LENGTH_LONG).show();

            byte[] bytes = data.getByteArrayExtra("BMP");
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Drawable drawable = new BitmapDrawable(bmp);
            myClub_adapter.addItem(drawable,
                    (String)data.getExtras().get("clubName"), (String)data.getExtras().get("clubDescription"), (int)data.getExtras().get("clubMaxMember"), 1, 0);

            wholeClub_adapter.addItem(drawable,
                    (String)data.getExtras().get("clubName"), (String)data.getExtras().get("clubDescription"), (int)data.getExtras().get("clubMaxMember"), 1, 0);

            //갱신
            myClub_adapter.notifyDataSetChanged();
            wholeClub_adapter.notifyDataSetChanged();
        }
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getData(String email, int room_index){

        String data = "email=" + email + "&room_index=" + room_index;

        return data;
    }

    public void SetListViewCreator(SwipeMenuListView listView){
        //슬라이드로 삭제
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                         getContext().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_white_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        //꾹 눌러서 삭제
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ChatViewItem wholeClubItem = ((ChatViewItem)wholeClub_adapter.getItem(position));

                        String url = "http://106.10.35.170/RemoveClub.php";
                        String data = getData(AppManager.getInstance().getEmail(), myClub_adapter.getItemRoomIndex(position));
                        NetworkTask networkTask = new NetworkTask(getContext(), url, data, Constants.REMOVE_CLUB );
                        networkTask.execute();

                        myClub_adapter.removeItem(position);
                        if( wholeClubItem.getNowMemberNum() <= 1 )
                            wholeClub_adapter.removeItem(position);
                        else
                            wholeClubItem.setNowMemberNum(wholeClubItem.getNowMemberNum()-1);

                        myClub_adapter.notifyDataSetChanged();
                        wholeClub_adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }



}