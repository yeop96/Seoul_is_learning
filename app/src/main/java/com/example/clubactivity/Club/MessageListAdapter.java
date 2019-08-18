package com.example.clubactivity.Club;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.clubactivity.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListAdapter extends BaseAdapter {

    Context context;

    public class MessageContents{
        String msg;
        int type;
        private String from_id;
        //private String path;
        private Bitmap profileImage;
        Bitmap image;
        private String nickName;

        public MessageContents(String _msg,int _type, String _from_id){
            this.msg = _msg;
            this.type = _type;
            this.from_id = _from_id;
            //this.path = null;
            this.image = null;
            this.profileImage = null;
        }

        public MessageContents(String _msg, int _type, String _from_id, String nickName, Bitmap profileImage){
            this.msg = _msg;
            this.type = _type;
            this.from_id = _from_id;
            //this.path = null;
            this.image = null;
            this.nickName = nickName;
            this.profileImage = profileImage;
        }

        public MessageContents(int _type, String _from_id, Bitmap image){
            this.msg = "";
            this.type = _type;
            this.from_id = _from_id;
            //this.path = null;
            this.profileImage = null;
            this.image = image;
        }

        public MessageContents(int _type, String _from_id, String nickName, Bitmap profileImage, Bitmap image){
            this.msg = "";
            this.type = _type;
            this.from_id = _from_id;
            //this.path = null;
            this.nickName = nickName;
            this.profileImage = profileImage;
            this.image = image;
        }

        /*
        public MessageContents(int type, String _from_id, String imagePath, Bitmap image){
            this.type = type;
            this.from_id = _from_id;
            //this.path = imagePath;
            this.image = image;
        }*/
    }

    ArrayList<MessageContents> messages;

    public MessageListAdapter(Context context){
        messages = new ArrayList<MessageContents>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //텍스트 전송
    public void addItem(String text, int type, String from_id) {
        if(type == 0) {
            messages.add(new MessageContents(text, type, from_id));
        }
        else if(type == 1){
            //TODO
            //서버에서 id에 해당하는 닉네임, 프로필 이미지 가져와서 넣어야됨
            messages.add(new MessageContents(text, type, from_id,"nickname", (BitmapFactory.decodeResource(context.getResources(), R.drawable.class1) )));
        }
    }

    //이미지 전송시 url로 받을 경우
    //public void addItem(int type, String imagePath, String from_id) { messages.add(new MessageContents(type, from_id, imagePath, null));}

    //이미지 Bitmap으로 받을 경우
    public void addItem(int type, Bitmap image, String from_id) {
        if(type == 0) {
            messages.add(new MessageContents(type, from_id, image));
        }
        else if(type == 1){
            //TODO
            //서버에서 id에 해당하는 닉네임, 프로필 이미지 가져와서 넣어야됨
            messages.add(new MessageContents(type, from_id, "nickname", (BitmapFactory.decodeResource(context.getResources(), R.drawable.class_paint)), image));
        }
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        CustomHolder holder = null;
        TextView text = null;
        LinearLayout layout = null;
        FrameLayout frameLayout = null;
        ImageView imageView = null;
        CircleImageView circleImageView = null;
        TextView nickTextView = null;


        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_message_box, viewGroup, false);

            layout = (LinearLayout)view.findViewById(R.id.chat_message_layout);
            text = (TextView)view.findViewById(R.id.message_text);
            frameLayout = (FrameLayout) view.findViewById(R.id.chat_message_box_layout);
            imageView = (ImageView) view.findViewById(R.id.chat_image);
            circleImageView = (CircleImageView) view.findViewById(R.id.chat_user_circleimage);
            nickTextView = (TextView) view.findViewById(R.id.chat_user_id);


            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.m_FrameLayout = frameLayout;
            holder.m_ImageView = imageView;
            holder.m_nicknameTextView = nickTextView;
            holder.m_profileImageView = circleImageView;
            view.setTag(holder);

        } else{
            holder = (CustomHolder) view.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            frameLayout = holder.m_FrameLayout;
            imageView = holder.m_ImageView;
            circleImageView = holder.m_profileImageView;
            nickTextView = holder.m_nicknameTextView;
        }


        text.setText(messages.get(i).msg);

        //frameLayout.getLayoutParams().width = text.getWidth();
        imageView.setImageBitmap(messages.get(i).image);
        //Glide.with(context).asBitmap().load(messages.get(i).path).into(imageView);

        //내가 보낸건지 남이 보낸건지에 따라 위치밑 상세 설정
        if( messages.get(i).type == 0 ) {
            //내가보낸거
            frameLayout.setBackgroundResource(R.drawable.chat_message_box_i);
            text.setTextColor(Color.BLACK);
            layout.setGravity(Gravity.RIGHT);
            nickTextView.setText("");
            circleImageView.setImageBitmap(null);
        }
        if(messages.get(i).type == 1){
            frameLayout.setBackgroundResource(R.drawable.chat_message_box_you);
            text.setTextColor(Color.BLACK);
            layout.setGravity(Gravity.LEFT);
            //from_id(보낸 사람 식별번호)에 따라 닉네임과 프로필 이미지를 서버에서 가져와 넣어줘야함
            nickTextView.setText(messages.get(i).nickName);
            circleImageView.setImageBitmap(messages.get(i).profileImage);
        }

        //텍스트 전송시
        if(messages.get(i).image != null){
            imageView.getLayoutParams().height = 500;
            imageView.getLayoutParams().width = 500;
            frameLayout.setBackgroundResource(0);
        }
        else{
            imageView.getLayoutParams().height = text.getHeight();
            imageView.getLayoutParams().width = text.getWidth();
        }

        return view;
    }

    private class CustomHolder {
        TextView    m_TextView;
        FrameLayout m_FrameLayout;
        LinearLayout layout;
        ImageView m_ImageView;
        CircleImageView m_profileImageView;
        TextView m_nicknameTextView;
    }

}