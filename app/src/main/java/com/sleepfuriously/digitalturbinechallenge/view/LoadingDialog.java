package com.sleepfuriously.digitalturbinechallenge.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sleepfuriously.digitalturbinechallenge.R;

/**
 * Provides a progress dialog to display while the
 * user is waiting for network events.
 *
 * Uses Singleton pattern, lazy instantiation.
 */
public class LoadingDialog {

    private static AlertDialog.Builder mBuilder;

    private LoadingDialog() {}


    /**
     * Returns an instance of an AlertDialog suitable for
     * displaying as a progress dialog.  Replaces the
     * deprecated ProgressDialog.
     *
     * NOTE: you MUST call onDestroy when the supplied Context
     * goes out of scope (is destroyed).  This is to prevent an
     * odd bug in the Android UI cache from causing crashes the
     * 2nd time the app is run.
     *
     * @param ctx   always needed
     *
     * @param titleResource     String id to use as title.
     */
    public static AlertDialog.Builder getLoadingDialogBuilder(Context ctx,
                                                              int titleResource) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(ctx);
            mBuilder.setCancelable(false);

            String str = ctx.getResources().getString(titleResource);
            mBuilder.setTitle(str);

            final ProgressBar progressBar = new ProgressBar(ctx);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            progressBar.setLayoutParams(lp);
            mBuilder.setView(progressBar);
        }

        return mBuilder;
    }

    /**
     * Same as {@link #getLoadingDialogBuilder(Context, int)}, except this uses the
     * default loading string.
     */
    public static AlertDialog.Builder getLoadingDialogBuilder(Context ctx) {
        return getLoadingDialogBuilder(ctx, R.string.initial_load_msg);
    }

    /**
     * You MUST call this when the context that started this dialog is
     * destroyed.  This prevents a weird bug in the Android view cache
     * from causing crashes when restarting the app.  Bizarre, but true.
     */
    public static void onDestroy() {
        if (mBuilder != null) {
            mBuilder = null;
        }
    }
}
