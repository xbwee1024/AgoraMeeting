package io.agora.meeting.adapter;

import android.graphics.Color;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.databinding.BindingAdapter;

import io.agora.meeting.widget.gesture.GestureLayer;
import io.agora.meeting.widget.gesture.touch.adapter.GestureVideoTouchAdapterImpl;
import io.agora.sdk.annotation.RenderMode;
import io.agora.sdk.manager.RtcManager;

public class BindingAdapters {
    @BindingAdapter("android:layout_alignParentEnd")
    public static void bindAlignParentEnd(View view, boolean alignParentEnd) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            if (alignParentEnd) {
                ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_END);
            } else {
                ((RelativeLayout.LayoutParams) layoutParams).removeRule(RelativeLayout.ALIGN_PARENT_END);
            }
            view.setLayoutParams(layoutParams);
        }
    }

    @BindingAdapter("isGone")
    public static void bindIsGone(View view, boolean isGone) {
        view.setVisibility(isGone ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter("activated")
    public static void bindActivated(View view, boolean activated) {
        view.setActivated(activated);
    }

    @BindingAdapter(value = {
            "video_scale",
            "video_enable",
            "video_uid",
            "video_overlay",
            "video_render_mode",
    }, requireAll = false)
    public static void bindVideo(View view, boolean scale, boolean enable, int uid, boolean overlay, @RenderMode int renderMode) {
        if (view instanceof ViewGroup) {
            if(scale && enable){
                TextureView textureView = findTextView(view);
                if(textureView != null){
                    Object tag = textureView.getTag();
                    if (tag instanceof Integer) {
                        int oUid = (int) tag;
                        if (oUid == uid) {
                            // return if the SurfaceView has bound this uid
                            return;
                        }
                    }
                }else{
                    textureView = RtcManager.instance().createTextureView(view.getContext());
                }
                textureView.setTag(uid);
                final TextureView _textView = textureView;

                view.setBackgroundColor(Color.BLACK);
                ((ViewGroup) view).removeAllViews();
                ((ViewGroup) view).addView(_textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                GestureLayer gestureLayer = new GestureLayer(view.getContext(),
                        new GestureVideoTouchAdapterImpl(_textView) {
                            @Override
                            public boolean isFullScreen() {
                                return true;
                            }
                        });
                ((ViewGroup) view).addView(gestureLayer.getContainer(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                if (uid == 0) {
                    RtcManager.instance().setupLocalVideo(textureView, renderMode);
                } else {
                    RtcManager.instance().setupRemoteVideo(textureView, renderMode, uid);
                }
            }
            else if (enable) {
                SurfaceView surfaceView;
                // get child view from ViewGroup
                View child = ((ViewGroup) view).getChildAt(0);
                if (child instanceof SurfaceView) { // SurfaceView already exits
                    surfaceView = (SurfaceView) child;
                    Object tag = surfaceView.getTag();
                    if (tag instanceof Integer) {
                        int oUid = (int) tag;
                        if (oUid == uid) {
                            // return if the SurfaceView has bound this uid
                            return;
                        }
                    }
                } else { // SurfaceView not exits
                    // create new SurfaceView
                    surfaceView = RtcManager.instance().createRendererView(view.getContext());
                }
                surfaceView.setZOrderMediaOverlay(overlay);
                surfaceView.setTag(uid); // bind uid
                view.setBackground(null);
                ((ViewGroup) view).removeAllViews();
                ((ViewGroup) view).addView(surfaceView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                if (uid == 0) {
                    RtcManager.instance().setupLocalVideo(surfaceView, renderMode);
                } else {
                    RtcManager.instance().setupRemoteVideo(surfaceView, renderMode, uid);
                }
            } else {
                view.setBackground(null);
                ((ViewGroup) view).removeAllViews();
            }
        }
    }

    private static TextureView findTextView(View view) {
        View targetView = view;
        if(targetView instanceof TextureView){
            return (TextureView) targetView;
        }
        if(targetView instanceof ViewGroup){
            ViewGroup parent = (ViewGroup) targetView;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                targetView = findTextView(parent.getChildAt(i));
                if(targetView != null){
                    return (TextureView) targetView;
                }
            }
        }
        return null;
    }
}
