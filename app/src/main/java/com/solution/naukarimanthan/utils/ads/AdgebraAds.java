package com.solution.naukarimanthan.utils.ads;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.solution.naukarimanthan.R;
import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.model.apis.adgebraads.AdgebraViewModel;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AdgebraAds {

    public static final int BIG_CARD = 1;
    public static final int SMALL_CARD = 2;

    public static  <V extends BaseViewModel>  void setNativeAds(V viewModel, Activity activity1,
                                                        AdsCallBack adsCallBack, int adsType) {
        WeakReference<Activity> activity = new WeakReference<>(activity1);
        String uniqueId = viewModel.getDataManager().getUniqueId();
        if(uniqueId == null || uniqueId.equalsIgnoreCase("")){
            uniqueId = String.valueOf(System.currentTimeMillis());
            uniqueId = uniqueId.substring(0, (uniqueId.length() / 2));
            viewModel.getDataManager().setUniqueId(uniqueId);
        }

        String ipAddress = viewModel.getDataManager().getIpAddress();

        if(ipAddress == null  || ipAddress.equalsIgnoreCase("")){
            getIpAddress(viewModel, activity, adsCallBack, uniqueId, adsType);
        }
        else{
            getAdsdata(viewModel, activity.get(), adsCallBack, ipAddress, uniqueId);
        }
    }

    private static   <V extends BaseViewModel> void getIpAddress(V viewModel, WeakReference<Activity> activity,
                                                         AdsCallBack adsCallBack, String uniqueId, int type) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getIpAddressApi()
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        String ipAddress = response;
                        if (!ipAddress.equalsIgnoreCase("")) {
                            viewModel.getDataManager().setIpAddress(ipAddress);
                            // call ads
                            if(type == BIG_CARD) {
                                getAdsdata(viewModel, activity.get(), adsCallBack, ipAddress, uniqueId);
                            }
                            else if(type == SMALL_CARD) {
                                getAdsdataSmall(viewModel, activity.get(), adsCallBack, ipAddress, uniqueId);
                            }
                        }
                        else{
                            adsCallBack.Error();
                        }
                    }
                    else{
                        adsCallBack.Error();
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }



    private static   <V extends BaseViewModel> void getIpAddress1(V viewModel, WeakReference<Activity> activity,
                                                                   AdsCallBack adsCallBack, String uniqueId, int type) {
        Call<String> call = null;
        call = viewModel.getDataManager().getIpAddressApiTmp();

        if (call != null) {
            call.enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull
                        Response<String> response) {
                    if (response != null && response.body() != null) {
                        String ipAddress = response.body().toString();

                        if (ipAddress.equalsIgnoreCase("")) {
                            viewModel.getDataManager().setIpAddress(ipAddress);
                            // call ads
                            if(type == BIG_CARD) {
                                getAdsdata(viewModel, activity.get(), adsCallBack, ipAddress, uniqueId);
                            }
                            else if(type == SMALL_CARD) {
                                getAdsdataSmall(viewModel, activity.get(), adsCallBack, ipAddress, uniqueId);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    call.cancel();
                    Log.e("RFdff", "dsfsdffsdf");

                }
            });
        }
    }

    private static  <V extends BaseViewModel> void getAdsdata(V viewModel, Activity mContext, AdsCallBack mAdsCallBack,
                                                       String ipAddress, String uniqueId) {
        String dfkj = "f";
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getAdgebraAdsApi("3147", "9", uniqueId, "1", "1", ipAddress,
                        "app-up50", "erGg9BN828kYj0p1HcAn")
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        final List<AdgebraViewModel> mAdgebraArrayList = response;
                        if (mAdgebraArrayList.size() > 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                if (mContext.isDestroyed()) {
                                    mAdsCallBack.Error();
                                    return;
                                }
                            }

                            if (mContext.isFinishing()) {
                                mAdsCallBack.Error();
                                return;
                            }

                            if (mAdgebraArrayList.get(0).getTrackerUrl() != null
                                    && !mAdgebraArrayList.get(0).getTrackerUrl().isEmpty()
                                    && mAdgebraArrayList.get(0).getNotificationMessage() != null
                                    && !mAdgebraArrayList.get(0).getNotificationMessage().isEmpty()
                                    && mAdgebraArrayList.get(0).getNotificationTitle() != null
                                    && !mAdgebraArrayList.get(0).getNotificationTitle().isEmpty()
                                    && mAdgebraArrayList.get(0).getIcon() != null
                                    && !mAdgebraArrayList.get(0).getIcon().isEmpty()
                                    && mAdgebraArrayList.get(0).getNotificationImage() != null
                                    && !mAdgebraArrayList.get(0).getNotificationImage().isEmpty()
                                    && mAdgebraArrayList.get(0).getImg990x505() != null
                                    && !mAdgebraArrayList.get(0).getImg990x505().isEmpty()) {


                                LayoutInflater inflater = (LayoutInflater)
                                        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.adgebra_ad_view, null);
                                view.setTag(mAdgebraArrayList.get(0).getTrackerUrl());
                                AppCompatImageView banner = view.findViewById(R.id.banner);
                                AppCompatImageView icon = view.findViewById(R.id.icon);
                                AppCompatTextView msg = view.findViewById(R.id.msg);
                                AppCompatTextView title = view.findViewById(R.id.title);
                                try {
                                    String afterDecodeMessage = URLDecoder.decode(mAdgebraArrayList.get(0).
                                            getNotificationMessage(), "UTF-8");
                                    msg.setText(afterDecodeMessage);

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    msg.setText(mAdgebraArrayList.get(0).getNotificationMessage().
                                            replaceAll("[+]", " "));
                                }
                                try {
                                    String afterDecodeTitle = URLDecoder.decode(mAdgebraArrayList.get(0).
                                            getNotificationTitle(), "UTF-8");
                                    title.setText(afterDecodeTitle);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    title.setText(mAdgebraArrayList.get(0).getNotificationTitle().
                                            replaceAll("[+]", " "));
                                }
/*
                                Glide.with(mContext)
                                        .load(mAdgebraArrayList.get(0).getIcon())
                                        .thumbnail(0.7f)
                                        .error(R.drawable.no_image_found)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(icon);
*/

                                Picasso.get()
                                        .load(mAdgebraArrayList.get(0).getIcon())
                                        .placeholder(R.drawable.logo_small)
                                        .error(R.drawable.no_image)
                                        .fit()
                                        .into(icon);

                                try {
/*
                                    Glide.with(mContext)
                                            .load(!mAdgebraArrayList.get(0).getNotificationImage().isEmpty() ?
                                                    mAdgebraArrayList.get(0).getNotificationImage() :
                                                    mAdgebraArrayList.get(0).getImageUrl())
                                            .error(R.drawable.no_image_found)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(banner);
*/

                                    Picasso.get()
                                            .load(!mAdgebraArrayList.get(0).getNotificationImage().isEmpty() ?
                                                    mAdgebraArrayList.get(0).getNotificationImage() :
                                                    mAdgebraArrayList.get(0).getImageUrl())
                                            .placeholder(R.drawable.logo)
                                            .error(R.drawable.no_image)
                                            .fit()
                                            .into(banner);
                                } catch (OutOfMemoryError ignored) {}
                                catch (Exception ignored) {}
                                view.setOnClickListener(v -> {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(mAdgebraArrayList.get(0).getTrackerUrl()));
                                        mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException anfe) {
                                        try {
                                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(mAdgebraArrayList.get(0).getTrackerUrl()));
                                            mContext.startActivity(intent);
                                        } catch (ActivityNotFoundException ignored) {}
                                    } catch (Exception ignored) {}

                                });
                                if (mAdsCallBack != null) {
                                    mAdsCallBack.adLoaded(view);
                                }
                            } else {
                                if (mAdsCallBack != null) {
                                    mAdsCallBack.Error();
                                }
                            }
                        } else {
                            if (mAdsCallBack != null) {
                                mAdsCallBack.Error();
                            }
                        }
                    } else {
                        if (mAdsCallBack != null) {
                            mAdsCallBack.Error();
                        }
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    if (mAdsCallBack != null) {
                        mAdsCallBack.Error();
                    }
                }));
    }


    private static <V extends BaseViewModel> void getAdsdataSmall(V viewModel, Activity mContext, AdsCallBack mAdsCallBack,
                                                       String ipAddress, String uniqueId) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getAdgebraAdsApi("3147", "9", uniqueId, "1", "1", ipAddress,
                        "app-up50", "erGg9BN828kYj0p1HcAn")
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        final List<AdgebraViewModel> mAdgebraArrayList = response;
                        if (mAdgebraArrayList.size() > 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                if (mContext.isDestroyed()) {
                                    return;
                                }
                            }

                            if (mContext.isFinishing()) {
                                return;
                            }

                            if (mAdgebraArrayList.get(0).getTrackerUrl() != null
                                    && !mAdgebraArrayList.get(0).getTrackerUrl().isEmpty()
                                    && mAdgebraArrayList.get(0).getNotificationMessage() != null
                                    && !mAdgebraArrayList.get(0).getNotificationMessage().isEmpty()
                                    && mAdgebraArrayList.get(0).getNotificationTitle() != null
                                    && !mAdgebraArrayList.get(0).getNotificationTitle().isEmpty()
                                    && mAdgebraArrayList.get(0).getIcon() != null
                                    && !mAdgebraArrayList.get(0).getIcon().isEmpty()
                                    && mAdgebraArrayList.get(0).getNotificationImage() != null
                                    && !mAdgebraArrayList.get(0).getNotificationImage().isEmpty()
                                    && mAdgebraArrayList.get(0).getImg990x505() != null
                                    && !mAdgebraArrayList.get(0).getImg990x505().isEmpty()) {


                                LayoutInflater inflater = (LayoutInflater)
                                        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.adgebra_small_ad_view, null);
                                view.setTag(mAdgebraArrayList.get(0).getTrackerUrl());
                                AppCompatImageView banner = view.findViewById(R.id.banner);
                                AppCompatImageView icon = view.findViewById(R.id.icon);
                                AppCompatTextView msg = view.findViewById(R.id.msg);
                                AppCompatTextView title = view.findViewById(R.id.title);
                                try {
                                    String afterDecodeMessage = URLDecoder.decode(mAdgebraArrayList.get(0).
                                            getNotificationMessage(), "UTF-8");
                                    msg.setText(afterDecodeMessage);

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    msg.setText(mAdgebraArrayList.get(0).getNotificationMessage().
                                            replaceAll("[+]", " "));
                                }
                                try {
                                    String afterDecodeTitle = URLDecoder.decode(mAdgebraArrayList.get(0).
                                            getNotificationTitle(), "UTF-8");
                                    title.setText(afterDecodeTitle);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    title.setText(mAdgebraArrayList.get(0).getNotificationTitle().
                                            replaceAll("[+]", " "));
                                }
/*
                                Glide.with(mContext)
                                        .load(mAdgebraArrayList.get(0).getIcon())
                                        .thumbnail(0.7f)
                                        .error(R.drawable.no_image_found)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(icon);
*/

                                Picasso.get()
                                        .load(mAdgebraArrayList.get(0).getIcon())
                                        .placeholder(R.drawable.logo_small)
                                        .error(R.drawable.no_image)
                                        .fit()
                                        .into(icon);

                                try {
/*
                                    Glide.with(mContext)
                                            .load(!mAdgebraArrayList.get(0).getNotificationImage().isEmpty() ?
                                                    mAdgebraArrayList.get(0).getNotificationImage() :
                                                    mAdgebraArrayList.get(0).getImageUrl())
                                            .error(R.drawable.no_image_found)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(banner);
*/

                                    Picasso.get()
                                            .load(!mAdgebraArrayList.get(0).getNotificationImage().isEmpty() ?
                                                    mAdgebraArrayList.get(0).getNotificationImage() :
                                                    mAdgebraArrayList.get(0).getImageUrl())
                                            .placeholder(R.drawable.logo)
                                            .error(R.drawable.no_image)
                                            .fit()
                                            .into(banner);
                                } catch (OutOfMemoryError ignored) {
                                } catch (Exception ignored) {}
                                view.setOnClickListener(v -> {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(mAdgebraArrayList.get(0).getTrackerUrl()));
                                        mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException anfe) {
                                        try {
                                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(mAdgebraArrayList.get(0).getTrackerUrl()));
                                            mContext.startActivity(intent);
                                        } catch (ActivityNotFoundException anfe2) {

                                        }
                                    } catch (Exception e) {

                                    }
                                });
                                if (mAdsCallBack != null) {
                                    mAdsCallBack.adLoaded(view);
                                }
                            } else {
                                if (mAdsCallBack != null) {
                                    mAdsCallBack.Error();
                                }
                            }
                        } else {
                            if (mAdsCallBack != null) {
                                mAdsCallBack.Error();
                            }
                        }
                    } else {
                        if (mAdsCallBack != null) {
                            mAdsCallBack.Error();
                        }
                    }

                }, throwable -> {
                }));
    }
}
