package com.awave.apps.droider.Main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awave.apps.droider.Elements.Article.ArticleActivity;
import com.awave.apps.droider.R;
import com.awave.apps.droider.Utils.Utils.FeedItem;
import com.awave.apps.droider.Utils.Utils.Helper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    private String TAG = AdapterMain.class.getSimpleName();

    public static ArrayList<FeedItem> data;

    private static Activity activity;

    private static String shareUrl;
    private static String shareTitle;
    private static String headImage;
    private static Drawable headerImg;
    private static DisplayMetrics mMetrics;

    public AdapterMain(Activity activity, ArrayList<FeedItem> data, DisplayMetrics metrics) {
        this.activity = activity;
        AdapterMain.data = data;
        AdapterMain.mMetrics = metrics;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        public ImageView cardImage;
        private TextView siteUrl;
        private TextView articleTitle;
        private TextView description;

        public ViewHolder(View holderView) {
            super(holderView);
            cardView = (CardView) holderView.findViewById(R.id.card_view);
            cardImage = (ImageView) holderView.findViewById(R.id.card_image);
            siteUrl = (TextView) holderView.findViewById(R.id.siteurl);

            articleTitle = (TextView) holderView.findViewById(R.id.articleTitle_card);
            articleTitle.setTypeface(Helper.getRobotoFont("Light", false, AdapterMain.activity));

            description = (TextView) holderView.findViewById(R.id.articleDescription);
            description.setTypeface(Helper.getRobotoFont("Light", false, AdapterMain.activity));
        }
    }

    @Override
    public AdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new ViewHolder(v);
    }

    public static String getShareUrl() {
        return shareUrl;
    }

    public static void setShareUrl(String shareUrl) {
        AdapterMain.shareUrl = shareUrl;
    }

    public static String getShareTitle() {
        return shareTitle;
    }

    public static void setShareTitle(String shareTitle) {
        AdapterMain.shareTitle = shareTitle;
    }

    public static String getHeadImage() {
        return headImage;
    }

    public static void setHeadImage (String mImage){
        AdapterMain.headImage = mImage;
    }

    public static Drawable getHeaderImage() { return  headerImg; }

    public static void setHeaderImg(Drawable mHeaderImg) { AdapterMain.headerImg = mHeaderImg; }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        final FeedItem item = data.get(i);
        viewHolder.articleTitle.setText(item.getTitle());
        viewHolder.description.setText(item.getDescription());
        viewHolder.siteUrl.setText(item.getLink());

        if (viewHolder.cardImage != null){
            Glide.with(activity).load(item.getImg()).into(viewHolder.cardImage);
        }

        final String url = item.getLink();


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ArticleActivity.Parser().execute(url);
                    Intent article = new Intent(activity, ArticleActivity.class);
                    article.putExtra(Helper.EXTRA_ARTICLE_TITLE, viewHolder.articleTitle.getText().toString());
                    article.putExtra(Helper.EXTRA_SHORT_DESCRIPTION, viewHolder.description.getText().toString());

                    activity.startActivity(article);
                    setShareUrl(url);
                    setShareTitle(item.getTitle());
                    setHeaderImg(viewHolder.cardImage.getDrawable());
                    setHeadImage(item.getImg());
                } catch (Exception e) {
                    // Ошибка происходит если пытаться отправить пикчу
                    // в статью. Сначала он выкидывал NullPointerException
                    // на article в ArticleActivity. Я закомментил
                    // после этого ничего не открывалось
                    Toast.makeText(activity, "Произошла ошибка при открытии статьи!", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onClick: Failed to open ArticleActivity!");
                }
            }
        });
        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onLingClick cardview");
                ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData copyLink = ClipData.newPlainText("", item.getLink());
                clipboardManager.setPrimaryClip(copyLink);
                Snackbar.make(view, "Ссылка скопирована", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}