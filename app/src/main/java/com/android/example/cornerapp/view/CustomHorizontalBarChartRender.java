package com.android.example.cornerapp.view;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by kapera on 22-Apr-18.
 */
public class CustomHorizontalBarChartRender extends HorizontalBarChartRenderer {

    private double mMaxValue;
    private static final int BAR_OFFSET = 32;
    private RectF mBarShadowRectBuffer = new RectF();

    public CustomHorizontalBarChartRender(int maxValue, BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.mMaxValue = maxValue;
        calcMaxOffset();
    }

    @Override
    protected void drawValue(Canvas c, String valueText, float x, float y, int color) {
        super.drawValue(c, valueText, (float) (x+ mMaxValue), y, color);
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        if(mMaxValue < 0) {

            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

            mBarBorderPaint.setColor(dataSet.getBarBorderColor());
            mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

            final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

            float phaseX = mAnimator.getPhaseX();
            float phaseY = mAnimator.getPhaseY();

            // draw the bar shadow before the values
            if (mChart.isDrawBarShadowEnabled()) {
                mShadowPaint.setColor(dataSet.getBarShadowColor());

                BarData barData = mChart.getBarData();

                final float barWidth = barData.getBarWidth();
                final float barWidthHalf = barWidth / 2.0f;
                float x;

                for (int i = 0, count = Math.min((int) (Math.ceil((float) (dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                     i < count;
                     i++) {

                    BarEntry e = dataSet.getEntryForIndex(i);

                    x = e.getX();

                    mBarShadowRectBuffer.top = x - barWidthHalf;
                    mBarShadowRectBuffer.bottom = x + barWidthHalf;

                    trans.rectValueToPixel(mBarShadowRectBuffer);

                    if (!mViewPortHandler.isInBoundsTop(mBarShadowRectBuffer.bottom))
                        continue;

                    if (!mViewPortHandler.isInBoundsBottom(mBarShadowRectBuffer.top))
                        break;

                    mBarShadowRectBuffer.left = mViewPortHandler.contentLeft();
                    mBarShadowRectBuffer.right = mViewPortHandler.contentRight();

                    c.drawRect(mBarShadowRectBuffer, mShadowPaint);
                }
            }

            // initialize the buffer
            BarBuffer buffer = mBarBuffers[index];
            buffer.setPhases(phaseX, phaseY);
            buffer.setDataSet(index);
            buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
            buffer.setBarWidth(mChart.getBarData().getBarWidth());

            buffer.feed(dataSet);

            trans.pointValuesToPixel(buffer.buffer);

            final boolean isSingleColor = dataSet.getColors().size() == 1;

            if (isSingleColor) {
                mRenderPaint.setColor(dataSet.getColor());
            }

            for (int j = 0; j < buffer.size(); j += 4) {

                if (!mViewPortHandler.isInBoundsTop(buffer.buffer[j + 3]))
                    break;

                if (!mViewPortHandler.isInBoundsBottom(buffer.buffer[j + 1]))
                    continue;

                if (!isSingleColor) {
                    // Set the color for the currently drawn value. If the index
                    // is out of bounds, reuse colors.
                    mRenderPaint.setColor(dataSet.getColor(j / 4));
                }

                c.drawRect(buffer.buffer[j] + BAR_OFFSET, buffer.buffer[j + 1], buffer.buffer[j + 2] + BAR_OFFSET,
                        buffer.buffer[j + 3], mRenderPaint);

                if (drawBorder) {
                    c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                            buffer.buffer[j + 3], mBarBorderPaint);
                }
            }
        } else
            super.drawDataSet(c, dataSet, index);
    }

    private void calcMaxOffset(){
        mMaxValue = mMaxValue*7.5;
    }
}
