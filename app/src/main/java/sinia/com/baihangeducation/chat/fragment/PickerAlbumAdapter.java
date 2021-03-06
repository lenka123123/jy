package sinia.com.baihangeducation.chat.fragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.supplement.tool.AlbumInfo;
import sinia.com.baihangeducation.supplement.tool.PickerImageLoadTool;
import sinia.com.baihangeducation.supplement.tool.RotateImageViewAware;
import sinia.com.baihangeducation.supplement.tool.ThumbnailsUtil;


public class PickerAlbumAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<AlbumInfo> mList;
    private Context mContext;
    private boolean isTemp = false;
    private int tempNum = 0;

    public PickerAlbumAdapter(Context context, List<AlbumInfo> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        this.mList = list;
    }

    public void setData(List<AlbumInfo> list) {
        mList.clear();
        this.mList = list;
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.picker_photofolder_item, null);
            viewHolder.folderCover = (ImageView) convertView.findViewById(R.id.picker_photofolder_cover);
            viewHolder.folderName = (TextView) convertView.findViewById(R.id.picker_photofolder_info);
            viewHolder.folderFileNum = (TextView) convertView.findViewById(R.id.picker_photofolder_num);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AlbumInfo albumInfo = mList.get(position);
        String thumbPath = ThumbnailsUtil.getThumbnailWithImageID(albumInfo.getImageId(), albumInfo.getFilePath());
        PickerImageLoadTool.disPlay(thumbPath, new RotateImageViewAware(viewHolder.folderCover, albumInfo.getAbsolutePath()),
                R.drawable.logo);
        viewHolder.folderName.setText(albumInfo.getAlbumName());
        if (albumInfo.getAlbumName().equals("camera") || albumInfo.getAlbumName().equals("Camera"))
            viewHolder.folderName.setText("相机");
        if (albumInfo.getAlbumName().equals("Screenshots"))
            viewHolder.folderName.setText("截屏");
        if (albumInfo.getAlbumName().equals("Pictures"))
            viewHolder.folderName.setText("照片");


        viewHolder.folderFileNum.setText(String.format(mContext.getResources().getString(
                R.string.picker_image_folder_info), mList.get(position).getList().size()));
        return convertView;
    }

    public class ViewHolder {
        public ImageView folderCover;
        public TextView folderName;
        public TextView folderFileNum;
        public LinearLayout layout;
    }
}
