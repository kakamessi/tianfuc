package com.chico.dialog;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * @author: 陈国权
 * @date: 2018/4/25 上午12:24
 * @describe: 分类
 */

public class CategoryDialog extends BaseDialog {

    private String[] categorys;

    private NumberPicker npCategory;
    private Button btnCancel;
    private Button btnConfirm;
    private String value;

    public static CategoryDialog newInstance(String[] array) {
        Bundle bundle = new Bundle();
        bundle.putCharSequenceArray("category", array);
        CategoryDialog dialog = new CategoryDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected boolean setCancelable() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categorys = (String[]) getArguments().getCharSequenceArray("category");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_category;
    }

    @Override
    protected void initDialogFragment(View view) {
        npCategory = view.findViewById(R.id.np_category);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnConfirm = view.findViewById(R.id.btn_confirm);

        npCategory.setDisplayedValues(categorys);
        int count = categorys.length;
        npCategory.setMinValue(0);
        npCategory.setMaxValue(count - 1);
        npCategory.setValue(count / 2);
        value = categorys[count / 2];
        //不可编辑
        npCategory.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //设置不能循环
        npCategory.setWrapSelectorWheel(false);
        npCategory.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                value = categorys[newVal];

            }
        });

        setNumberPickerDividerColor(npCategory, R.color.color_line);
        setNumberPickerDividerHeight(npCategory, 1);
        setNumberPickerTextColor(npCategory, 0xFF666666);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialogListener != null) {
                    mDialogListener.onDialogClick(DialogConstant.DIALOG_CATEGORY, value);
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.DialogScaleAnim);
            WindowManager.LayoutParams wl = window.getAttributes();
            window.setAttributes(wl);
        }
    }


    /**
     * 设置NumberPicker分割线颜色
     *
     * @param picker
     * @param color
     */
    private void setNumberPickerDividerColor(NumberPicker picker, int color) {
        try {
            Field field = NumberPicker.class.getDeclaredField("mSelectionDivider");
            if (field != null) {
                field.setAccessible(true);
                field.set(picker, new ColorDrawable(color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置NumberPicker分割线高度
     *
     * @param picker
     */
    private void setNumberPickerDividerHeight(NumberPicker picker, int height) {
        try {
            Field field = NumberPicker.class.getDeclaredField("mSelectionDividerHeight");
            if (field != null) {
                field.setAccessible(true);
                field.set(picker, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置NumberPicker文字颜色
     *
     * @param picker
     * @param color
     * @return
     */
    private void setNumberPickerTextColor(NumberPicker picker, int color) {
        final int count = picker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = picker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field field = picker.getClass().getDeclaredField("mSelectorWheelPaint");
                    field.setAccessible(true);
                    ((Paint) field.get(picker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    picker.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
