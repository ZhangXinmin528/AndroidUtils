package com.zxm.utils.core.widget.loginfo;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.zxm.utils.core.R;
import com.zxm.utils.core.loginfo.LogLine;
import com.zxm.utils.core.loginfo.util.SearchCriteria;
import com.zxm.utils.core.loginfo.util.TagColorUtil;
import com.zxm.utils.core.widget.recyclerview.AbsRecyclerAdapter;
import com.zxm.utils.core.widget.recyclerview.AbsViewBinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wanglikun on 2018/10/30.
 */

public class LogItemAdapter extends AbsRecyclerAdapter<AbsViewBinder<LogLine>, LogLine> implements Filterable {


    public LogItemAdapter(Context context) {
        super(context);
        mClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    }


    private ArrayList<LogLine> mOriginalValues = new ArrayList<>();
    private ArrayFilter mFilter = new ArrayFilter();
    private int logLevelLimit = Log.VERBOSE;
    private ClipboardManager mClipboard;

    /**
     * 清空log
     */
    @SuppressLint("NotifyDataSetChanged")
    public void clearLog() {
        if (mOriginalValues != null && mOriginalValues.size() > 0) {
            mOriginalValues.clear();
        }
        clear();
        notifyDataSetChanged();
    }

    @Override
    protected AbsViewBinder<LogLine> createViewHolder(View view, int viewType) {
        return new LogInfoViewHolder(view);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.layout_log_info_list_item, parent, false);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public int getLogLevelLimit() {
        return logLevelLimit;
    }


    public void setLogLevelLimit(int logLevelLimit) {
        this.logLevelLimit = logLevelLimit;
    }

    public List<LogLine> getTrueValues() {
        return mOriginalValues != null ? mOriginalValues : mList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeFirst(int n) {
        if (mOriginalValues != null) {
            List<LogLine> subList = mOriginalValues.subList(n, mOriginalValues.size());
            for (int i = 0; i < n; i++) {
                // value to delete - delete it from the mObjects as well
                mList.remove(mOriginalValues.get(i));
            }
            mOriginalValues = new ArrayList<>(subList);
        }
        notifyDataSetChanged();
    }

    public class LogInfoViewHolder extends AbsViewBinder<LogLine> {

        private TextView mLogText;
        private TextView mPid;
        private TextView mTime;
        private TextView mTag;
        private TextView mLevel;

        public LogInfoViewHolder(View view) {
            super(view);
        }

        @Override
        protected void getViews() {
            mLogText = getView(R.id.log_output_text);
            mLevel = getView(R.id.log_level_text);
            mPid = getView(R.id.pid_text);
            mTime = getView(R.id.timestamp_text);
            mTag = getView(R.id.tag_text);
        }

        @Override
        protected void onViewClick(View view, final LogLine data) {
            super.onViewClick(view, data);
            data.setExpanded(!data.isExpanded());
            if (data.isExpanded() && data.getProcessId() != -1) {
                mLogText.setSingleLine(false);
                mTime.setVisibility(View.VISIBLE);
                mPid.setVisibility(View.VISIBLE);
                view.setBackgroundColor(Color.parseColor("#99A0AA"));
                mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), true));
                mTag.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), true));
            } else {
                mLogText.setSingleLine(true);
                mTime.setVisibility(View.GONE);
                mPid.setVisibility(View.GONE);
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), false));
                mTag.setTextColor(TagColorUtil.getTextColor(getContext(), data.getLogLevel(), false));
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipData clipData = ClipData.newPlainText("Label", data.getOriginalLine());
                    mClipboard.setPrimaryClip(clipData);
                    Toast.makeText(v.getContext(), "copy success", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        @Override
        public void bind(LogLine item) {

            mLevel.setText(item.getLogLevelText());
            mLevel.setTextColor(TagColorUtil.getLevelColor(getContext(), item.getLogLevel()));
            mLevel.setBackgroundColor(TagColorUtil.getLevelBgColor(getContext(), item.getLogLevel()));

            mPid.setText(String.valueOf(item.getProcessId()));
            mTime.setText(item.getTimestamp());

            mLogText.setText(item.getLogOutput());

            mTag.setText(item.getTag());

            if (item.isExpanded() && item.getProcessId() != -1) {
                mLogText.setSingleLine(false);
                mTime.setVisibility(View.VISIBLE);
                mPid.setVisibility(View.VISIBLE);
                mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), true));
                mTag.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), true));
                itemView.setBackgroundColor(Color.parseColor("#99A0AA"));

            } else {
                mLogText.setSingleLine(true);
                mTime.setVisibility(View.GONE);
                mPid.setVisibility(View.GONE);
                itemView.setBackgroundColor(Color.WHITE);
                mLogText.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), false));
                mTag.setTextColor(TagColorUtil.getTextColor(getContext(), item.getLogLevel(), false));
            }
        }
    }

    /**
     * 添加日志到adapter中
     *
     * @param logObj
     * @param text
     * @param notify
     */
    public void addWithFilter(LogLine logObj, CharSequence text, boolean notify) {

        if (mOriginalValues != null) {

            List<LogLine> inputList = Collections.singletonList(logObj);

            List<LogLine> filteredObjects = mFilter.performFilteringOnList(inputList, text);

            mOriginalValues.add(logObj);

            mList.addAll(filteredObjects);
            if (notify) {
                notifyItemRangeInserted(mList.size() - filteredObjects.size(), filteredObjects.size());
            }
        } else {
            mList.add(logObj);
            if (notify) {
                notifyItemInserted(mList.size());
            }
        }
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();


            ArrayList<LogLine> allValues = performFilteringOnList(mOriginalValues, prefix);

            results.values = allValues;
            results.count = allValues.size();

            return results;
        }

        public ArrayList<LogLine> performFilteringOnList(List<LogLine> inputList, CharSequence query) {

            SearchCriteria searchCriteria = new SearchCriteria(query);

            // search by log level
            ArrayList<LogLine> allValues = new ArrayList<>();

            ArrayList<LogLine> logLines = new ArrayList<>(inputList);


            for (LogLine logLine : logLines) {
                if (logLine != null && logLine.getLogLevel() >= logLevelLimit) {
                    allValues.add(logLine);
                }
            }
            ArrayList<LogLine> finalValues = allValues;

            // search by criteria
            if (!searchCriteria.isEmpty()) {

                final ArrayList<LogLine> values = allValues;
                final int count = values.size();

                final ArrayList<LogLine> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    final LogLine value = values.get(i);
                    // search the logline based on the criteria
                    if (searchCriteria.matches(value)) {
                        newValues.add(value);
                    }
                }

                finalValues = newValues;
            }

            return finalValues;
        }

        @SuppressLint("NotifyDataSetChanged")
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked

            //log.d("filtering: %s", constraint);


            mList = (List<LogLine>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetChanged();
            }
        }
    }
}