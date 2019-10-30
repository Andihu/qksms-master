package com.moez.QKSMS.xy;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.model.Message;
import com.xy.bizport.rcs.ui.widget.mms.ActionLongListener;
import com.xy.bizport.rcs.ui.widget.mms.MmsMultimediaView;
import com.xy.bizport.rcs.ui.widget.mms.data.MmsParts;

/**
 * Created by xueyakun on 2019/4/23.
 */

public class XYMmsCardUtils {
    private ActionLongListener mLongListener;

    public boolean createCard(Message message, ViewGroup parent) {

//        Message tag = (Message) parent.getTag();
//       if (tag != null && tag.getId() == message.getId()){
//           return true;
//       }

        XYMmsParts parts = new XYMmsParts();
        parts.transitionParts(message.getMmsParts(), message.getSubject(), message.getContentId());
        if (!( message.isMms() && (parts.isUnionMms(message)||parts.verifyMmsCode(message)))) {
            parent.setVisibility(View.GONE);
            return false;
        }
        parent.removeAllViews();
        View bindView = bindView(parts, parent.getContext());
        if (bindView != null) {
            parent.setVisibility(View.VISIBLE);
            parent.addView(bindView);
//          parent.setTag(message);
            return true;
        }
        return false;
    }

    private View bindView(MmsParts parts, Context mCtx) {
        MmsMultimediaView mediaView = new MmsMultimediaView(mCtx);
        mediaView.setLongAction(mLongListener);
        boolean isSuccess = mediaView.setData(parts);
        return isSuccess ? mediaView : null;
    }

    public void setActionLongClick(ActionLongListener listener) {
        if (mLongListener == null) {
            mLongListener = listener;
        }
    }
}
