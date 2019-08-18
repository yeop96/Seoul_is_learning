package com.example.clubactivity.Club;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubactivity.Constants;
import com.example.clubactivity.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClubEnterActivity extends AppCompatActivity {
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_enter);

        Intent intent = getIntent();

        ImageView clubImage = findViewById(R.id.club_enter_image);
        title = findViewById(R.id.club_name);
        TextView description = findViewById(R.id.club_description);
        TextView memberNumber = findViewById(R.id.club_memeber_number);

        ChatViewItem item = (ChatViewItem)intent.getExtras().get("item");


        //이미지안넘어감 왜터지는지안나옴
        //byte[] bytes = intent.getByteArrayExtra("clubImage");
        //Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //clubImage.setImageBitmap(bmp);
/*
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), (Uri)intent.getExtras().get("imageUri"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
        //clubImage.setImageBitmap(((BitmapDrawable)item.getIcon()).getBitmap());
        //clubImage.setImageBitmap(bitmap);
        title.setText(intent.getExtras().getString("clubName"));
        description.setText(intent.getExtras().getString("clubDescription"));
        memberNumber.setText(intent.getExtras().get("clubNowMember").toString() + "/" + intent.getExtras().get("clubMaxMember").toString() + "명");
    }

    public void JoinClub(View view) {
        //서버 유저 동호회 목록에 추가, 내 동호회 목록은 서버에서 받아서 업데이트 해야할듯
        Intent intent = new Intent(ClubEnterActivity.this, ChatRoomActivity.class);
        intent.putExtra("clubName", title.getText());
        startActivityForResult(intent, Constants.REQUEST_CLUB_ENTER);
        this.finish();

    }
}