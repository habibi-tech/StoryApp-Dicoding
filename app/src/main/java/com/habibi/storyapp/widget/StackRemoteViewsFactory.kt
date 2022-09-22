package com.habibi.storyapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.habibi.core.data.Resource
import com.habibi.core.domain.widget.usecase.IWidgetUseCase
import com.habibi.storyapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Singleton
class StackRemoteViewsFactory @Inject constructor(
    @ApplicationContext private val mContext: Context,
    private val useCase: IWidgetUseCase
) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Pair<String, String>>()

    override fun onCreate() {
    }

    override fun onDataSetChanged() {

        CoroutineScope(context = Dispatchers.IO).launch {
            when (val response = useCase.getListStory()) {
                is Resource.Success -> {
                    mWidgetItems.clear()
                    response.data?.map {
                        mWidgetItems.add(Pair(it.name, it.photoUrl))
                    }
                }
                else -> {
                    mWidgetItems.clear()
                }
            }
        }

    }

    override fun onDestroy() {}

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)

        val bitmap: Bitmap = Glide
            .with(mContext)
            .asBitmap()
            .load(mWidgetItems[position].second)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, bitmap)
        rv.setTextViewText(R.id.tv_user_name, mWidgetItems[position].first)

        val extras = bundleOf(
            StoryWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}