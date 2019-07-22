package com.qourier.technicaltest.question2.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * Created by Hoa Nguyen on Jul 22 2019.
 */
public class BindingAdapters {

    @BindingAdapter({"imageURL"})
    public static void imageURL(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
